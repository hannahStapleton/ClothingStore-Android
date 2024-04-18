package c20321466.softwarepatterns.clothingstore.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import c20321466.softwarepatterns.clothingstore.R;
import c20321466.softwarepatterns.clothingstore.data.models.BasketItem;
import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;
import c20321466.softwarepatterns.clothingstore.data.models.Order;
import c20321466.softwarepatterns.clothingstore.ui.adapters.BasketItemAdapter;
import c20321466.softwarepatterns.clothingstore.ui.viewmodels.BasketViewModel;

public class ShoppingBasketActivity extends AppCompatActivity {
    private BasketViewModel basketViewModel;
    private RecyclerView recyclerView;
    private BasketItemAdapter basketItemAdapter;
    private TextView textViewTotalPrice;
    private Button buttonPurchase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        basketViewModel = new ViewModelProvider(this).get(BasketViewModel.class);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonPurchase = findViewById(R.id.buttonPurchase);
        recyclerView = findViewById(R.id.recyclerViewBasket);
        basketItemAdapter = new BasketItemAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(basketItemAdapter);

        // Observe basket items LiveData
        basketViewModel.getBasketItemsLiveData().observe(this, basketItems -> {
            basketItemAdapter.setItems(basketItems);
            double totalPrice = basketViewModel.calculateTotalPrice(basketItems);
            updateTotalPrice(totalPrice);
        });

        // Set up click listener for the purchase button
        buttonPurchase.setOnClickListener(v -> basketViewModel.purchaseBasket());
    }

    public void updateTotalPrice(double totalPrice) {
        textViewTotalPrice.setText(String.format("Total Price: €%.2f", totalPrice));
    }
}
