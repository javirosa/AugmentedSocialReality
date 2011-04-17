package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;


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
public class Narrative {
	String prolog;
	String setting;
	ArrayList<Mission> associatedMssns;
	ArrayList<Agent> agents;
	ArrayList<Role> roles;
	ArrayList<String> resources;
	
	void assignMission(Mission mission, Agent agent, Role role) 
	{
	    //get agent
        Agent ag = agents.get(agents.indexOf(agent));
        ag.addMission(mission, role);
	    //TODO Check if already assigned
	    
	}
	void addRole(Role role, Agent agent)
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
	void addResource(String comment, Agent agent, Role role, Mission mission)
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
	void addMission(Mission mission)
	{
	    associatedMssns.add(mission);
	}
}
