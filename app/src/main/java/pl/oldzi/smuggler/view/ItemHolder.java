package pl.oldzi.smuggler.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pl.oldzi.smuggler.R;

public class ItemHolder extends RecyclerView.ViewHolder {
    protected TextView name;
    protected TextView quantity;

    public ItemHolder(View view) {
        super(view);
        this.name = (TextView) view.findViewById(R.id.nameInRow);
        this.quantity = (TextView) view.findViewById(R.id.quantityInRow);
    }
}
