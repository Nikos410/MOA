package de.nikos410.moa.radio.util;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

import java.text.MessageFormat;

public final class ViewUtils {

    private ViewUtils() {}

    public static <T extends View> T findById(Activity activity, @IdRes int id, Class<T> type) {

        final View view = activity.findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException(MessageFormat.format("View with ID {0} not found.", id));
        }

        if (type.isAssignableFrom(view.getClass())) {
            return type.cast(view);
        } else {
            throw new IllegalArgumentException(MessageFormat.format("Expected view with ID {0} to be of type {1}, but was {2}", id, type, view.getClass()));
        }
    }
}
