package com.ami.livemenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
//    ImageView menuView;
//    Set<Object> ItemButtons = new HashSet<>();
//    private ConstraintLayout layout;
//    Button b;
    List<String> foodList;
    ListView menuList;
//    SyntaxEndpoint endpoint;
//    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menu List");
//        query = getString(R.string.CloudAPIKey);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://language.googleapis.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        endpoint = retrofit.create(SyntaxEndpoint.class);
        for(Bitmap bitmap : ImageHolder.holder.getBitmaps()){
            processBitmap(bitmap);
        }

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        layout = findViewById(R.id.menuLayout);
//        menuView = (ImageView) findViewById(R.id.menuView);
//        menuView.setImageBitmap(bitmap);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageHolder.holder.clean();
        clearPictures();
    }

    private void clearPictures(){
        File dir = new File(Environment.getExternalStorageDirectory()+"/LiveMenu/a");
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
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
                                try {
                                    processTextRecognitionResult(texts);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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

    private void processTextRecognitionResult(FirebaseVisionText texts) throws IOException {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if(foodList == null){
            foodList = new ArrayList<>();
        }
        if (blocks.size() == 0) {
            Log.i(null, "No text found");
            return;
        }
        SyntaxInput input = new SyntaxInput();
        Document doc = new Document();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    final String content = elements.get(k).getText();
                    foodList.add(content);
//                    doc.setContent(content);
//                    input.setDocument(doc);
//                    Call<SyntaxData> call = endpoint.getSyntaxData(input, query);
//                    call.enqueue(new Callback<SyntaxData>(){
//
//                        @Override
//                        public void onResponse(Call<SyntaxData> call, Response<SyntaxData> response) {
////                            boolean verb = false;
////                            boolean noun = false;
////                            for(Token token : response.body().getTokens()){
////                                if(!noun && token.getPartOfSpeech().getTag().compareTo("NOUN") == 0){
////                                    noun = true;
////                                }
////                                else if(token.getPartOfSpeech().getTag().compareTo("VERB") == 0){
////                                    verb = true;
////                                    break;
////                                }
////                            }
////                            if(!verb && noun){
////                                foodList.add(content);
////                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<SyntaxData> call, Throwable t) {
//                            return;
//                        }
//                    });

                }
            }
        }
    }


}
