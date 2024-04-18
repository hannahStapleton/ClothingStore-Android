package c20321466.softwarepatterns.clothingstore.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;
import c20321466.softwarepatterns.clothingstore.R;

public class ClothingItemAdapter extends RecyclerView.Adapter<ClothingItemAdapter.ViewHolder> {

    private List<ClothingItem> items;
    private OnItemClickListener listener;

    public void setItems(List<ClothingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothingItem item = items.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemManufacturer.setText(item.getManufacturer());
        holder.itemPrice.setText(String.format("€%.2f", item.getPrice()));
        if (item.getStockLevel() > 0) {
            holder.itemInStock.setText(R.string.in_stock);
            holder.addToBasketButton.setVisibility(View.VISIBLE); // Show add to basket button
        } else {
            holder.itemInStock.setText(R.string.out_of_stock);
            holder.addToBasketButton.setVisibility(View.GONE); // Hide add to basket button
        }
        // Load image using Glide or Picasso (not implemented here)
        // Example: Glide.with(holder.itemView).load(item.getImageUrl()).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setClothingItems(List<ClothingItem> clothingItems) {
    }

    public interface OnItemClickListener {
        void onAddToBasketClick(ClothingItem item);
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemManufacturer;
        TextView itemPrice;
        TextView itemInStock;
        Button addToBasketButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.clothing_item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemManufacturer = itemView.findViewById(R.id.item_manufacturer);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemInStock = itemView.findViewById(R.id.item_in_stock);
            addToBasketButton = itemView.findViewById(R.id.add_to_basket);

            addToBasketButton.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ClothingItem clickedItem = items.get(position);
                        listener.onAddToBasketClick(clickedItem);
                    }
                }
            });
        }
    }
}

