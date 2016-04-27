package pl.oldzi.smuggler.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.R;

public class SmugglerRecyclerAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemList;
    private boolean inBossMode;

    public SmugglerRecyclerAdapter(List<Item> itemList, boolean inBossMode) {
        this.itemList = itemList;
        this.inBossMode = inBossMode;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Item item = itemList.get(position);
        if(inBossMode)
        holder.name.setText(item.getCodename());
        else holder.name.setText(item.getName());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
