package c20321466.softwarepatterns.clothingstore.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import c20321466.softwarepatterns.clothingstore.R;
import c20321466.softwarepatterns.clothingstore.data.models.BasketItem;

public class BasketItemAdapter extends RecyclerView.Adapter<BasketItemAdapter.BasketItemViewHolder> {

    private List<BasketItem> basketItems;
    private Context context;

    public BasketItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BasketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        return new BasketItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketItemViewHolder holder, int position) {
        BasketItem basketItem = basketItems.get(position);

        holder.textViewTitle.setText(basketItem.getClothingItem().getTitle());
        holder.textViewManufacturer.setText(basketItem.getClothingItem().getManufacturer());
        holder.textViewQuantity.setText(String.valueOf(basketItem.getQuantity()));
        holder.textViewPrice.setText(String.format("€%.2f", basketItem.getClothingItem().getPrice()));

        // Set the item image (using placeholder image for now)
        holder.imageViewItem.setImageResource(R.drawable.default_item_image);
    }

    @Override
    public int getItemCount() {
        return basketItems != null ? basketItems.size() : 0;
    }

    public void setItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
        notifyDataSetChanged();
    }

    static class BasketItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        TextView textViewTitle;
        TextView textViewManufacturer;
        TextView textViewQuantity;
        TextView textViewPrice;

        public BasketItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewBasketItem);
            textViewTitle = itemView.findViewById(R.id.textViewBasketItemTitle);
            textViewManufacturer = itemView.findViewById(R.id.textViewBasketItemManufacturer);
            textViewQuantity = itemView.findViewById(R.id.textViewBasketItemQuantityValue);
            textViewPrice = itemView.findViewById(R.id.textViewBasketItemPrice);
        }
    }
}