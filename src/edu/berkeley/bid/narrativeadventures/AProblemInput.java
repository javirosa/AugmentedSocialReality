package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Problem;

public class AProblemInput extends Activity {
    private static final String[] places = { "Home", "School", "Work",
            "Friend's place", "Street", "Public places", "Restaurant", "Other" };

    /**
     * Edits the problem in currentProblem If we want to create a new Problem
     * then we just set it to be a new Problem before this starts When this
     * screen is first started the currentProblem should be a new problem if we
     * are creating a new one
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probdefix2);

        // Initialize place locations
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        Spinner listPlaces = (Spinner) findViewById(R.id.probDefiPlacFill);
        for (int i = 0; i < places.length; i++) {
            adapter1.add(places[i]);
        }
        listPlaces.setAdapter(adapter1);

        // To Social Selection button
        Button goToSociSele = (Button) findViewById(R.id.probDefiToSoci); 
        goToSociSele.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.setEnabled(false);
                saveToCurrentProblem();
                Intent i = new Intent(view.getContext(), SocialSelection.class);
                startActivity(i);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        loadFromCurrentProblem();
    }

    /**
     * Reads the values from the screen and sets them in currentProblem
     * 
     * @return
     */
    private void saveToCurrentProblem() {
        // When this screen is first started the currentProblem should be a new
        // problem
        Problem p = ((NAApp) getApplication()).currentProblem;
        EditText situation = (EditText) findViewById(R.id.probDefiNameFill);
        EditText description = (EditText) findViewById(R.id.probDefiDescFill);
        Spinner place = (Spinner) findViewById(R.id.probDefiPlacFill);
        EditText people = (EditText) findViewById(R.id.probDefiPeopFill);
        SeekBar difficulty = (SeekBar) findViewById(R.id.probDefiRateFill);

        p.situation = situation.getText().toString();
        p.description = description.getText().toString();
        p.place = (String) place.getAdapter().getItem(
                place.getLastVisiblePosition());
        p.people = people.getText().toString();
        p.difficulty = difficulty.getProgress();
    }

    /**
     * Takes input from the gui and loads it into the currentProblem
     */
    private void loadFromCurrentProblem() {
        Problem p = ((NAApp) getApplication()).currentProblem;
        EditText situation = (EditText) findViewById(R.id.probDefiNameFill);
        EditText description = (EditText) findViewById(R.id.probDefiDescFill);
        Spinner place = (Spinner) findViewById(R.id.probDefiPlacFill);
        EditText people = (EditText) findViewById(R.id.probDefiPeopFill);
        SeekBar difficulty = (SeekBar) findViewById(R.id.probDefiRateFill);

        // Set screen
        difficulty.setMax(100);
        situation.setText(p.situation);
        description.setText(p.description);
        for (int idx = 0; idx < places.length; idx++) {
            if (places[idx].equals(p.place)) {
                place.setSelection(idx);
                break;
            }
        }
        people.setText(p.people);
        difficulty.setProgress(p.difficulty);
    }
}
