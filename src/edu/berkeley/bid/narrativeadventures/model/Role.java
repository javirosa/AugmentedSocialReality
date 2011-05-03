package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;
import java.util.UUID;

/**
Represents the role that an agent has
Has:
    Description
    Graphic (either icon overlay or some image which serves to reinforce the role)
    Associated account/owner

 * @author javirosa
 */
public class Role {
	
    private String id;
    public String name;
	public String description;
	public ArrayList<Mission> missions;
	public ArrayList<String> resources;
	public byte[] roleIcon;
	
	public Role()
	{
	    description = "";
	    name = "";
	    missions = new ArrayList<Mission>();
	    resources = new ArrayList<String>();
	    roleIcon = new byte[]{};
	}
	
	/**
     * Used to create a role based off of another with the same 
     * description information and mission set.
     * @param other
     * @return
     */
    @SuppressWarnings("unchecked")
    public Role cloneRole()
    {
        Role returnMe = new Role();
        returnMe.id = UUID.randomUUID().toString();
        returnMe.description = this.description;
        for (Mission m: missions) {
            returnMe.missions.add(m.cloneMission());
        }
        returnMe.resources = (ArrayList<String>) resources.clone();
        returnMe.roleIcon = roleIcon;
        returnMe.name = name;
        
        return returnMe;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
