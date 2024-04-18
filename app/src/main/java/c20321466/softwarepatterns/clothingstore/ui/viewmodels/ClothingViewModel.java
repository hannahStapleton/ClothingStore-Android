package c20321466.softwarepatterns.clothingstore.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import c20321466.softwarepatterns.clothingstore.data.ClothingRepository;
import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;
import c20321466.softwarepatterns.clothingstore.data.models.User;

public class ClothingViewModel extends ViewModel {

    private LiveData<List<ClothingItem>> clothingItemsLiveData;
    private ClothingRepository repository;

    public ClothingViewModel() {
        clothingItemsLiveData = new MutableLiveData<>();
        repository = new ClothingRepository(); // Instantiate your repository here
    }

    public LiveData<List<ClothingItem>> getClothingItemsLiveData() {
        clothingItemsLiveData = repository.getAllClothingItems();
        return  clothingItemsLiveData;
    }

    public LiveData<List<ClothingItem>> searchClothingItems(String searchText, String filter) {
        clothingItemsLiveData = repository.searchClothingItems(searchText, filter);
        return clothingItemsLiveData;
    }

    public void filterClothingItems(String selectedFilter) {
    }

}

