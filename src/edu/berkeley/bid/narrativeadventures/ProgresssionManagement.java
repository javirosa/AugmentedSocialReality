package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import edu.berkeley.bid.narrativeadventures.io.ContactListAgentSource;
import edu.berkeley.bid.narrativeadventures.model.Agent;

public class ProgresssionManagement extends Activity {
    ProgressionArrayAdapter adapter;
    private List<Agent> agentList;
    
    private int mRuntimeOrientation;
    private boolean mDisableScreenRotation;

    protected int getScreenOrientation() {
       Display display = getWindowManager().getDefaultDisplay();
       int orientation = display.getOrientation();

       if (orientation == Configuration.ORIENTATION_UNDEFINED) {
          orientation = getResources().getConfiguration().orientation;

          if (orientation == Configuration.ORIENTATION_UNDEFINED) {
             if (display.getWidth() == display.getHeight())
                orientation = Configuration.ORIENTATION_SQUARE;
             else if(display.getWidth() < display.getHeight())

                orientation = Configuration.ORIENTATION_PORTRAIT;
             else
                orientation = Configuration.ORIENTATION_LANDSCAPE;
             }
          }
       return orientation;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Get the list of agents in the current narrative
        NAApp application = (NAApp)getApplication();
        if (application.currentProblem == null) {
            //TODO Popup narrative creation box or should we never get here?
            agentList = new ContactListAgentSource(this).getAgents();
        } else {
            agentList = application.currentProblem.narrative.agents; 
        }
        
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.progmana);
        
        adapter = new ProgressionArrayAdapter(getApplicationContext(), R.layout.twoicononrow, agentList);       
        ListView lv = (ListView) this.findViewById(R.id.progManaPers);
        lv.setAdapter(adapter);     
        
    Button backToMain = (Button) findViewById(R.id.progManaButt);
    backToMain.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(),
                    NarrativeAdventures.class);
            startActivityForResult(i, 0);
            finish();
            }
        });
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
       if (mDisableScreenRotation) {
          super.onConfigurationChanged(newConfig);
          this.setRequestedOrientation(mRuntimeOrientation);
       } else {
          
          mRuntimeOrientation = this.getScreenOrientation();
          super.onConfigurationChanged(newConfig);
       }
    }
}
