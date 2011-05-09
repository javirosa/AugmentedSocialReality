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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
    private Role currentRole;
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
        // Get the list of agents in the current narrative
        NAApp application = (NAApp) getApplication();
        currentNarrative = application.currentProblem.narrative;
        final String[] listaproblems = new String[application.runningProblems
                .size()];
        TextView prolog = (TextView) this.findViewById(R.id.roleAssiPlotDesc);
        prolog.setText(currentNarrative.prolog);
        Spinner spinner = (Spinner) findViewById(R.id.roleAssiNarrSele);
        for (int i = 0; i < application.runningProblems.size(); i++) {
            listaproblems[i] = application.runningProblems.get(i).narrative.title;
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listaproblems);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        // application.runningProblems.get(0).narrative.title;
        // ArrayAdapter.createFromResource(this, application.runningProblems,
        // android.R.layout.simple_spinner_item);
        agentAdapter = new AgentArrayAdapter(getApplicationContext(),
                R.layout.oneiconrow, currentNarrative.agents);
        ListView agentList = (ListView) this.findViewById(R.id.roleAssiPers);
        agentList.setAdapter(agentAdapter);
        agentList.setSelection(currentAgentPosition);
        currentAgent = currentNarrative.agents.get(currentAgentPosition);
        roleAdapter2 = new RoleArrayAdapter2(getApplicationContext(),
                R.layout.oneiconrow, currentAgent.roles);
        // if (currentMission == null) {
        // currentMission = roleAdapter2.getItem(0).missions.get(0);
        // }
        ListView roleList = (ListView) this.findViewById(R.id.roleAssiNameIn);
        roleList.setAdapter(roleAdapter2);
        roleList.setSelection(currentRolePosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.roleassign);

        update();

        ListView actorList = (ListView) this.findViewById(R.id.roleAssiPers);
        ListView roleList = (ListView) this.findViewById(R.id.roleAssiNameIn);
        Spinner spinnerNarr = (Spinner) this
                .findViewById(R.id.roleAssiNarrSele);

        spinnerNarr.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
                // currentNarrative = (Narrative) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing
            }
        });
        /*
         * roleList.setOnItemLongClickListener(new OnItemLongClickListener() {
         * 
         * @Override public boolean onItemLongClick(AdapterView<?> parent, View
         * view, int position, long id) { Vibrator v = (Vibrator)
         * getSystemService(Context.VIBRATOR_SERVICE); v.vibrate(100); return
         * true; } });
         */
        actorList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                currentAgent = (Agent) parent.getItemAtPosition(position);
                currentAgentPosition = position;
                // currentRole = ((Agent)
                // parent.getItemAtPosition(position)).roles.get(0);
                // currentRolePosition = 0;
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
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
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
