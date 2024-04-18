package c20321466.softwarepatterns.clothingstore.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import c20321466.softwarepatterns.clothingstore.data.OrderRepository;
import c20321466.softwarepatterns.clothingstore.data.models.BasketItem;
import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;
import c20321466.softwarepatterns.clothingstore.data.models.Order;

public class BasketViewModel extends ViewModel {
    private MutableLiveData<List<BasketItem>> basketItemsLiveData = new MutableLiveData<>();
    private List<BasketItem> basketItems = new ArrayList<>();
    private OrderRepository repository = new OrderRepository();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    public LiveData<List<BasketItem>> getBasketItemsLiveData() {
        return basketItemsLiveData;
    }

    public void addToBasket(ClothingItem clothingItem, String userId) {
        BasketItem basketItem = new BasketItem(clothingItem, userId, 1); // Initial quantity is 1
        basketItems.add(basketItem);
        basketItemsLiveData.setValue(basketItems);
    }

    public void purchaseBasket() {
        String userId = currentUser.getUid();
        // Assuming repository method to save the order and clear the basket
        List<BasketItem> basketItems = basketItemsLiveData.getValue();
        double totalPrice = calculateTotalPrice(basketItems);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDate.format(formatter);
        Order order = new Order(userId, basketItems, formattedDateTime, totalPrice);
        repository.saveOrder(order); // Save order to database
        assert basketItems != null;
        basketItems.clear();
        basketItemsLiveData.setValue(basketItems);// Clear basket items
    }

    public double calculateTotalPrice(List<BasketItem> basketItems) {
        double totalPrice = 0.0;
        if (basketItems != null) {
            for (BasketItem basketItem : basketItems) {
                ClothingItem clothingItem = basketItem.getClothingItem();
                totalPrice += clothingItem.getPrice() * basketItem.getQuantity();
            }
        }
        return totalPrice;
    }
}
