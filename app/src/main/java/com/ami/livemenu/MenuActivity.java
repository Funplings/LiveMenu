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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuActivity extends AppCompatActivity {
//    ImageView menuView;
//    Set<Object> ItemButtons = new HashSet<>();
//    private ConstraintLayout layout;
//    Button b;
    List<String> foodList;
    ListView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menu List");
        Bitmap bitmap = ImageHolder.holder.getBitmap();
        processBitmap(bitmap);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        layout = findViewById(R.id.menuLayout);
//        menuView = (ImageView) findViewById(R.id.menuView);
//        menuView.setImageBitmap(bitmap);

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
                                menuList = (ListView) findViewById(R.id.menuList);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this, R.layout.list, foodList);
                                menuList.setAdapter(adapter);
                                menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                        Cursor cursor = (Cursor) menuList.getItemAtPosition(position);
//                                        iD = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
//                                        //Toast.makeText(getActivity(), iD + "", Toast.LENGTH_LONG).show();
                                        Intent result = new Intent(getApplicationContext(), ImageViewer.class);
                                        result.putExtra("term", foodList.get(position));
                                        // intent.putExtra("ID", iD);
                                        startActivity(result);

                                    }
                                });
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

        foodList = new ArrayList<>();
        if (blocks.size() == 0) {
            Log.i(null, "No text found");
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    foodList.add(elements.get(k).getText());
                }
            }
        }
    }


}
