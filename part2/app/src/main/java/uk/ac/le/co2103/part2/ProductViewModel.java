package uk.ac.le.co2103.part2;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repository;
    private final LiveData<List<Product>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    LiveData<List<Product>> getProductsForList(int listId) {
        return repository.getProductsForList(listId);
    }
}



