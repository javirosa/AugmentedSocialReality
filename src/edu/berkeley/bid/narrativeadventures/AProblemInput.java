package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;

import edu.berkeley.bid.narrativeadventures.model.Problem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


public class AProblemInput extends Activity {
	private Problem problemOpen;
	private static final String[] places = {"home", "school", "work", "friend's place", "street", "public places", "restaurant", "other"};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.probdefix2);
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		Spinner listPlaces = (Spinner) findViewById(R.id.probDefiPlacFill);
		for (int i=0; i<places.length; i++) {
            adapter1.add(places[i]);
        }       
		
		listPlaces.setAdapter(adapter1);
		
		//Back to Main
		Button backToMain = (Button) findViewById(R.id.probDefiToMain); //Button to go back to Main
		backToMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		//To Social Selection
		Button goToSociSele = (Button) findViewById(R.id.probDefiToSoci); //Button to navigate to Social Selection
		goToSociSele.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                problemOpen = populate();               
                TextView textos = (TextView) findViewById(R.id.probDefiNameTag);
    //            textos.setText(problemOpen.description + " " + problemOpen.people + " " + problemOpen.situation + " " + problemOpen.place + " " + problemOpen.difficulty);
			    Intent i = new Intent(view.getContext(), SocialSelection.class);
				startActivityForResult(i, 0);
				finish();
			}
		});
	}
	
	public Problem populate(){
	    Problem p = new Problem();
	    final EditText situation = (EditText) findViewById(R.id.probDefiNameFill);
	    final EditText description = (EditText) findViewById(R.id.probDefiDescFill);
	    final Spinner place = (Spinner) findViewById(R.id.probDefiPlacFill);
	    final EditText people = (EditText) findViewById(R.id.probDefiPeopFill);
	    final SeekBar difficulty = (SeekBar) findViewById(R.id.probDefiRateFill);
	    
	    p.situation = situation.getText().toString();
	    p.description = description.getText().toString();
	    p.place = (String) place.getAdapter().getItem(place.getLastVisiblePosition());
	    p.people = people.getText().toString();
	    p.difficulty = difficulty.getProgress();
	    return p;
	}
	
}
