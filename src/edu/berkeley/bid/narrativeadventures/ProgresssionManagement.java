package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;

import edu.berkeley.bid.narrativeadventures.io.ContactListAgentSource;
import edu.berkeley.bid.narrativeadventures.io.IconStorage;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Mission;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Role;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/** 
 * The screen is  being updated everytime there is a change in narrative, in role or when new mission is created
 * @author cusgadmin
 *
 */
public class ProgresssionManagement extends Activity  {
    ProgressionArrayAdapter roleAdapter;
    AgentArrayAdapter adapter2;
    IconArrayAdapter adapter3;
    SimpleExpandableListAdapter adaptador;
    MissionArrayAdapter missionAdapter;   
    
    private int mRuntimeOrientation;
    private boolean mDisableScreenRotation;
    private Role currentRole;
    private int currentRolePosition;
    private int currentAgentPosition;
    private int currentMissionPosition;
    private Agent currentAgent;  
    private Narrative currentNarrative;
    
//    private static final Mission newMission = new Mission();
//    static {
//        newMission.description = "[ NEW MISSION ]";
//    }
    
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
    
    public void update(){
        //Get the list of agents in the current narrative
        NAApp application = (NAApp)getApplication();
        currentNarrative = application.currentProblem.narrative;  // set current narrative
        final String[] listaproblems = new String[application.runningProblems.size()];              
        TextView prolog = (TextView) this.findViewById(R.id.progManaPlotDesc);  //set current prolog layout
        prolog.setText(currentNarrative.prolog);  //write current prolog
        Spinner spinner = (Spinner) findViewById(R.id.progManaNarrSele);  //set narrative selector
        for (int i=0; i<application.runningProblems.size(); i++) {
            listaproblems[i] = application.runningProblems.get(i).narrative.title;
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaproblems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        //application.runningProblems.get(0).narrative.title;
        //ArrayAdapter.createFromResource(this, application.runningProblems, android.R.layout.simple_spinner_item);                
        roleAdapter = new ProgressionArrayAdapter(getApplicationContext(), R.layout.twoicononrow, currentNarrative.agents);  //set role adapter
//        if (currentRole == null) {
  //          currentRole = roleAdapter.getItem(0).roles.get(0);
    //    }
        ListView roleList = (ListView) this.findViewById(R.id.progManaPers);  //set role list layout
        roleList.setAdapter(roleAdapter);     
        roleList.setSelection(0);
        currentAgent = currentNarrative.agents.get(currentAgentPosition);
        currentRole = currentNarrative.agents.get(currentAgentPosition).roles.get(0); // ASSUMING ONLY ONE ROLE!!!
        missionAdapter = new MissionArrayAdapter(getApplicationContext(), R.layout.oneiconrow, currentRole.missions);
        ListView missionList = (ListView) this.findViewById(R.id.progManaMissList);
        missionList.setAdapter(missionAdapter);  
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.progmana);
        
        update();
 
        ListView roleList = (ListView) this.findViewById(R.id.progManaPers);
        ListView missionList = (ListView) this.findViewById(R.id.progManaMissList);
        Spinner spinnerNarr = (Spinner) this.findViewById(R.id.progManaNarrSele);
        
        spinnerNarr.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           //     currentNarrative = (Narrative) parent.getItemAtPosition(pos); 
            }
            public void onNothingSelected(AdapterView parent){
             // Do nothing   
            }
        });
            
        //missionList.setAdapter(missionAdapter);     
        missionList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
  //              Mission missionSelected = (Mission) parent.getItemAtPosition(position);
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
              return true;
            }
        });        

        roleList.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               currentRole = ((Agent) parent.getItemAtPosition(position)).roles.get(0);
             //  currentRolePosition = position;
               currentAgentPosition = position;
               Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
               v.vibrate(100);
               update();
           }
        });
        
        Button newMission = (Button) findViewById(R.id.progManaButt);
            newMission.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    newMission(); //Dialogue to intro new mission
                }
            });
    }
    
 
    
    public void newMission(){
        //Dialogue to create mission
        final Mission newMision = new Mission();
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.inputtexticon, null);
        ListView lv1 = (ListView) dialoglayout.findViewById(R.id.icons);
        adapter3 = new IconArrayAdapter(getApplicationContext(), R.layout.onlyiconrow, IconStorage.loadIcons(this));
        lv1.setAdapter(adapter3);     
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ImageView icon = (ImageView) view.findViewById(R.id.icon);
                Drawable d = icon.getDrawable();
                Bitmap b = ((BitmapDrawable)d).getBitmap();
                newMision.icon = IconStorage.toBytes(b);
            }
        });
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(dialoglayout);
        adb.setTitle("NEW MISSION");     
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editing = (EditText) dialoglayout.findViewById(R.id.edit);
                newMision.description = editing.getText().toString();
                currentRole.missions.add(newMision);
                update();
            }
        }); 
        adb.setNegativeButton("Cancel", null);
        adb.show();
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
