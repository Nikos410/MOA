package de.nikos410.moa.listmenu;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findById(R.id.listview, ListView.class);
        final ListAdapter listAdapter = buildListAdapter();
        listView.setAdapter(listAdapter);
    }

    private ListAdapter buildListAdapter() {

        final List<String> listItems = buildListItems();
        return new LoggingArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
    }

    private List<String> buildListItems() {

        return IntStream.range(0, 10_000)
                .boxed()
                .map(i -> "ListItem " + i)
                .collect(toList());
    }

    private <T extends View> T findById(@IdRes int id, Class<T> type) {

        final View view = findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException(format("View with ID {0} not found.", id));
        }

        if (type.isAssignableFrom(view.getClass())) {
            return type.cast(view);
        } else {
            throw new IllegalArgumentException(format("Expected view with ID {0} to be of type {1}, but was {2}",
                    id, type, view.getClass()));
        }
    }

    private static class LoggingArrayAdapter<T> extends ArrayAdapter<T> {

        private static final Logger LOG = Logger.getLogger(LoggingArrayAdapter.class.getSimpleName());

        public LoggingArrayAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View previousView, @NonNull ViewGroup parent) {

            final boolean isNewView = isNull(previousView);

            final CharSequence previousText = Optional.ofNullable(previousView)
                    .map(TextView.class::cast)
                    .map(TextView::getText)
                    .orElse(null);

            final TextView nextView = (TextView) super.getView(position, previousView, parent);
            final CharSequence nextText = nextView.getText();

            final boolean isSameInstance = previousView == nextView;
            final boolean isSameContent = Objects.equals(previousText, nextText);
            LOG.info(format("position {0} | new view: {1} | same instance: {2} | same content: {3}",
                    position, isNewView, isSameInstance, isSameContent));

            return nextView;
        }
    }
}