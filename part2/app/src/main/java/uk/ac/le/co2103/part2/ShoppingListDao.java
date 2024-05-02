package uk.ac.le.co2103.part2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.util.Log;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Insert
    default void insert(ShoppingList item) {
        Log.d("ShoppingListDao", "Inserting Shopping List: " + item.getName());

        // ACTUAL DATABASE INSERTION CODE WOULD GO HERE.
        // This depends on how you have set up Room and your database interactions.
        // Room might autogenerate some of this if you're using @Insert
    }

    @Query("SELECT * FROM shopping_list ORDER BY name ASC")
    LiveData<List<ShoppingList>> getShoppingList();

    @Query("DELETE FROM shopping_list")
    void deleteAll();

    // Inside your ShoppingListDao interface
    @Query("SELECT * FROM shopping_list ORDER BY name ASC") // Adjust the query if needed
    List<ShoppingList> getShoppingListAsList();
}

