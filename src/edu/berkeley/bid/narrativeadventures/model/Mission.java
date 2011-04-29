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
	
	enum Status 
	{
		ASSIGNED,UNASSIGNED,COMPLETE,STANDING,RECURRENT
	};
	
	String id; 			//Used to uniquely identify the mission. This should never change!
	int benefit; 		//Used to quantify the importance of the mission
	GregorianCalendar assigned; 	
	GregorianCalendar completed;	
	Status status;
	String description;
	ArrayList<String> resources; //Can have urls and plain textw
	
	public boolean equals(Mission other) 
	{
		return this.id.equals(other.id);
	}
	
	public Mission()
	{
	    this(0,new GregorianCalendar(), "No Description");
	}
	
	public Mission(int benefit, GregorianCalendar gregorianCalendar,String description)
	{   
	    this.id = UUID.randomUUID().toString();
	    this.benefit = benefit;
	    this.assigned = gregorianCalendar;
	    this.description = description;
	    resources = new ArrayList<String>();
	    this.status = Status.UNASSIGNED;
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
        returnMe.description = this.description;
        returnMe.benefit = benefit;
        returnMe.resources = (ArrayList<String>) resources.clone();
        returnMe.status = Status.UNASSIGNED;        
        return returnMe;
    }
}
