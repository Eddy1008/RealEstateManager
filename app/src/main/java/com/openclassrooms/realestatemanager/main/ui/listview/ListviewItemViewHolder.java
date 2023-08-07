package com.openclassrooms.realestatemanager.main.ui.listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.detail.DetailActivity;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.detail.ui.itemtodetail.ItemFragment;
import com.openclassrooms.realestatemanager.model.MyItemTest;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.List;

public class ListviewItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView itemPhoto;
    private final TextView itemType;
    private final TextView itemAddress;
    private final TextView itemPrice;

    public ListviewItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemPhoto = itemView.findViewById(R.id.item_listview_photo);
        this.itemType = itemView.findViewById(R.id.item_listview_textview_type);
        this.itemAddress = itemView.findViewById(R.id.item_listview_textview_address);
        this.itemPrice = itemView.findViewById(R.id.item_listview_textview_price);
    }

    public void bind(Property property, List<PropertyType> propertyTypeList, boolean isTwoPane) {
        Glide.with(this.itemPhoto.getContext())
                .load(property.getMainPhoto())
                .apply(RequestOptions.circleCropTransform())
                .into(itemPhoto);

        // itemType.setText(String.valueOf(property.getPropertyTypeId()));
        for (PropertyType propertyType : propertyTypeList) {
            if (String.valueOf(propertyType.getId()).equals(String.valueOf(property.getPropertyTypeId()))) {
                itemType.setText(propertyType.getName());
            }
        }

        itemAddress.setText(property.getAddress());
        String myItemPrice = "$" + property.getPropertyPrice();
        itemPrice.setText(myItemPrice);

        /**
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTwoPane) {
                    showItemInFragment(myItemTest);
                } else {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putString("ITEM_INFO", myItemTest.getInfo());
                    myBundle.putString("ITEM_PHOTO", myItemTest.getPhoto());
                    intent.putExtra("BUNDLE_ITEM_TO_DETAIL", myBundle);
                    view.getContext().startActivity(intent);
                }
            }
        });
         */
    }

    private void showItemInFragment(MyItemTest myItemTest) {
        String itemInfo = myItemTest.getInfo();
        String itemPhoto = myItemTest.getPhoto();

        // Update the DetailViewModel with the selected item information.
        DetailViewModel detailViewModel = new ViewModelProvider((AppCompatActivity) itemView.getContext(), ViewModelFactory.getInstance(itemView.getContext())).get(DetailViewModel.class);
        detailViewModel.setMyItemInfo(itemInfo);
        detailViewModel.setMyItemPhoto(itemPhoto);

        // Create an instance of the ItemFragment and set its arguments.
        FragmentManager fragmentManager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
        ItemFragment itemFragment = ItemFragment.newInstance(itemInfo, itemPhoto);

        // Replace the content of the detailContainer with the ItemFragment.
        fragmentManager.beginTransaction()
                .replace(R.id.detailContainer, itemFragment)
                .commit();
    }
}
