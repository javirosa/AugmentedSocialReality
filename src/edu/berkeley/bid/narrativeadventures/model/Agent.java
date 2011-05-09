package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;
/** 
 * An agent fullfills roles and can be the source of missions, completions, and suggestions.
 * In the first iteration an agent will just be a facebook id.
 * 
 * This should extend or have a facebook GSON friendly 
 * object so that all of the facebook data is easily accessible
 * @author javirosa
 *
 */
public class Agent implements Comparable<Agent> 
{
	//Assign mission
	//Mission update status {Complete,Rewarded,Updated} 
	//Get identification information
	//commentMission/Role
	
	public String number; 
	public String name;
	public ArrayList<String> strengths;
	public ArrayList<String> comments;
	public byte[] photo;
	public ArrayList<Role> roles;
	public float trust;     //From 0-1
	public float responsiveness;  //From 0-1
	
	public Agent()
	{
	    number = "";
	    name = "";
	    strengths = new ArrayList<String>();
	    comments = new ArrayList<String>();
	    photo = new byte[0];
	    roles = new ArrayList<Role>();
	    trust = 0;
	    responsiveness = 0;
	}
	/*
	@Override
	public boolean equals(Object other)
	{
	    if (other instanceof Agent && other != null) {
	        return this.number.equals(((Agent)other).number);
	    }
	    return false;
	}*/
	
	public String addMission(Mission mission, Role role) 
	{
	    roles.get(roles.indexOf(role)).missions.add(mission);
		return "New mission!\n" + Narrative.getTitle(role.description) + "\n" + Narrative.getTitle(mission.description);
	}
	
	public String addRole(Role role)
	{   
	    roles.add(role);
	    return "New role!\n" + Narrative.getTitle(role.description);
	}
	
	public String getNumber() 
	{
	    return number;
	}
	
	public int compareTo(Agent other)
	{
	    if (this.number.equals(other.number)) {
	        return 0;
	    }
	    return this.name.compareTo(other.name);
	}
	
	@Override
	public boolean equals(Object other) 
	{
	    if (other != null && other instanceof Agent)
	    {
	        return this.compareTo((Agent)other) == 0;
	    }
	    return false;
	}
}
