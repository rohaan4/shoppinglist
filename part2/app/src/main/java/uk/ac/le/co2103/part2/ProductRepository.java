package uk.ac.le.co2103.part2;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> productDao.insert(product));
    }

    LiveData<List<Product>> getProductsForList(int listId) {
        return productDao.getProductsForList(listId);
    }
}
