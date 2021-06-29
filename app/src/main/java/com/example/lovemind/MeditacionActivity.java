package com.example.lovemind;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MeditacionActivity extends AppCompatActivity {
    String url = "<iframe width=\"405\" height=\"250\"" +
            " src=\"https://www.youtube.com/embed/IShkpOm63gg\" title=\"YouTube video player" +
            "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write;" +
            " encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
            "<body style=\"margin: 0; padding: 0\">";
    WebView WebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditacion);


        WebView web = (WebView) findViewById(R.id.webView);

        web.setWebViewClient(new WebViewClient());
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.loadData(url,"text/html",null);

    }

}