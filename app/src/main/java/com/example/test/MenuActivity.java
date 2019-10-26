package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    ImageView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bitmap bitmap = ImageHolder.holder.getBitmap();
        menuView = (ImageView) findViewById(R.id.menuView);
        menuView.setImageBitmap(bitmap);
    }
}
