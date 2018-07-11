package com.example.floralboutique.ui.cart;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floralboutique.ui.member.flowerdetail.MemberFlowerDetailActivity;
import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Vector;

public class CartFragment extends Fragment {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private List<CartItemModel> items_ = new Vector<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // load view model
        CartViewModelFactory factory = AppComponents.getCartViewModelFactory(Objects.requireNonNull(getActivity()).getApplicationContext());
        CartViewModel model = ViewModelProviders.of(this, factory).get(CartViewModel.class);

        // setup recycler view
        CartAdapter adapter = new CartAdapter(this::startFlowerDetailActivity,
                model::removeFromCart,
                model::updateQuantity);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, adapter);

        // setup other widgets
        Button buttonPlaceOrder = view.findViewById(R.id.button_place_order);
        TextView textCartEmpty = view.findViewById(R.id.text_cart_empty);
        TextView textTotal = view.findViewById(R.id.text_total);
        View footer = view.findViewById(R.id.cart_footer);

        // events
        buttonPlaceOrder.setOnClickListener((v) -> placeOrder(model, adapter));

        // observe
        model.getCartItems().observe(getViewLifecycleOwner(),
                items -> onCartItemsChanged(items, adapter, footer, textCartEmpty, textTotal));

        return view;
    }

    private void onCartItemsChanged(@Nullable List<CartItemModel> items,
                                    @NonNull CartAdapter adapter,
                                    @NonNull View footer,
                                    @NonNull TextView textCartEmpty,
                                    @NonNull TextView textTotal) {
        if (items == null || items.size() == 0) {
            textCartEmpty.setVisibility(View.VISIBLE);
            footer.setVisibility(View.GONE);
            adapter.clearItems();
            return;
        }

        TreeMap<String, CartItemModel> map = new TreeMap<>();

        for (CartItemModel item : items) {
            CartItemModel mapped = map.get(item.flowerName);
            if (mapped == null) {
                mapped = item;
            } else {
                mapped = new CartItemModel(mapped.flowerName,
                        mapped.image,
                        mapped.price,
                        mapped.discountPercent * item.discountPercent,
                        mapped.quantity);
            }
            map.put(item.flowerName, mapped);
        }

        items_ = new ArrayList<>(map.values());
        adapter.swapItems(items_);
        calculateTotal(textTotal);
        textCartEmpty.setVisibility(View.GONE);
        footer.setVisibility(View.VISIBLE);
    }

    private void calculateTotal(@NonNull TextView textTotal) {
        float total = 0;
        for (CartItemModel item : items_) {
            total += item.price * item.discountPercent * item.quantity;
        }

        textTotal.setText(AppFormatter.formatPrice(total));
    }

    private void placeOrder(@NonNull CartViewModel model, @NonNull CartAdapter adapter) {
        if (model.getCurrentUser().getValue() == null) {
            Toast.makeText(getContext(), "Please login to place order!", Toast.LENGTH_SHORT).show();
            return;
        }

        executors_.diskIo.execute(() -> {
            model.placeOrder(items_);
            executors_.mainThread.execute(adapter::clearItems);
        });
    }

    private void startFlowerDetailActivity(@NonNull Object item) {
        CartItemModel cartItem = (CartItemModel) item;
        Intent intent = new Intent(this.getActivity(), MemberFlowerDetailActivity.class);
        intent.putExtra(MemberFlowerDetailActivity.FLOWER_NAME_EXTRA, cartItem.flowerName);
        startActivity(intent);
    }
}
