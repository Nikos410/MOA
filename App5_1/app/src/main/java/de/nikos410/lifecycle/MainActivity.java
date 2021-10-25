package de.nikos410.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOG = Logger.getLogger(MainActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOG.info("onCreate was called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LOG.info("onStart was called.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LOG.info("onRestart was called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LOG.info("onResume was called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LOG.info("onPause was called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LOG.info("onStop was called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LOG.info("onDestroy was called.");
    }

}