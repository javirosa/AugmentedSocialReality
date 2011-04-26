package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Contacts;

public class ContactListAgents implements AgentSource
{
    private ContentResolver cr;
    
    public ContactListAgents(Activity activity)
    {
        cr = activity.getContentResolver();
    }
    
    //getAgents from contact list
    public ArrayList<Agent> getAgents()
    {
        int PHOTO = 1;
        int NAME = 0;
        int NUMBER = 2;
        int COUNTS = 3;
        //int _ID = 4;
        ArrayList<Agent> returnMe = new ArrayList<Agent>();
        
        String[] cols = {Contacts.People.Phones.NAME,Contacts.Photos.DATA,Contacts.People.Phones.NUMBER,Contacts.People.Phones.TIMES_CONTACTED,Contacts.People._ID};
        Cursor cur = cr.query(Contacts.People.CONTENT_URI, cols, null, null, Contacts.ContactMethods.DEFAULT_SORT_ORDER);
        
        for (int i = 0; i < cur.getCount(); i++){
            //Bitmap bmp = BitmapFactory.decodeByteArray(cur.getBlob(PHOTO), 0, cur.getBlob(PHOTO).length);
            byte[] bmp = cur.getBlob(PHOTO);
            String name = cur.getString(NAME);
            String number = cur.getString(NUMBER);
            int contactCount = cur.getInt(COUNTS);
            
            Agent newAgent = new Agent();
            newAgent.name = name;
            newAgent.number = number;
            newAgent.photo = bmp;
            newAgent.responsiveness = contactCount;
            
            returnMe.add(newAgent);
        }
        
        cur.close();
           
        //Uri personURI = android.content.ContentUris.withAppendedId(Contacts.People.CONTENT_URI,cur.getLong(_ID) );
        //bmp = People.loadContactPhoto(activity.getApplicationContext(),personURI, R.drawable.icon, null);
        return returnMe;
    }    
}
