package edu.berkeley.bid.narrativeadventures.model;
import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

/**
 * Represents a possible mission adaptable by an agent through a role
Has:
    Description
    Status (Assigned, Unassigned, complete, standing or recurrent)
    Benefit of completion (later on this can be more objective and possible measured by an automated agent.)
    Due date and assignment date. schedule information
    Owner object? which is useful to other agents who want to help the owner on their mission
    Backup references - [Websites, pictures, any relevant information which can guide an actor in fulfilling a mission]

	//Calendar events: http://www.developer.com/ws/article.php/3850276/Working-with-the-Android-Calendar.htm
 * @author javirosa
 *
 */
public class Mission
{
	
	enum STATUS 
	{
		ASSIGNED,UNASSIGNED,COMPLETE,STANDING,RECURRENT
	};
	
	int id; 			//Used to uniquely identify the mission. This should never change!
	int benefit; 		//Used to quantify the importance of the mission
	Time assigned; 	
	Time completed;	
	String description;
	ArrayList<String> resources; //Can have urls and plain textw
	
	@Override
	public boolean equals(Object other) 
	{
		return this.id == ((Mission)other).id;
	}
	
	public Mission()
	{
	    this(0,0,new Time(), "No Description");
	    this.assigned.setToNow();
	    
	}
	
	public Mission(int id, int benefit, Time assigned,String description)
	{   
	    this.id = id;
	    this.benefit = benefit;
	    this.assigned = assigned;
	    this.description = description;
	    resources = new ArrayList<String>();
	}
}
