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

public class ListviewItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView itemPhoto;
    private final TextView itemInfo;

    public ListviewItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemPhoto = itemView.findViewById(R.id.item_listview_photo);
        this.itemInfo = itemView.findViewById(R.id.item_listview_textview);
    }

    public void bind(MyItemTest myItemTest, boolean isTwoPane) {
        if (myItemTest.getPhoto().equals("1")) {
            Glide.with(this.itemPhoto.getContext())
                    .load("https://cdn.pixabay.com/photo/2017/08/30/01/05/milky-way-2695569_960_720.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemPhoto);
        } else {
            Glide.with(this.itemPhoto.getContext())
                    .load("https://cdn.pixabay.com/photo/2016/09/05/18/54/texture-1647380_960_720.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemPhoto);
        }
        itemInfo.setText(myItemTest.getInfo());

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
