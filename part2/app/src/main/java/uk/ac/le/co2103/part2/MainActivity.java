package uk.ac.le.co2103.part2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private ShoppingListViewModel shoppingListViewModel;
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Setup the adapter
        adapter = new ShoppingListAdapter(this, shoppingLists);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        if (shoppingLists.isEmpty()) {
            insertDummyData();
        }
        // Setup ViewModel and observe changes to shopping lists
        shoppingListViewModel.getAllItems().observe(this, shoppingLists -> {
            adapter.setShoppingLists(shoppingLists);
            adapter.notifyDataSetChanged();
        });


        adapter.setOnItemClickListener(shoppingList -> {
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            intent.putExtra("LIST_ID", shoppingList.getListId());
            startActivity(intent);
        });

        productAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            // Handle item click, if necessary
            Toast.makeText(MainActivity.this, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, products -> {
            // Update the cached copy of the products in the adapter.
            productAdapter.updateProducts(products);
        });


        //Activity 3
        adapter.setOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShoppingList shoppingList) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("LIST_ID", shoppingList.getListId()); // Pass list ID or entire object
                startActivity(intent);
            }
        });

        //Activity 4
        // Set the long click listener here
        adapter.setOnItemLongClickListener(position -> {
            // Show confirmation dialog for deletion
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Shopping List")
                    .setMessage("Are you sure you want to delete this shopping list and all its products?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteShoppingList(position))
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        });

        // Set the click listener here
        adapter.setOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShoppingList shoppingList) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("LIST_ID", shoppingList.getListId()); // Pass list ID or entire object
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivityForResult(intent, CREATE_LIST_REQUEST_CODE);
        });
        Log.d("MainActivity", "MainActivity onCreate() executed");
    }


    private void insertDummyData() {
        List<ShoppingList> dummyLists = Arrays.asList(
                new ShoppingList(1, "Groceries", "url_to_groceries_image"), // Example image URL or local resource
                new ShoppingList(2, "Electronics", "url_to_electronics_image"),
                new ShoppingList(3, "Clothing", "url_to_clothing_image")
        );
        for (ShoppingList list : dummyLists) {
            shoppingListViewModel.insert(list);
        }
    }

    //Activity 2
    // Define a request code
    private static final int CREATE_LIST_REQUEST_CODE = 1;

    // Handle the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_LIST_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String listName = data.getStringExtra("LIST_NAME");
            // Here you would add the new shopping list to your dataset
            // and notify the adapter, potentially including image handling
        }
    }

    //Activity 4
    private void deleteShoppingList(int position) {
        // Example deletion code, adjust according to your data handling
        ShoppingList listToDelete = shoppingLists.get(position);
        // Delete the list from your data source (e.g., database, in-memory list)
        shoppingLists.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Shopping list deleted", Toast.LENGTH_SHORT).show();
    }
}