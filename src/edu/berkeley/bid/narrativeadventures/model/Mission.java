package edu.berkeley.bid.narrativeadventures.model;
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
public class Mission {
	
	enum STATUS {
		ASSIGNED,UNASSIGNED,COMPLETE,STANDING,RECURRENT
	};
	
	int benefit;
	Time assigned;
	Time completed;
	String description;
	String[] resources; //Can have urls and plain textw
	String[] comments;
	int numAssignable;
	
	//assign
	//changeStatus
	//first resource is manager input
	
}
