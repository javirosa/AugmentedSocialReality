package edu.berkeley.bid.narrativeadventures.model;

import java.util.List;

import android.graphics.Bitmap;

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
	
	String description;
	List<Mission> missions;
	List<String> resources;
	Bitmap roleIcon;
	//Image associated with role
	
}
