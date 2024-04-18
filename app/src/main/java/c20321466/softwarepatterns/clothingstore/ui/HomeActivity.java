package c20321466.softwarepatterns.clothingstore.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;
import c20321466.softwarepatterns.clothingstore.ui.adapters.ClothingItemAdapter;
import c20321466.softwarepatterns.clothingstore.ui.viewmodels.ClothingViewModel;
import c20321466.softwarepatterns.clothingstore.R;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClothingItemAdapter adapter;
    private SearchView searchView;
    private Spinner filterSpinner;

    private ClothingViewModel clothingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.grid_recycler_view);
        searchView = findViewById(R.id.search);
        filterSpinner = findViewById(R.id.filterSpinner);

        setupRecyclerView();

        // Initialize ViewModel
        clothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        // Observe clothing items LiveData
        clothingViewModel.getClothingItemsLiveData().observe(this, clothingItems -> {
            adapter.setClothingItems(clothingItems);
        });

        // Setup SearchView and Spinner
        SearchView searchView = findViewById(R.id.search);
        Spinner filterSpinner = findViewById(R.id.filterSpinner);

        // SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query, filterSpinner.getSelectedItem().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText, filterSpinner.getSelectedItem().toString());
                return true;
            }
        });

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String filter = adapterView.getItemAtPosition(position).toString();
                String searchText = searchView.getQuery().toString();
                performSearch(searchText, filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle nothing selected
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new ClothingItemAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void performSearch(String searchText, String filter) {
        // Call ViewModel method to search for clothing items
        clothingViewModel.searchClothingItems(searchText, filter).observe(this, clothingItems -> {
            if (clothingItems != null) {
                // Update RecyclerView with new data
                adapter.setClothingItems(clothingItems);
            } else {
                Toast.makeText(HomeActivity.this, "Failed to fetch clothing items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart(ClothingItem item) {
        // Implement cart functionality (e.g., add item to shopping cart)
        Toast.makeText(this, "Item added to cart: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
