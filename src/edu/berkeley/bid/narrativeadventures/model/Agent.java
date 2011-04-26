package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
/** 
 * An agent fullfills roles and can be the source of missions, completions, and suggestions.
 * In the first iteration an agent will just be a facebook id.
 * 
 * This should extend or have a facebook GSON friendly 
 * object so that all of the facebook data is easily accessible
 * @author javirosa
 *
 */
public class Agent {
	//Assign mission
	//Mission update status {Complete,Rewarded,Updated} 
	//Get identification information
	//commentMission/Role
	//Add Mission added listener?
	
	public String number; 
	public String name;
	public ArrayList<String> strengths;
	public ArrayList<String> comments;
	public byte[] photo;
	public ArrayList<Role> roles;
	public float trust;     //From 0-1
	public float responsiveness;  //From 0-1
	
	@Override
	public boolean equals(Object other)
	{
	    return this.number.equals(((Agent)other).number);
	}
	
	String addMission(Mission mission, Role role) 
	{
	    roles.get(roles.indexOf(role)).missions.add(mission);
		return "New mission!\n" + Narrative.getTitle(role.description) + "\n" + Narrative.getTitle(mission.description);
	}
	
	String addRole(Role role)
	{   
	    roles.add(role);
	    return "New role!\n" + Narrative.getTitle(role.description);
	}
	
	/**
	 * Limit message to SMS sizes for best results.
	 * @param message 
	 * @param truncate if true then only the first message in what would be a multi-part message is sent
	 */
	void sendMessage(String message, boolean truncate)
	{
	    if (message == null) 
	    {
	        message = "null";
	    }
	    
	    SmsManager sms = SmsManager.getDefault();
	    ArrayList<String> msgs = sms.divideMessage(message);
	    if (truncate) {
	        sms.sendTextMessage(number, null, msgs.get(0), null, null);
	    } else {
	        sms.sendMultipartTextMessage(number, null, msgs, null, null);
	    }
	}
}
