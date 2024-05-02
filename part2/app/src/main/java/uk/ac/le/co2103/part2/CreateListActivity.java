package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CreateListActivity extends AppCompatActivity {

    private ShoppingListViewModel viewModel; // Declare your ViewModel
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        final EditText editTextListName = findViewById(R.id.editTextListName);
        final Button buttonCreate = findViewById(R.id.buttonCreate);

        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class); // Initialize ViewModel

        buttonCreate.setOnClickListener(view -> {
            String listName = editTextListName.getText().toString().trim();
            if (!listName.isEmpty()) {

                // Create a new ShoppingList object
                ShoppingList newList = new ShoppingList(0, listName, ""); // Assuming '0' triggers auto-generated ID

                // Call the insert method on your ViewModel
                viewModel.insert(newList);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                // Prepare data intent for result
                Intent data = new Intent();
                data.putExtra("LIST_NAME", listName);
                setResult(RESULT_OK, data);
                finish(); // Close the activity
            } else {
                editTextListName.setError("Name is required!");
            }
            Log.d("CreateListActivity", "Create List button clicked");
        });
    }
}
