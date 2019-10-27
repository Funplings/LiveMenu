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

    private void processBitmap(Bitmap bitmap){
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                processTextRecognitionResult(texts);
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                // Task failed with an exception
                                e.printStackTrace();
                            }
                        });
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            Log.i(null, "No text found");
            return;
        }

        // Clear items
        for (Object item: ItemButtons) {
            layout.removeView((View) item);
        }
        ItemButtons.clear();

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {

                    // Get element
                    FirebaseVisionText.Element elem = elements.get(k);
                    // Get text
                    final String rawText = elem.getText();

                    // Get button position/size
                    Rect textBox = elem.getBoundingBox();
                    float posX = (float) ((textBox.left + textBox.right) / 2.0);
                    float posY = (float) ((textBox.top + textBox.bottom) / 2.0);
                    int width = textBox.width();
                    int height = textBox.height();

                    // Create button
                    Button b1 = new Button(this);
                    b1.setText(rawText);
                    b1.setX(posX);
                    b1.setY(posY);
                    //b1.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));

                    // Let button onClick show images
                    b1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), rawText, Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add button to set/layout
                    ItemButtons.add(b1);
                    layout.addView(b1);
                }
            }
        }
    }


}
