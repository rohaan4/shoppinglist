package uk.ac.le.co2103.part2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddProductActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        setupUI();

    }

    private void setupUI() {
        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextQuantity = findViewById(R.id.editTextQuantity);
        final Spinner spinner = findViewById(R.id.spinner);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_unit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        buttonAdd.setOnClickListener(view -> saveProduct(editTextName, editTextQuantity, spinner));
    }

    private void saveProduct(EditText editTextName, EditText editTextQuantity, Spinner spinner) {
        String name = editTextName.getText().toString().trim();
        String quantityString = editTextQuantity.getText().toString().trim();
        String unit = spinner.getSelectedItem().toString();

        // Create product with list ID
        int quantity = Integer.parseInt(quantityString);
        Product product = new Product(name, quantity, unit);
        int listId = 0;
        product.id = listId;

        listId = getIntent().getIntExtra("LIST_ID", -1);
        if (listId == -1) {
            Toast.makeText(this, "List ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.insert(product);
        if (!name.isEmpty() && !quantityString.isEmpty()) {
            try {
                viewModel.insert(product);
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity entered", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

}

