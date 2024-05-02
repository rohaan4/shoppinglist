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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class ShoppingListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();
    private static final int UPDATE_PRODUCT_REQUEST_CODE = 1;
    private ProductViewModel productViewModel;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int listId = getIntent().getIntExtra("LIST_ID", -1);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductsForList(listId).observe(this, products -> {
            productList = products;
            adapter = new ProductAdapter(productList, product -> { /* ... */ });
            recyclerView.setAdapter(adapter);
        });

        initializeProductList(listId);


        adapter = new ProductAdapter(productList, product -> {
            Toast.makeText(ShoppingListActivity.this, "Clicked product: " + product.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
            intent.putExtra("LIST_ID", listId);
            startActivity(intent);


        });

        setupItemClickListener();
    }

    private void setupItemClickListener() {
        adapter.setOnItemClickListener(product -> {
            new AlertDialog.Builder(ShoppingListActivity.this)
                    .setTitle("Edit or Delete")
                    .setMessage("Choose an action for " + product.getName())
                    .setPositiveButton("Edit", (dialog, which) -> {
                        Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                        intent.putExtra("PRODUCT_NAME", product.getName());
                        startActivityForResult(intent, UPDATE_PRODUCT_REQUEST_CODE);
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        int position = productList.indexOf(product);
                        if(position != -1) {
                            productList.remove(position);
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(ShoppingListActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initializeProductList(int listId) {
        // Using the corrected constructor that properly initializes fields
        productList.add(new Product("Apples", 2, "Kg"));
        productList.add(new Product("Milk", 1, "Litre"));
    }

    private void deleteProduct(Product product) {
        int position = productList.indexOf(product);
        if (position != -1) {
            productList.remove(position);
            adapter.notifyItemRemoved(position);
            // If you're using a database, remember to delete the product from the database as well
        }
    }
}



