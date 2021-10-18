package de.nikos410.moa.pizza;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void salamiClicked(View view) {
        showSelection(view, getText(R.string.salami));
    }

    public void thunfischClicked(View view) {
        showSelection(view, getText(R.string.thunfisch));
    }

    public void margheritaClicked(View view) {
        showSelection(view, getText(R.string.margherita));
    }

    private void showSelection(View view, CharSequence selectedVariant) {
        final Context context = view.getContext();
        final String message = MessageFormat.format(">>> {0} <<<", selectedVariant);

        Toast.makeText(context, message, LENGTH_SHORT).show();
    }

    public void freeChoiceClicked(View view) {
        final Context context = view.getContext();

        new AlertDialog.Builder(context)
                .setTitle(getText(R.string.free_choice_dialog_title))
                .setMultiChoiceItems(buildFreeChoiceOptions(), null, null)
                .setPositiveButton(getText(R.string.free_choice_dialog_positive_button), null)
                .setNegativeButton(getText(R.string.free_choice_dialog_negative_button), null)
                .show();
    }

    private CharSequence[] buildFreeChoiceOptions() {

        final CharSequence[] options = new String[4];
        options[0] = getText(R.string.free_choice_option_salami);
        options[1] = getText(R.string.free_choice_option_cheese);
        options[2] = getText(R.string.free_choice_option_anchovis);
        options[3] = getText(R.string.free_choice_option_ham);
        return options;
    }
}