package c20321466.softwarepatterns.clothingstore.data;

public class ClothingRepository {
    private ClothingItemDao clothingItemDao;

    public ClothingRepository(ClothingItemDao clothingItemDao) {
        this.clothingItemDao = clothingItemDao;
    }

    public void addClothingItem(ClothingItem item, OnCompleteListener<DocumentReference> listener) {
        clothingItemDao.addClothingItem(item, listener);
    }

    public void getClothingItemById(String itemId, OnCompleteListener<DocumentSnapshot> listener) {
        clothingItemDao.getClothingItemById(itemId, listener);
    }

    public void updateClothingItem(String itemId, ClothingItem updatedItem, OnCompleteListener<Void> listener) {
        clothingItemDao.updateClothingItem(itemId, updatedItem, listener);
    }

    public void updateStockLevel(String itemId, int newStockLevel, OnCompleteListener<Void> listener) {
        clothingItemDao.updateStockLevel(itemId, newStockLevel, listener);
    }

    public void deleteClothingItem(String itemId, OnCompleteListener<Void> listener) {
        clothingItemDao.deleteClothingItem(itemId, listener);
    }

    // Additional methods for querying and complex operations can be implemented here
}
