package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListRepository repository;
    private LiveData<List<ShoppingList>> allItems;

    public ShoppingListViewModel(Application application) {
        super(application);
        repository = new ShoppingListRepository(application);
        allItems = repository.getAllItems();
    }

    LiveData<List<ShoppingList>> getAllItems() {
        return allItems;
    }

    public void insert(ShoppingList item) {
        repository.insert(item);
        repository.refreshData();
    }
}
