package com.ami.livemenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class ImageViewer extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * .8);
        int height = (int) (dm.heightPixels * .8);
        getWindow().setLayout(width, height);
        setContentView(R.layout.activity_image_viewer);
        webview = (WebView) findViewById(R.id.webview);
        String added = getIntent().getExtras().getString("term");
        getSupportActionBar().setTitle(added);
        String searchString = "https://www.google.com/search?tbm=isch&q=" + URLEncoder.encode(added);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(searchString);

    }
}
