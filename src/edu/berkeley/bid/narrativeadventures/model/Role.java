package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;

/**
Represents the role that an agent has
Has:
    Description
    Graphic (either icon overlay or some image which serves to reinforce the role)
    Associated account/owner

Missions

Agent
 * @author javirosa
 *
 */
public class Role {
	
	public String description;
	public ArrayList<Mission> missions;
	public ArrayList<String> resources;
	public byte[] roleIcon;
	
	public Role()
	{
	    description = "";
	    missions = new ArrayList<Mission>();
	    resources = new ArrayList<String>();
	    roleIcon = null;
	}
	//Image associated with role
	
}
