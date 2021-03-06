package de.nikos410.moa.radio;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import de.nikos410.moa.radio.model.RadioStation;

public class RadioActivity extends AppCompatActivity {

    private static MediaPlayer PLAYER;

    private RadioStation radioStation;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        final Bundle bundle = getIntent().getExtras();
        radioStation = RadioStation.fromBundle(bundle);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        webView.loadUrl(radioStation.getWebsiteUrl());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isNull(PLAYER)) {
            startPlaying();
        } else {
            resume();
        }
    }

    @Override
    public void onDestroy() {
        stopPlaying();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        pause();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (nonNull(webView) && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void startPlaying() {

        PLAYER = new MediaPlayer();

        try {
            PLAYER.setDataSource(radioStation.getStreamUrl());
        } catch (IOException e) {
            throw new IllegalStateException("Could not open stream URL " + radioStation.getStreamUrl(), e);
        }

        PLAYER.setOnPreparedListener(MediaPlayer::start);
        PLAYER.prepareAsync();
        Toast.makeText(getApplicationContext(),"The radio stream will start playing in a few seconds.",Toast.LENGTH_LONG).show();
    }

    private void stopPlaying() {

        PLAYER.stop();
        PLAYER.release();
        PLAYER = null;
    }

    private void pause() {

        if (PLAYER.isPlaying()) {
            PLAYER.pause();
        }
    }

    private void resume() {
        PLAYER.start();
    }
}
