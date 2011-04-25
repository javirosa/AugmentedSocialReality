package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Contacts;
import android.provider.Contacts.People;

/** 
 * Returns a list of possible agents
 * 
 * @author javirosa
 *
 */
public interface AgentSource 
{
    public ArrayList<Agent> getAgents(Activity activity);
}

class ContactListAgents implements AgentSource
{
    //getAgents from contact list
    public ArrayList<Agent> getAgents(Activity activity)
    {
        ContentResolver cr = activity.getContentResolver();
        String[] cols = {Contacts.People.Phones.NAME,Contacts.Photos.DATA,Contacts.People.Phones.NUMBER,Contacts.People.Phones.TIMES_CONTACTED};
        Cursor cur = cr.query(Contacts.People.CONTENT_URI, cols, null, null, Contacts.ContactMethods.DEFAULT_SORT_ORDER);
        //cur.
        //People.loadContactPhoto(arg0, arg1, R.drawable.icon, null);
        //Get number
        //Get name
        //Get photo 
        // for each agent
        return null;
    }
    
}