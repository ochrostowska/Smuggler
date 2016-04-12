package pl.oldzi.smuggler;

import java.io.Serializable;

public class Item implements Serializable{

    //Variables that are in our json
    private int item_id;
    private String codename;
    private String name;
    private int quantity;


    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    @Override
//    public int describeContents() {
//        return this.hashCode();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(item_id);
//        dest.writeString(name);
//        dest.writeString(codename);
//        dest.writeInt(quantity)
//    }
//
//    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
//    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
//        public Item createFromParcel(Parcel in) {
//            return new Item();
//        }
//
//        public Item[] newArray(int size) {
//            return new Item[][size];
//        }
//    };



}