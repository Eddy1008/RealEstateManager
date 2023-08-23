package com.openclassrooms.realestatemanager.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.openclassrooms.realestatemanager.R;

public class DeleteButtonImageView extends AppCompatImageView {

    public DeleteButtonImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public DeleteButtonImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeleteButtonImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageResource(R.drawable.ic_baseline_cancel_24);
        setScaleType(ScaleType.CENTER);

        // set color
        int color = ContextCompat.getColor(getContext(), R.color.colorDeleteButton);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gère l'action de suppression ici
                // Par exemple : appelle une fonction pour supprimer l'élément associé à cette vue
            }
        });
    }
}
