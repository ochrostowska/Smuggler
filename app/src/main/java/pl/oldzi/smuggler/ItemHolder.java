package pl.oldzi.smuggler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Oldzi on 09.04.2016.
 */
public class ItemHolder extends RecyclerView.ViewHolder {

    protected TextView name;
    protected TextView quantity;

    public ItemHolder(View view) {
        super(view);
        this.name = (TextView) view.findViewById(R.id.nameInRow);
        this.quantity = (TextView) view.findViewById(R.id.quantityInRow);
    }


}
