package com.ami.livemenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class ImageViewer extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        webview = (WebView) findViewById(R.id.webview);
        String added = getIntent().getExtras().getString("term");
        getSupportActionBar().setTitle(added);
        String searchString = "https://www.google.com/search?tbm=isch&q=" + URLEncoder.encode(added);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(searchString);

    }
}
