package edu.berkeley.bid.narrativeadventures;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import edu.berkeley.bid.narrativeadventures.adapters.ProblemArrayAdapter;
import edu.berkeley.bid.narrativeadventures.io.ContactListAgentSource;
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

        // Used to store global state of application. e.g. narrative information
        // all activities can call getApplication
        // for some odd reason getFilesDir
        // returns null if this is done at the application level
        NAApplication naapp = (NAApplication) getApplication();
        File possibleNarsDir = new File(getFilesDir(), "possibleNarratives");
        File runningProbsDir = new File(getFilesDir(), "runningProblems");
        possibleNarsDir.mkdirs();
        runningProbsDir.mkdirs();

        // TESTING SAVE/LOAD
        // Build a problem
        Problem problem = new Problem();

        final String[] itemsH = {
                "Finish adding the PERMA section so all may see the rational behind the glory that is our majestic device!",
                "Get us some pizza! The mind of a mad scientist requires nourishment!",
                "Start reading the NIH proposal and figure out how to convince medical proffessionals of the wonders we have to offer!" };
        // {"buy chicken",
        // "cook some healthy food"}
        final String[] itemsA = {
                "Stop the Mission Editor Memory Leak before the phone explodes!",
                "Don't let android beat you. Get text messages sent to all of the peers.",
                "XML is an alien language decipher the codes which represent an android Spinner!" };

        // "make me laugh three times a week",
        // "buy the following items before midnight: 1-food, 2-costumes, 3-boardgames, 4-incense, 5-many videos from the 80's, 6-all the magazines from our old room"};
        // final String[]
        // names={"paul","joseph","rick","ermenegildo","the loco","jim","mary","jeena","roy","my love","mom","dad","gordo"};
        final String[] messages = { "nice work", "i tould you I can do it",
                "i am getting tired!", "this is interesting" };
        // final String[] roles={"the crazy cook", "the wizard of oz",
        // "superwoman", "the math geek"};

        problem.situation = "I am stressed for a final presentation.";
        problem.description = "I have a presentation due for CS264 and I always feel ill prepared no matter what I do or how many buckets of ice cream I eat.";// "I have a lot to study and my test is in 2 days, I have not read all the material and I missed some classes... \n"
                                                                                                                                                               // +
        // "I am freaking out";
        problem.difficulty = 75;
        problem.narrative = new Narrative();
        problem.place = "School";
        problem.people = "Pablo, Javier, Eric";

        // ArrayList<Role> roleslist = new ArrayList<Role>();
        Role role1 = new Role();
        Role role2 = new Role();

        role1.description = "Bring forth the knowledge you have gained and spead it to the world!";
        role1.name = "The Herald";
        Bitmap BMP = BitmapFactory.decodeResource(getResources(),
                R.drawable.herald);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BMP.compress(CompressFormat.PNG, 100, baos);
        role1.roleIcon = baos.toByteArray();

        for (String s : itemsH) {
            Mission m = new Mission();
            m.description = s;
            m.icon = baos.toByteArray();
            m.status = Mission.Status.ASSIGNED;
            m.resources.addAll(Arrays.asList(messages));
            role1.missions.add(m);
        }
        // missions.add(new Mission());
        // missions.get(items.length).description="[ NEW MISSION ]";

        role2 = role1.cloneRole();
        role2.missions = new ArrayList<Mission>();
        for (String s : itemsA) {
            Mission m = new Mission();
            m.description = s;
            m.icon = baos.toByteArray();
            m.status = Mission.Status.ASSIGNED;
            m.resources.addAll(Arrays.asList(messages));
            role2.missions.add(m);
        }
        role2.description = "The archictect is reponsible for the design and implementation of the great discovery. He/She understands the inner workings of your device.";
        role2.name = "Chief Architect";

        BMP = BitmapFactory.decodeResource(getResources(),
                R.drawable.forward_arrow);
        baos = new ByteArrayOutputStream();
        BMP.compress(CompressFormat.PNG, 100, baos);
        role2.roleIcon = baos.toByteArray();

        Narrative narr = problem.narrative;
        // Take two people from the agent list
        ArrayList<Agent> keep = new ContactListAgentSource(this).getAgents();
        narr.agents.add(keep.get(0));
        narr.agents.add(keep.get(1));
        // new ArrayList<Agent>();
        narr.associatedMssns = new ArrayList<Mission>();
        narr.narrator = narr.agents.get(0);
        narr.participant = narr.agents.get(1);
        narr.prolog = "A great mystery has been solved. You must present it to the world at a gathering of great scientists and inventors. It is the key to solving all of humankind's problems.";// "HI This is the description of the narrative that tells everything that is needed to know, and sometimes things that are not needed, to make the outcome really successful and to bla bla bla";
        narr.setting = "NOTHING";
        /*
         * for (int i = 0; i < narr.agents.size(); i++) { if (i % 2 == 1) {
         * narr.agents.get(i).addRole(role1); } else {
         * narr.agents.get(i).addRole(role2); } }
         */
        narr.agents.get(0).addRole(role1);
        narr.agents.get(1).addRole(role2);
        narr.title = "Revelation Presentation";

        problem.narrative = narr;

        NAApplication application = (NAApplication) getApplication();
        application.currentProblem = problem;

        application.runningProblems.add(problem);

        /*
         * Generate a simple narrative with one agent,mission, and role
         * Narrative narrative1 = new Narrative(); Agent narrator = new Agent();
         * narrative1.agents.add(narrator); narrative1.agents.get(0).name =
         * "foobar"; Role role = new Role(); role.description = "descr";
         * role.missions.add(new Mission());
         * narrative1.agents.get(0).roles.add(role);
         * narrative1.roles.add(role.cloneRole()); narrative1.narrator =
         * narrator; narrative1.participant = new Agent();
         * 
         * problem.narrative = narrative1; naapp.runningProblems.add(problem);
         * naapp.possibleNarratives.add(narrative1.cloneNarrative());
         */
        // if (!naapp.saveState(possibleNarsDir,runningProbsDir)) {
        // Log.d("GSON", "save unsuccessful");
        // Toast.makeText(this, "Save Failed", Toast.LENGTH_LONG);
        // }

        // if (! naapp.loadState(possibleNarsDir, runningProbsDir) ) {
        // Log.d("GSON", "load unsuccessful");
        // }

        // END TESTING SAVE/LOAD


        final Button sociSele = (Button) findViewById(R.id.sociSele);
        final Button probDefiExist = (Button) findViewById(R.id.probDefiExist);
        final Button probDefi = (Button) findViewById(R.id.probDefi);
        final Button narrSele = (Button) findViewById(R.id.narrSele);
        final Button roleAssi = (Button) findViewById(R.id.roleAssi);
        final Button progMana = (Button) findViewById(R.id.progMana);
        final Spinner narSpin = (Spinner) findViewById(R.id.narSpin);
        
        ProblemArrayAdapter probAAd = new ProblemArrayAdapter(
                getApplicationContext(), R.id.oneiconrowroot,
                ((NAApplication) getApplication()).runningProblems);
        narSpin.setAdapter(probAAd);
        //probAAd.setDropDownViewResource(R.layout.oneiconrow);
        narSpin.setOnItemSelectedListener( new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                NAApplication naapp = (NAApplication)getApplication();
                naapp.currentProblem = (Problem) ((Spinner)arg0).getSelectedItem();
                sociSele.setEnabled(true);
                probDefiExist.setEnabled(true);
                narrSele.setEnabled(true);
                roleAssi.setEnabled(true);
                progMana.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                NAApplication naapp = (NAApplication)getApplication();
                //The only value behavior is to create a new Problem
                naapp.currentProblem = null;
                sociSele.setEnabled(false);
                probDefiExist.setEnabled(false);
                narrSele.setEnabled(false);
                roleAssi.setEnabled(false);
                progMana.setEnabled(false);
            }
        });
        // On selection set currentProblem to currently selected
        // all additions of narratives is through spinner

        probDefi.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {

                //Create new problem
                ArrayAdapter<Problem> aap = ((ArrayAdapter<Problem>)narSpin.getAdapter());
                aap.add(new Problem());
                narSpin.setSelection(aap.getCount()-1);

                Intent i = new Intent(v.getContext(), AProblemInput.class);
                startActivityForResult(i, 0);
                // TODO if result OK then problem is not canceled then set the new problem as selected
            }
        });
        
        //BUTTONS
        probDefiExist.setOnClickListener(new View.OnClickListener() {
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

        narrSele.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NarrativeSelection.class);
                startActivityForResult(i, 0);
            }
        });
        roleAssi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RoleAssignment.class);
                startActivityForResult(i, 0);
            }
        });

        progMana.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),
                        ProgresssionManagement.class);
                startActivityForResult(i, 0);
            }
        });
    }
}
