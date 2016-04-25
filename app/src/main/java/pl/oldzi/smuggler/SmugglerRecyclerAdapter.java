package pl.oldzi.smuggler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Oldzi on 30.03.2016.
 */
public class SmugglerRecyclerAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemList;
    private Context context;
    private boolean inBossMode;

    public SmugglerRecyclerAdapter(Context context, List<Item> itemList, boolean inBossMode) {
        this.context = context;
        this.itemList = itemList;
        this.inBossMode = inBossMode;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ITA", "Hi im creating onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_layout, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
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
