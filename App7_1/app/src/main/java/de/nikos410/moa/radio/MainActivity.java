package de.nikos410.moa.radio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import de.nikos410.moa.radio.model.RadioStation;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOG = Logger.getLogger(MainActivity.class.getSimpleName());

    private static final List<RadioStation> STATIONS = new LinkedList<>();

    static {
        STATIONS.add(new RadioStation("1Live", "https://www1.wdr.de/radio/1live/index.html", "https://wdr-1live-live.icecast.wdr.de/wdr/1live/live/mp3/128/stream.mp3"));
        STATIONS.add(new RadioStation("WDR 2", "https://www1.wdr.de/radio/wdr2/index.html", "https://wdr-wdr2-muensterland.icecast.wdr.de/wdr/wdr2/muensterland/mp3/128/stream.mp3"));
        STATIONS.add(new RadioStation("DLF", "https://www.deutschlandfunk.de", "https://st01.sslstream.dlf.de/dlf/01/128/mp3/stream.mp3?aggregator=web"));
        STATIONS.add(new RadioStation("RadioWMW", "https://www.radiowmw.de", "https://radiowmw-ais-edge-4008-fra-dtag-cdn.cast.addradio.de/radiowmw/live/mp3/high"));
    }

    private static final String CONTEXT_MENU_START_PLAYING = "Start playing";
    private static final String CONTEXT_MENU_DISPLAY_WEBSITE = "Display website";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView stationList = findViewById(R.id.stationlist);

        stationList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, STATIONS));

        stationList.setOnItemClickListener(
                (parent, view, position, id) -> {
                    if (position < STATIONS.size()) {
                        startPlaying(STATIONS.get(position));
                    } else {
                        throw new IllegalStateException("Invalid station index.");
                    }
                }
        );

        registerForContextMenu(stationList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.stationlist) {
            final AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            final int position = adapterContextMenuInfo.position;

            menu.add(0, position, 0, CONTEXT_MENU_START_PLAYING);
            menu.add(0, position, 1, CONTEXT_MENU_DISPLAY_WEBSITE);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final RadioStation station = STATIONS.get(item.getItemId());
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle().equals(CONTEXT_MENU_START_PLAYING)) {
            startPlaying(station);
        } else if (item.getTitle().equals(CONTEXT_MENU_DISPLAY_WEBSITE)) {
            displayWebsite(station, menuInfo.targetView);
        }

        return true;
    }

    private void startPlaying(RadioStation radioStation) {

        final Intent intent = new Intent(this, RadioActivity.class);
        intent.putExtras(radioStation.toBundle());

        LOG.info("Starting RadioActivity.");
        startActivity(intent);
    }

    private void displayWebsite(RadioStation radioStation, View view) {

        new AlertDialog.Builder(view.getContext())
                .setTitle("Website for " + radioStation)
                .setMessage(Html.fromHtml(MessageFormat.format("<a href='{0}'>{0}</a>", radioStation.getWebsiteUrl()), 0))
                .show();
    }
}
