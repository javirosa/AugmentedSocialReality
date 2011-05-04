package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Has:
    Roles - The set of possible roles to be fullfulled in the narrative, each object is defined below
    Title - A simple statement which communicates the narrative setting and purpose

Prolog - A description which is seen after the title which serves to place the users in a setting and immerse the users.

Rules - Define the modes of interaction between users and rules to avoid causing harm. A simple generic rule might be do no harm, a more specific rule (enforceable by an automated agent) might be never mention falling to someone who is trying to overcome a fear of heights.
    Unassigned Missions - These are part of the narrative as they are yet to be transferred to someone fulfilling a role.
    Description - A highly elaborated description of the narrative which is more informative than a prolog and serves to give users a better idea as to why the narrative will be useful.
 * @author javirosa
 *
 */
public class Narrative implements Ided 
{
    
    private String id;

    public String prolog;
	public String setting;
	public ArrayList<Mission> associatedMssns;
	public ArrayList<Agent> agents;
	public ArrayList<Role> roles;
	public ArrayList<String> resources;
	public Agent narrator;
	public Agent participant;
	public String title;
	
	public Narrative() 
	{
	    id = UUID.randomUUID().toString();
	    prolog = "";
	    setting = "";
	    associatedMssns = new ArrayList<Mission>();
	    resources= new ArrayList<String>();
	    roles = new ArrayList<Role>();
	    agents = new ArrayList<Agent>();
	    title="";
	}
	
	public void assignMission(Mission mission, Agent agent, Role role) 
	{
	    //get agent
        Agent ag = agents.get(agents.indexOf(agent));
        ag.addMission(mission, role);
	    //TODO Check if already assigned
	    
	}
	
	public void addRole(Role role, Agent agent)
	{
	    //get agent
        Agent ag = agents.get(agents.indexOf(agent));
        ag.addRole(role);
        //TODO Check if already assigned
	}
	
	/**
	 * Adds comment to the first item which is not null. 
	 * This may be a narrative if all are null, 
	 * an agent if role is null, and so on. 
	 * @param agent
	 * @param role
	 * @param mission
	 */
	public void addResource(String comment, Agent agent, Role role, Mission mission)
	{
		if (agent == null){
		    this.resources.add(comment);
		    return;
		} 
	    //get agent
        Agent ag = agents.get(agents.indexOf(agent));
        
		if (role == null) {
		    ag.comments.add(comment);
		    return;
		} 
		//get role
        Role ro = ag.roles.get(ag.roles.indexOf(role));
        
		if (mission == null) {
            ro.resources.add(comment);
            return;
		}
		
		//add comment to resources
		ro.missions.get(ro.missions.indexOf(mission)).resources.add(comment);
		
	}
	
	public void addMission(Mission mission)
	{
	    associatedMssns.add(mission);
	}
	
	public static String getTitle(String description) 
    {
        int end =description.indexOf(":"); 
        if ( end == -1 || end > 22) {
            end = 22; //22 22 22 characters for SMS message
        } 
        if (end > description.length()) {
            end = description.length();
        }
        return description.substring(0,end);
    }
	
	public boolean equals(Narrative other)
    {
	    return this.id.equals(other.id);
    }
	
	/**
	 * Used to create a narrative based off of this one with the same 
	 * prolog, role set, and resource set, but no agents.
	 * @param other
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public Narrative cloneNarrative()
	{
	    Narrative returnMe = new Narrative();
	    returnMe.id = UUID.randomUUID().toString();
	    returnMe.prolog = this.prolog;
	    returnMe.title = this.title;
	    for (Role r : this.roles) {
	        returnMe.roles.add(r.cloneRole());
	    }
	    for (Mission m: this.associatedMssns) {
	        returnMe.associatedMssns.add(m.cloneMission());
	    }
	    returnMe.resources = (ArrayList<String>) resources.clone();
	    return returnMe;
	}
	
	public String getId() {
        return id;
    }
}
