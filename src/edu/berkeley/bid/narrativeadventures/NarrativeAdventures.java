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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        final String[] items={"buy chicken", "cook some healthy food", "send pictures of every family reunion", "get together every saturday to play games", "complete all the puzzles tonight", "send flowers to jenny tomorrow", "do other task without getting upset","eat all the food","take every medication by 6pm",
            "get together with my parents to talk", "make me laugh three times a week", "buy the following items before midnight: 1-food, 2-customs, 3-boardgames, 4-incense, 5-many videos from the 80's, 6-all the magazines from our old room"};
        final String[] names={"paul","joseph","rick","ermenegildo","the loco","jim","mary","jeena","roy","my love","mom","dad","gordo"};
        final String[] messages={"nice work", "i tould you I can do it", "i am getting tired!", "this is interesting"};
        final String[] roles={"the crazy cook", "the wizard of oz", "superwoman", "the math geek"};
        
        problem.situation="I am stressed for a final test";
        problem.description="I have a lot to study and my test is in 2 days, I have not read all the material and I missed some classes... \n" +
        		"I am freaking out";
        problem.difficulty=25;
        problem.narrative=new Narrative();
        problem.place="school";
        
//        ArrayList<Role> roleslist = new ArrayList<Role>();      
        Role role1 = new Role();        
        Role role2 = new Role();
        
        role1.description="This is the role number 1";
        role1.name = "First Role";        
        Bitmap BMP = BitmapFactory.decodeResource(getResources(), R.drawable.back_arrow);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BMP.compress(CompressFormat.PNG, 100, baos);
        role1.roleIcon = baos.toByteArray();

        for (String s:items){
            Mission m = new Mission();
            m.description=s;
            m.icon=baos.toByteArray();
            m.status=Mission.Status.ASSIGNED;
            m.resources.addAll(Arrays.asList(messages));
            role1.missions.add(m);
        }        
//      missions.add(new Mission());
//      missions.get(items.length).description="[ NEW MISSION ]";
        
        role2=role1.cloneRole();
        role2.description="This is the role number 2";
        role2.name = "Second Role";        
        BMP = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        baos = new ByteArrayOutputStream();
        BMP.compress(CompressFormat.PNG, 100, baos);
        role2.roleIcon = baos.toByteArray();

        Narrative narr = problem.narrative;      
        narr.agents = new ContactListAgentSource(this).getAgents();
      //      new ArrayList<Agent>();
        narr.associatedMssns = new ArrayList<Mission>();
        narr.narrator = narr.agents.get(0);
        narr.participant = narr.agents.get(1);
        narr.prolog = "HI This is the description of the narrative that tells everything that is needed to know, and sometimes things that are not needed, to make the outcome really successful and to bla bla bla";
        narr.setting = "NOTHING";
        narr.agents.get(0).addRole(role1); 
        narr.agents.get(1).addRole(role2); 
        narr.title = "the crazy narrative";

        problem.narrative=narr;
        
        
        NAApp application = (NAApp) getApplication();
        application.currentProblem=problem;
        
        application.runningProblems.add(problem);
            
        /*Generate a simple narrative with one agent,mission, and role
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
        */
        //if (!naapp.saveState(possibleNarsDir,runningProbsDir)) {
        //    Log.d("GSON", "save unsuccessful");
        //    Toast.makeText(this, "Save Failed", Toast.LENGTH_LONG);
        //}
        
        //if (! naapp.loadState(possibleNarsDir, runningProbsDir) ) {
        //    Log.d("GSON", "load unsuccessful");
        //}
        
        
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