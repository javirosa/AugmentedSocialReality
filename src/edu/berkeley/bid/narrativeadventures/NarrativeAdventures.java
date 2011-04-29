package edu.berkeley.bid.narrativeadventures;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import edu.berkeley.bid.narrativeadventures.io.NarrativeStorage;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Mission;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Problem;
import edu.berkeley.bid.narrativeadventures.model.Role;

public class NarrativeAdventures extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Used to store global state of application. e.g. narrative information
        NAApp naapp = (NAApp)getApplication();
        
        Narrative narrative1 = new Narrative();
        Agent narrator = new Agent();
        narrative1.agents.add(narrator);
        narrative1.agents.get(0).name = "foobar";
        narrative1.agents.get(0).roles.add(new Role());
        narrative1.agents.get(0).roles.get(0).description = "descr";
        narrative1.agents.get(0).roles.get(0).missions.add(new Mission());
        narrative1.problem = new Problem();
        narrative1.narrator = narrator;
        narrative1.participant = new Agent();
        
        
        naapp.runningNarratives.add(narrative1);
        String json1 = NarrativeStorage.toJson(naapp.runningNarratives.get(0)); 
        Log.d("GSON", json1);
        
        naapp.possibleNarratives.add(narrative1.cloneNarrative());
        
        
        try {
            openFileOutput("narrative1",MODE_WORLD_READABLE);
            naapp.saveNarratives();
            Log.d("GSON",getFilesDir().toString());
        } catch (FileNotFoundException fne){
            Log.d("GSON","fne");
        } catch (IOException ioe) {
            Log.d("GSON","ioe");
        }
        
        
        //TODO load narratives from disk
        
        //TODO connect to cloud and update narrative list

        //TODO load agents
        
    	Button probDefi = (Button)findViewById(R.id.probDefi);
    	Button sociSele = (Button)findViewById(R.id.sociSele);
    	Button narrSele = (Button)findViewById(R.id.narrSele);
    	Button roleAssi = (Button)findViewById(R.id.roleAssi);
    	Button missMana = (Button)findViewById(R.id.missMana);
    	
    	probDefi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), AProblemInput.class);
				startActivityForResult(i, 0);
			}
		});
    	sociSele.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Intent i = new Intent(v.getContext(), SocialSelection.class);
    			startActivityForResult(i, 0);
    		}
    	});

    	
    	narrSele.setOnClickListener(new View.OnClickListener(){
    	    public void onClick(View v) {
    	        Intent i = new Intent(v.getContext(), NarrativeSelection.class);
    	        startActivityForResult(i, 0);
    	    }
    	});
    }
}