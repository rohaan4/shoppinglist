package uk.ac.le.co2103.part2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Button buttonIncrease = findViewById(R.id.buttonIncrease);
        Button buttonDecrease = findViewById(R.id.buttonDecrease);
        EditText editTextQuantity = findViewById(R.id.editTextQuantity); // Assuming you have this EditText in your layout

        buttonIncrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(editTextQuantity.getText().toString());
            editTextQuantity.setText(String.valueOf(++currentQuantity));
        });

        buttonDecrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(editTextQuantity.getText().toString());
            if (currentQuantity > 0) {
                editTextQuantity.setText(String.valueOf(--currentQuantity));
            }
        });

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        // Fetch product details based on productId and populate the UI

        // Setup the EditText, Spinner, and Buttons as in AddProductActivity
        // Additionally, implement the "+" and "-" buttons to adjust quantity
    }
}

