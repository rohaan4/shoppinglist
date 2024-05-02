package uk.ac.le.co2103.part2;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ShoppingListRepository {
    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> allItems;


    ShoppingListRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        allItems = shoppingListDao.getShoppingList();
    }

    void insert(ShoppingList item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.insert(item);
        });
    }

    public LiveData<List<ShoppingList>> getAllItems() {
        return allItems;
    }

    private MutableLiveData<List<ShoppingList>> _allItems = new MutableLiveData<>();
    public void refreshData() {
        // Inside ShoppingListRepository.java
        AppDatabase.databaseWriteExecutor.execute(() -> {
                // Fetch the latest data from the DAO (still on background thread)
            List<ShoppingList> latestData = shoppingListDao.getShoppingListAsList(); // Or use your existing getShoppingList() method

                // Switch to the main thread to update LiveData safely
            new Handler(Looper.getMainLooper()).post(() -> {
                _allItems.setValue(latestData);
            });
        });
    }


}
