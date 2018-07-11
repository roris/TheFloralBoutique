package com.example.floralboutique.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.floralboutique.data.dao.CartItemDao;
import com.example.floralboutique.data.dao.CategoryDao;
import com.example.floralboutique.data.dao.FlowerCategoryDao;
import com.example.floralboutique.data.dao.FlowerDao;
import com.example.floralboutique.data.dao.FlowerOrderDao;
import com.example.floralboutique.data.dao.FlowerPromotionDao;
import com.example.floralboutique.data.dao.OrderDao;
import com.example.floralboutique.data.dao.PromotionDao;
import com.example.floralboutique.data.dao.UserDao;
import com.example.floralboutique.data.converter.DateConverter;
import com.example.floralboutique.data.entity.CartItem;
import com.example.floralboutique.data.entity.Category;
import com.example.floralboutique.data.entity.Flower;
import com.example.floralboutique.data.entity.FlowerCategory;
import com.example.floralboutique.data.entity.FlowerOrder;
import com.example.floralboutique.data.entity.FlowerPromotion;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.data.entity.User;

@Database(entities = {
        User.class,
        Flower.class,
        Promotion.class,
        FlowerPromotion.class,
        CartItem.class,
        Order.class,
        FlowerOrder.class,
        Category.class,
        FlowerCategory.class},
        exportSchema = false,
        version = 1)
@TypeConverters(DateConverter.class)
public abstract class FloralBoutiqueDatabase extends RoomDatabase {
    private static FloralBoutiqueDatabase instance_;
    private Context context_;

    public static FloralBoutiqueDatabase getInstance(Context context) {
        if (instance_ == null) {
            synchronized (FloralBoutiqueDatabase.class) {
                if (instance_ == null) {
                    instance_ = Room.databaseBuilder(context.getApplicationContext(), FloralBoutiqueDatabase.class, "tfb_db")
                            .fallbackToDestructiveMigration().build();
                    instance_.context_ = context;
                }
            }
        }
        return instance_;
    }

    public void populateDatabase() {
        userDao().insertOrIgnore(new User("admin", "1234", User.USER_TYPE_ADMIN));
        userDao().insertOrIgnore(new User("bob", "1234", User.USER_TYPE_MEMBER));

        // test with 3 categories
        // Bitmap bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_birthday);
        // File file = new File(context_.getFilesDir(), "birthday.png");
        // BitmapUtil.save(file, bmp);
        // categoryDao().insertOrIgnore(new Category("Birthday", file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_anniversary);
        // file = new File(context_.getFilesDir(), "anniversary.png");
        // BitmapUtil.save(file, bmp);
        // categoryDao().insertOrIgnore(new Category("Anniversary", file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_funeral);
        // file = new File(context_.getFilesDir(), "funeral.png");
        // BitmapUtil.save(file, bmp);
        // categoryDao().insertOrIgnore(new Category("Funeral", file.getAbsolutePath()));

        // insertOrIgnore 10 flowers
        // there are categories to consider

        // String loremIpsum = context_.getString(R.string.lorem_ipsum);
        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_alstroemeria);
        // file = new File(context_.getFilesDir(), "alstroemeria.png");
        // BitmapUtil.save(file, bmp);
        // flowerDao().insertOrIgnore(new Flower("Alstroemeria", loremIpsum, 100, file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_rose);
        // file = new File(context_.getFilesDir(), "rose.png");
        // BitmapUtil.save(file, bmp);
        // flowerDao().insertOrIgnore(new Flower("Rose", loremIpsum, 200, file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_tulip);
        // file = new File(context_.getFilesDir(), "tulip.png");
        // BitmapUtil.save(file, bmp);
        // flowerDao().insertOrIgnore(new Flower("Tulip", loremIpsum, 150, file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_anemone);
        // file = new File(context_.getFilesDir(), "anemone.png");
        // BitmapUtil.save(file, bmp);
        // flowerDao().insertOrIgnore(new Flower("Anemone", loremIpsum, 250, file.getAbsolutePath()));

        // bmp = BitmapFactory.decodeResource(context_.getResources(), R.drawable.img_amaryllis);
        // file = new File(context_.getFilesDir(), "amaryllis.png");
        // BitmapUtil.save(file, bmp);
        // flowerDao().insertOrIgnore(new Flower("Amaryllis", loremIpsum, 250, file.getAbsolutePath()));

        // flower <-> category
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Rose", "Anniversary"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Rose", "Birthday"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Tulip", "Anniversary"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Anemone", "Funeral"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Alstroemeria", "Birthday"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Amaryllis", "Birthday"));
        // flowerCategoryDao().insertOrIgnore(new FlowerCategory("Amaryllis", "Anniversary"));

        // create some promotions
        // promotionDao().insertOrReplace(new Promotion(0, DateConverter.toDate("2018-06-29 00:00:00"), DateConverter.toDate("2018-12-01 23:59:59"), 0.90f));
        // promotionDao().insertOrReplace(new Promotion(0, DateConverter.toDate("2018-06-01 00:00:00"), DateConverter.toDate("2018-08-01 23:59:59"), 0.75f));

        // flower <-> promotion
        // flowerPromotionDao().insertOrIgnore(new FlowerPromotion("Rose", 1));
        // flowerPromotionDao().insertOrIgnore(new FlowerPromotion("Rose", 2));
        // flowerPromotionDao().insertOrIgnore(new FlowerPromotion("Tulip", 1));
        // flowerPromotionDao().insertOrIgnore(new FlowerPromotion("Alstroemeria", 1));
    }

    public abstract UserDao userDao();

    public abstract CartItemDao cartItemDao();

    public abstract FlowerDao flowerDao();

    public abstract PromotionDao promotionDao();

    public abstract OrderDao orderDao();

    public abstract FlowerOrderDao flowerOrderDao();

    public abstract CategoryDao categoryDao();

    public abstract FlowerCategoryDao flowerCategoryDao();

    public abstract FlowerPromotionDao flowerPromotionDao();
}
