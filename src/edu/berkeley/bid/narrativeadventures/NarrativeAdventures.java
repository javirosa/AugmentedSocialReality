package edu.berkeley.bid.narrativeadventures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
        //all activities can call getApplication
        //for some odd reason getFilesDir 
        //  returns null if this is done at the application level
        NAApp naapp = (NAApp)getApplication();
        File possibleNarsDir = new File(getFilesDir(),"possibleNarratives");
        File runningProbsDir = new File(getFilesDir(),"runningProblems");
        possibleNarsDir.mkdirs();
        runningProbsDir.mkdirs();
        
        //TESTING SAVE/LOAD
        //Build a problem
        Problem problem = new Problem();
        
        //Generate a simple narrative with one agent,mission, and role
        Narrative narrative1 = new Narrative();
        Agent narrator = new Agent();
        narrative1.agents.add(narrator);
        narrative1.agents.get(0).name = "foobar";
        Role role = new Role();
        role.description = "descr";
        role.missions.add(new Mission());
        narrative1.agents.get(0).roles.add(role);
        narrative1.roles.add(role.cloneRole());
        narrative1.narrator = narrator;
        narrative1.participant = new Agent();
        
        problem.narrative = narrative1;
        naapp.runningProblems.add(problem);
        naapp.possibleNarratives.add(narrative1.cloneNarrative());
        
        if (!naapp.saveState(possibleNarsDir,runningProbsDir)) {
            Log.d("GSON", "save unsuccessful");
            Toast.makeText(this, "Save Failed", Toast.LENGTH_LONG);
        }
        
        if (! naapp.loadState(possibleNarsDir, runningProbsDir) ) {
            Log.d("GSON", "load unsuccessful");
        }
        
        // END TESTING SAVE/LOAD
        
        //TODO connect to cloud and update narrative list

    	Button probDefi = (Button)findViewById(R.id.probDefi);
    	Button sociSele = (Button)findViewById(R.id.sociSele);
    	Button narrSele = (Button)findViewById(R.id.narrSele);
    	Button roleAssi = (Button)findViewById(R.id.roleAssi);
    	Button progMana = (Button)findViewById(R.id.progMana);
    	
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
        roleAssi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RoleAssignment.class);
                startActivityForResult(i, 0);
            }
        });

        progMana.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProgresssionManagement.class);
                startActivityForResult(i, 0);
            }
        });

    }
}