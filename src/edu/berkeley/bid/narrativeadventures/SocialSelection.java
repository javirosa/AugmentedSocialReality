package edu.berkeley.bid.narrativeadventures;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import edu.berkeley.bid.narrativeadventures.io.ContactListAgentSource;
import edu.berkeley.bid.narrativeadventures.model.Agent;

public class SocialSelection extends Activity {
    // int posi;
    // private TextView selection;
    private List<Agent> personaListIn;
    private List<Agent> personaListOut;
    AgentArrayAdapter adapterIn;
    AgentArrayAdapter adapterOut;

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
                else if (display.getWidth() < display.getHeight())
                    orientation = Configuration.ORIENTATION_PORTRAIT;
                else
                    orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    /** Called when the activity is first created and also when ROTATED!!. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.socisele);

        // Load agents from contact list and remove them if they are already in
        // the
        // narrative.
        ContactListAgentSource agentsource = new ContactListAgentSource(this);
        personaListOut = agentsource.getAgents();
        for (Agent agent : personaListOut) {
            Log.d("AgentSource", agent.name);
        }
        personaListIn = ((NAApp) getApplication()).currentProblem.narrative.agents;
        personaListOut.removeAll(personaListIn);

        // Get data for people included (actors)
        adapterIn = new AgentArrayAdapter(getApplicationContext(),
                R.layout.oneiconrow, personaListIn);
        ListView lvIn = (ListView) this.findViewById(R.id.sociSeleIn);
        lvIn.setAdapter(adapterIn);

        // Get data for people excluded (not acting)
        adapterOut = new AgentArrayAdapter(getApplicationContext(),
                R.layout.oneiconrow, personaListOut);
        ListView lvOut = (ListView) this.findViewById(R.id.sociSeleOut);
        lvOut.setAdapter(adapterOut);

        // Move from actors (included people) to not acting (non included
        // people)
        lvIn.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Agent personaSelected = (Agent) parent
                        .getItemAtPosition(position);
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(300);
                adapterOut.insert(personaSelected, 0);
                // adapterOut.add(personaSelected);
                adapterIn.remove(personaSelected);
                adapterIn.notifyDataSetChanged();
                adapterOut.notifyDataSetChanged();
                v.cancel();
                // moving(personaSelected); //Dialogue to confirm move
            }
        });

        // Move from actors (not included people) to not acting (included
        // people)
        lvOut.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Agent personaSelected = (Agent) parent
                        .getItemAtPosition(position);
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(300);
                // moving(personaSelected); //Dialogue to confirm move
                adapterIn.insert(personaSelected, 0);
                // adapterIn.add(personaSelected);
                adapterOut.remove(personaSelected);
                adapterIn.notifyDataSetChanged();
                adapterOut.notifyDataSetChanged();
                v.cancel();
            }
        });

        // Implementation of the 2-dimensional social-selector
        ImageButton dot = (ImageButton) findViewById(R.id.sociSeleDot);
        dot.setOnTouchListener(new View.OnTouchListener() {
            private final static int START_DRAGGING = 0;
            private final static int STOP_DRAGGING = 1;
            private int status;

            // TextView comment = (TextView)
            // findViewById(R.id.sociSeleNarrTitl);
            @Override
            public boolean onTouch(View view, MotionEvent me) {
                // comment.setText("hope never ends");
                if (me.getAction() == MotionEvent.ACTION_DOWN) {
                    status = START_DRAGGING;
                    // comment.setText("START DRAGGING");
                }
                if (me.getAction() == MotionEvent.ACTION_UP) {
                    status = STOP_DRAGGING;
                    // comment.setText("STOP DRAGGING");
                } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
                    if (status == START_DRAGGING) {
                        AbsoluteLayout papa = (AbsoluteLayout) findViewById(R.id.sociSeleToucPad);
                        papa.invalidate();
                        int testx = view.getLeft();
                        int testy = view.getTop();
                        float posx = me.getX() - view.getWidth() / 2;
                        float posy = me.getY() - view.getWidth() / 2;
                        // comment.setText("DRAGGING: "+ (int) posx + " " +
                        // (int) posy + "test: " + testx + " " + testy);
                        if (posy + testy <= 0) {
                            view.offsetTopAndBottom(-testy);
                        } else if (posy + testy > papa.getHeight()
                                - view.getHeight()) {
                            view.offsetTopAndBottom(papa.getHeight()
                                    - view.getHeight() - testy);
                        } else {
                            view.offsetTopAndBottom((int) posy);
                        }
                        if (posx + testx <= 0) {
                            view.offsetLeftAndRight(-testx);
                        } else if (posx + testx > papa.getWidth()
                                - view.getWidth()) {
                            view.offsetLeftAndRight(papa.getWidth()
                                    - view.getWidth() - testx);
                        } else {
                            view.offsetLeftAndRight((int) posx);
                        }
                    }
                }
                return false;
            }
        });

        // Button to return to narrative selection
        Button ToNarr = (Button) findViewById(R.id.sociSeleToNarr);
        ToNarr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),
                        NarrativeSelection.class);
                view.setEnabled(false);
                startActivity(i);
                finish();
            }
        });

        /*
         * Button to go back to Main Button backToMain = (Button)
         * findViewById(R.id.sociSeleToMain); backToMain.setOnClickListener(new
         * View.OnClickListener() { public void onClick(View view) { finish(); }
         * });
         */
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        // selection.setText(items[position]+" was sent to other list");
        // selection.setText("");
    }

    /*
     * public void moving(Agent personMoving){ //Dialogue to confirm move
     * AlertDialog.Builder adb=new AlertDialog.Builder(SocialSelection.this);
     * adb.setTitle("Confirm you want to move this person");
     * adb.setMessage(personMoving.name +" will be moved");
     * adb.setPositiveButton("Ok", null); adb.show(); }
     */

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mDisableScreenRotation) {
            super.onConfigurationChanged(newConfig);
            this.setRequestedOrientation(mRuntimeOrientation);
        } else {
            // filler();
            mRuntimeOrientation = this.getScreenOrientation();
            super.onConfigurationChanged(newConfig);
        }
    }
}
