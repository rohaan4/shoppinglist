package uk.ac.le.co2103.part2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<ShoppingList> shoppingLists;
    private Context context;

    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.textViewShoppingListName.setText(shoppingList.getName());
        // Set image using shoppingList.getImageUri() here, consider using a library like Glide or Picasso

        //Activity 3
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(shoppingLists.get(position));
                }
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            if (longClickListener != null) {
                return longClickListener.onItemLongClick(holder.getAdapterPosition());
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewShoppingListName;
        ImageView imageViewShoppingList;

        ViewHolder(View itemView) {
            super(itemView);
            textViewShoppingListName = itemView.findViewById(R.id.textViewShoppingListName);
            imageViewShoppingList = itemView.findViewById(R.id.imageViewShoppingList);
        }
    }

    //Activity 3
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ShoppingList shoppingList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //Activity 4
    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged(); // Notify the adapter of the change in dataset
    }
}

