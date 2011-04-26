package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NarrativeAdventures extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //TODO connect to cloud and update narrative list
        //TODO load narratives
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