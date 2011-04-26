package edu.berkeley.bid.narrativeadventures;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.NarrativeStorage;

public class NarrativeAdventures extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Used to store global state of application. e.g. narrative information
        NAApp naapp = (NAApp)getApplication();
        
        naapp.runningNarratives.add(new Narrative());
        String json1 =NarrativeStorage.toJson(naapp.runningNarratives.get(0)); 
        Log.d("GSON", json1);
        
        try {
            openFileOutput("narrative1",MODE_WORLD_READABLE);
        } catch (FileNotFoundException fne){
            Log.d("GSON","fne");
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
    	
    }
}