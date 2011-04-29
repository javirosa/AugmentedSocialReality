package edu.berkeley.bid.narrativeadventures.io;

import java.util.ArrayList;

import android.telephony.SmsManager;
import edu.berkeley.bid.narrativeadventures.model.Agent;

public class MessageManager {

	/**
	 * Limit message to SMS sizes for best results.
	 * @param message 
	 * @param truncate if true then only the first message in what would be a multi-part message is sent
	 */
	void sendMessage(Agent agent, String message, boolean truncate)
	{
	    if (message == null) 
	    {
	        message = "null";
	    }
	    
	    SmsManager sms = SmsManager.getDefault();
	    ArrayList<String> msgs = sms.divideMessage(message);
	    if (truncate) {
	        sms.sendTextMessage(agent.getNumber(), null, msgs.get(0), null, null);
	    } else {
	        sms.sendMultipartTextMessage(agent.getNumber(), null, msgs, null, null);
	    }
	}
}
