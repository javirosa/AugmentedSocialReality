package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import edu.berkeley.bid.narrativeadventures.adapters.RoleArrayAdapter;
import edu.berkeley.bid.narrativeadventures.adapters.RoleArrayAdapter2;
import edu.berkeley.bid.narrativeadventures.io.IconStorage;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Role;

public class RoleAssignment extends Activity {
    RoleArrayAdapter roleAdapter;
    RoleArrayAdapter2 roleAdapter2;
    AgentArrayAdapter adapter2;
    IconArrayAdapter adapter3;
    SimpleExpandableListAdapter adaptador;
    MissionArrayAdapter missionAdapter;
    AgentArrayAdapter agentAdapter;

    private int mRuntimeOrientation;
    private boolean mDisableScreenRotation;
    private int currentRolePosition;
    private int currentAgentPosition;
    private Agent currentAgent;
    private Narrative currentNarrative;

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
        Button newRole = (Button) this.findViewById(R.id.roleAssiButt);
        TextView prolog = (TextView) this.findViewById(R.id.roleAssiPlotDesc);
        ListView agentList = (ListView) this.findViewById(R.id.roleAssiPers);
        ListView roleList = (ListView) this.findViewById(R.id.roleAssiNameIn);
        
        //Pull data about the current problem and narrative from the application
        NAApplication application = (NAApplication) getApplication();
        currentNarrative = application.currentProblem.narrative;
        
        //Handle a lack of agents
        if (application.currentProblem == null || currentNarrative == null || currentNarrative.agents.size() == 0) {
            newRole.setEnabled(false);
            Context context = getApplicationContext();
            Toast.makeText(context, "Please add agents using the social selection screen.", 10).show();
            return;
        } else {
            newRole.setEnabled(true);
        }
        
        currentAgent = currentNarrative.agents.get(currentAgentPosition);
        
        //Update view
        
        prolog.setText(currentNarrative.prolog);
        // Get the list of agents in the current narrative 
        agentAdapter = new AgentArrayAdapter(getApplicationContext(),
                R.layout.oneiconrow, currentNarrative.agents);
        agentList.setAdapter(agentAdapter);
        agentList.setSelection(currentAgentPosition);
        
        //Get the list of roles for the current agent
        roleAdapter2 = new RoleArrayAdapter2(getApplicationContext(),
                R.layout.oneiconrow, currentAgent.roles);
        roleList.setAdapter(roleAdapter2);
        roleList.setSelection(currentRolePosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.roleassign);

        currentAgentPosition = 0;
        update();

        ListView actorList = (ListView) this.findViewById(R.id.roleAssiPers);
        actorList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                currentAgent = (Agent) parent.getItemAtPosition(position);
                currentAgentPosition = position;
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
                update();
            }
        });

        Button newRoleButt = (Button) findViewById(R.id.roleAssiButt);
        newRoleButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newRole(); // Dialogue to introduce new role
            }
        });
        
        Button toMissionButt = (Button) findViewById(R.id.roleAssiToProg);
        toMissionButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),
                            ProgresssionManagement.class);
                    view.setEnabled(false);
                    startActivity(i);
                    finish();
            }
        });
    }

    public void newRole() {
        // Dialogue to create new role
        final Role newRole = new Role();
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
                newRole.roleIcon = IconStorage.toBytes(b);
            }
        });
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(dialoglayout);
        adb.setTitle("NEW ROLE");
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editing = (EditText) dialoglayout
                        .findViewById(R.id.edit);
                newRole.description = editing.getText().toString();
                currentAgent.roles.add(newRole);
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
