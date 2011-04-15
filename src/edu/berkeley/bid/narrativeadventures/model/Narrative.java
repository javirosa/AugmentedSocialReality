package edu.berkeley.bid.narrativeadventures.model;

import java.util.List;


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
	List<Mission> msns;
	List<Agent> agts;
	List<Role> rls;
	String[] comments;
	//getMissions
	//getRoles
	//getAgents
}
