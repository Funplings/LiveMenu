package com.ami.livemenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuActivity extends AppCompatActivity {
    ImageView menuView;
    Set<Object> ItemButtons = new HashSet<>();
    private ConstraintLayout layout;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = findViewById(R.id.menuLayout);
        Bitmap bitmap = ImageHolder.holder.getBitmap();
        menuView = (ImageView) findViewById(R.id.menuView);
        menuView.setImageBitmap(bitmap);
//        processBitmap(bitmap);
        b = (Button) findViewById(R.id.popup);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i1 = new Intent(MenuActivity.this, FoodImage.class);
                startActivity(i1);
            }
        });
    }
}
