package c20321466.softwarepatterns.clothingstore.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ClothingItem implements Parcelable{
    private String id;
    private String title;
    private String manufacturer;
    private double price;
    private String category;
    private int stockLevel;
    private String imageUrl;

    public ClothingItem() {
    }

    public ClothingItem(String id, String title, String manufacturer, double price, String category, int stockLevel, String imageUrl) {
        this.id = id;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.stockLevel = stockLevel;
        this.imageUrl = imageUrl;
    }

    protected ClothingItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        manufacturer = in.readString();
        price = in.readDouble();
        category = in.readString();
        stockLevel = in.readInt();
        imageUrl = in.readString();
    }

    public static final Creator<ClothingItem> CREATOR = new Creator<ClothingItem>() {
        @Override
        public ClothingItem createFromParcel(Parcel in) {
            return new ClothingItem(in);
        }

        @Override
        public ClothingItem[] newArray(int size) {
            return new ClothingItem[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(manufacturer);
        dest.writeDouble(price);
        dest.writeString(category);
        dest.writeInt(stockLevel);
        dest.writeString(imageUrl);
    }
}