package edu.berkeley.bid.narrativeadventures.model;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;


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
	
	public enum Status 
	{
		ASSIGNED,UNASSIGNED,COMPLETE,STANDING,RECURRENT
	};
	
	//ADDED PUBLIC HERE>>> NEED TO DECIDE IF GETTER/PUTTER WILL BE USED
	public String id; 			//Used to uniquely identify the mission. This should never change!
	public int benefit; 		//Used to quantify the importance of the mission
	public GregorianCalendar assigned; 	
	public GregorianCalendar completed;	
	public Status status;
	public String description;
	public ArrayList<String> resources; //Can have urls and plain textw
	public byte[] icon;
	
	public boolean equals(Mission other) 
	{
		return this.id.equals(other.id);
	}
	
	public Mission()
	{
	    this(0,new GregorianCalendar(), "No Title","No Description");
	}
	
	public Mission(int benefit, GregorianCalendar gregorianCalendar,String title, String description)
	{   
	    this.id = UUID.randomUUID().toString();
	    this.benefit = benefit;
	    this.assigned = gregorianCalendar;
	    //this.title = title;
	    this.description = description;
	    resources = new ArrayList<String>();
	    this.status = Status.UNASSIGNED;
	    this.icon =  new byte[0];
	}
	
	/**
     * Used to create a Mission based off of another with the same 
     * description information.
     * @param other
     * @return
     */
    @SuppressWarnings("unchecked")
    public Mission cloneMission()
    {
        Mission returnMe = new Mission();
        returnMe.id = UUID.randomUUID().toString();
        returnMe.benefit = benefit;
        //returnMe.title = this.title;
        returnMe.description = this.description;
        returnMe.resources = (ArrayList<String>) resources.clone();
        returnMe.status = Status.UNASSIGNED;    
        returnMe.icon = this.icon;
        return returnMe;
    }
    
    public void finishMission() 
    {
        this.completed = new GregorianCalendar();
        this.status = Status.COMPLETE;
    }
}
