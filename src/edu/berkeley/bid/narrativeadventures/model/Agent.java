package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

/** 
 * An agent fullfills roles and can be the source of missions, completions, and suggestions.
 * In the first iteration an agent will just be a facebook id.
 * 
 * This should extend or have a facebook GSON friendly 
 * object so that all of the facebook data is easily accessible
 * @author javirosa
 *
 */
public class Agent {
	//Assign mission
	//Mission update status {Complete,Rewarded,Updated} 
	//Get identification information
	//commentMission/Role
	//Add Mission added listener?
	
	String facebookID; //This is actually a number
	String name;
	ArrayList<String> strengths;
	ArrayList<String> comments;
	Bitmap photo;
	ArrayList<Role> roles;
	
	@Override
	public boolean equals(Object other)
	{
	    return this.facebookID.equals(((Agent)other).facebookID);
	}
	
	void addMission(Mission mission, Role role) 
	{
		//Notify agent of status update
	    
	    roles.get(roles.indexOf(role)).missions.add(mission);
		//Add mission to role
	}
	void addRole(Role role)
	{
		//Notify agent of status update
	    
		//add role to agent
	    roles.add(role);
	}
	/**
	 * Called after a mission has been updated. Comment added, etc
	 * Missions are modified directly
	 * 
	 * @param mission
	 * @param role
	 */
	void updatedMission(Mission mission, Role role)
	{
		//notify of update
	    
	}
}
