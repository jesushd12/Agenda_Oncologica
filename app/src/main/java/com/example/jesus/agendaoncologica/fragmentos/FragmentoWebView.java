package com.example.jesus.agendaoncologica.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.jesus.agendaoncologica.R;

/**
 * Created by Jesus on 2/15/2016.
 */
public class FragmentoWebView  extends Fragment{
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.informacion_cancer,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Informacion");
        setHasOptionsMenu(true);
        String url = getArguments().getString("url");
        webView = (WebView)v.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
    }
}
