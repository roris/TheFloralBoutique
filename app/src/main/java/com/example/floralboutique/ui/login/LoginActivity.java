package com.example.floralboutique.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;

public class LoginActivity extends AppCompatActivity {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private boolean closing_ = false;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        LoginViewModelFactory factory = AppComponents.getLoginViewModelFactory(this.getApplicationContext());
        LoginViewModel model = ViewModelProviders.of(this, factory).get(LoginViewModel.class);

        EditText editUsername = findViewById(R.id.edit_username);
        EditText editPassword = findViewById(R.id.edit_password);

        findViewById(R.id.button_login).setOnClickListener(
                view -> onLoginPressed(model, editUsername, editPassword));

        findViewById(R.id.button_register).setOnClickListener(
                view -> onRegisterClicked(model, editUsername, editPassword));
    }

    private synchronized void onLoginSuccessful() {
        if (closing_) {
            return;
        }
        closing_ = true;
        finish();
    }

    public void onRegisterClicked(LoginViewModel model, EditText editUsername, EditText editPassword) {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (username.length() < 8) {
            editUsername.setError("at least 8 characters!");
        }

        if (password.length() < 8) {
            editPassword.setError("at least 8 characters!");
        }

        if (username.length() >= 8 && password.length() >= 8) {
            // dispatch to worker thread
            executors_.diskIo.execute(() -> {
                boolean successful = model.register(username, password);
                if (successful) {
                    onLoginSuccessful();
                    return;
                }

                executors_.mainThread.execute(() ->
                        Toast.makeText(this, "Failed to register!", Toast.LENGTH_SHORT).show());
            });
        }
    }

    void onLoginPressed(LoginViewModel model, EditText editUsername, EditText editPassword) {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // dispatch to worker thread
        executors_.diskIo.execute(() -> {
            boolean successful = model.login(username, password);

            if (successful) {
                onLoginSuccessful();
                return;
            }

            executors_.mainThread.execute(() ->
                    Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show());
        });
    }
}
