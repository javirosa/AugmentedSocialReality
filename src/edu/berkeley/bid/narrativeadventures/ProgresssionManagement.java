package edu.berkeley.bid.narrativeadventures;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.bid.narrativeadventures.adapters.AgentArrayAdapter;
import edu.berkeley.bid.narrativeadventures.adapters.IconArrayAdapter;
import edu.berkeley.bid.narrativeadventures.adapters.MissionArrayAdapter;
import edu.berkeley.bid.narrativeadventures.adapters.WheelProgressionArrayAdapter;
import edu.berkeley.bid.narrativeadventures.io.IconStorage;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Mission;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Role;

/**
 * The screen is being updated everytime there is a change in narrative, in role
 * or when new mission is created
 * 
 * @author cusgadmin
 * 
 */
public class ProgresssionManagement extends Activity {
    WheelProgressionArrayAdapter roleAdapter;
    AgentArrayAdapter adapter2;
    IconArrayAdapter adapter3;
    SimpleExpandableListAdapter adaptador;
    MissionArrayAdapter missionAdapter;

    private int mRuntimeOrientation;
    private boolean mDisableScreenRotation = true;
    private Role currentRole;
    private int currentAgentPosition;
    private Narrative currentNarrative;
    private boolean scrolling;

    protected int getScreenOrientation() {
        Display display = getWindowManager().getDefaultDisplay();
        int orientation = display.getOrientation();

        if (orientation == Configuration.ORIENTATION_UNDEFINED) {
            orientation = getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_UNDEFINED) {
                if (display.getWidth() == display.getHeight())
                    orientation = Configuration.ORIENTATION_SQUARE;
                else if (display.getWidth() < display.getHeight())

                    orientation = Configuration.ORIENTATION_PORTRAIT;
                else
                    orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    public void update() {
        Button newMission = (Button) this.findViewById(R.id.progManaButt);
        TextView prolog = (TextView) this.findViewById(R.id.progManaPlotDesc);
        ListView missionList = (ListView) this.findViewById(R.id.progManaMissList);
        WheelView roleWheel= (WheelView) this.findViewById(R.id.progManaPersWheel); 
        
        // Get the list of agents in the current narrative
        NAApplication application = (NAApplication) getApplication();
        currentNarrative = application.currentProblem.narrative; // set current
                                                                 // narrative
        
        newMission.setEnabled(false);
        //Handle a lack of agents 
        if (application.currentProblem == null || currentNarrative == null || currentNarrative.agents.size() == 0) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please add agents using the social selection screen.", 10).show();
            return;
        } else {
            //Handle a lack of roles
            for (int i = 0; i < currentNarrative.agents.size(); i++) {
                if (currentNarrative.agents.get(i).roles.size()== 0){
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Please add roles to all agents using the role selection screen.", 10).show();
                    return;
                }
            }
        }
        newMission.setEnabled(true);
        
        prolog.setText(currentNarrative.prolog); // write current prolog
        //Get roles
        // ASSUMING ONLY ONE ROLE!!!
        currentRole = 
            currentNarrative.agents.get(currentAgentPosition).roles.get(0); 
        roleAdapter = new WheelProgressionArrayAdapter(getApplicationContext(),
                R.layout.twoicononrow, currentNarrative.agents); 
        roleWheel.setViewAdapter(roleAdapter);
        
        //Get missions
        missionAdapter = new MissionArrayAdapter(getApplicationContext(),
                R.layout.oneiconrow, currentRole.missions);
        missionList.setAdapter(missionAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.progmana);

        WheelView roleWheel= (WheelView) this.findViewById(R.id.progManaPersWheel); 
        roleWheel.setVisibleItems(1);
        roleWheel.setVerticalFadingEdgeEnabled(false);
        roleWheel.setHorizontalFadingEdgeEnabled(false);
        roleWheel.setFadingEdgeLength(0);
        update();

        ListView missionList = (ListView) this
                .findViewById(R.id.progManaMissList);
        missionList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
                return true;
            }
        });

        roleWheel.addChangingListener(new OnWheelChangedListener() {

            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    int position = wheel.getCurrentItem();
                    currentRole = ((Agent) roleAdapter.getItem(position)).roles
                            .get(0);
                    currentAgentPosition = position;
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    update();
                }
            }
        });
        
        roleWheel.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                int position = wheel.getCurrentItem();
                currentRole = ((Agent) roleAdapter.getItem(position)).roles
                        .get(0);
                currentAgentPosition = position;
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
                update();
            }
        });

        Button newMission = (Button) findViewById(R.id.progManaButt);
        newMission.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newMission(); // Dialogue to intro new mission
            }
        });
    }

    public void newMission() {
        // Dialogue to create mission
        final Mission newMision = new Mission();
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout = inflater
                .inflate(R.layout.inputtexticon, null);
        ListView lv1 = (ListView) dialoglayout.findViewById(R.id.icons);
        adapter3 = new IconArrayAdapter(getApplicationContext(),
                R.layout.onlyiconrow, IconStorage.loadIcons(this));
        lv1.setAdapter(adapter3);
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ImageView icon = (ImageView) view.findViewById(R.id.icon);
                Drawable d = icon.getDrawable();
                Bitmap b = ((BitmapDrawable) d).getBitmap();
                newMision.icon = IconStorage.toBytes(b);
            }
        });
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(dialoglayout);
        adb.setTitle("NEW MISSION");
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editing = (EditText) dialoglayout
                        .findViewById(R.id.edit);
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
