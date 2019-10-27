package com.ami.livemenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

// Imports from com.ami.livemenu
import com.ami.livemenu.MenuImage.Graphic;

// GMS Tasks Imports
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

// Firebase Imports
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;

// Firebase Vision Imports
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

// Util Imports
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    private MenuImage mMenuImage;
    ImageView img;
    Set<Object> ItemButtons = new HashSet<>();
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);
        layout = findViewById(R.id.constraint);
    }

    public void doSomething(View view) {
        switch(view.getId()){
            case R.id.galleryButton:
                Intent gallery =
                        new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                break;
            case R.id.cameraButton:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Set<String> string = extras.keySet();
            bitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(bitmap);
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Bundle extras = data.getExtras();
            Set<String> string = extras.keySet();

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img.setImageBitmap(bitmap);
        }
        Log.i("MainActivity.java", "about to process");
        processBitmap(bitmap);
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
                                Log.i("MainActivity.java", "processed");
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
