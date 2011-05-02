package edu.berkeley.bid.narrativeadventures.io;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts;
import android.util.Log;
import edu.berkeley.bid.narrativeadventures.R;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.AgentSourceI;

public class ContactListAgentSource implements AgentSourceI
{
    private ContentResolver cr;
    private Activity activity;
    
    public ContactListAgentSource(Activity activity)
    {
        cr = activity.getContentResolver();
        this.activity = activity;
    }
    
    //getAgents from contact list
    public ArrayList<Agent> getAgents()
    {
        int NAME = 0;
        int NUMBER = 1;
        int COUNTS = 2;
        int ID = 3;
        ArrayList<Agent> returnMe = new ArrayList<Agent>();
        
        String[] cols = {
                Contacts.People.DISPLAY_NAME,
                Contacts.People.NUMBER,
                Contacts.People.TIMES_CONTACTED,
                Contacts.People._ID};
        Cursor cur = cr.query(Contacts.People.CONTENT_URI, cols, null, null, Contacts.People.DEFAULT_SORT_ORDER);
        
        for (int i = 0; i < cur.getCount(); i++){
            //Pull data from the next contact
            cur.moveToNext();
            //Bitmap bmp = BitmapFactory.decodeByteArray(cur.getBlob(PHOTO), 0, cur.getBlob(PHOTO).length);
            String name = cur.getString(NAME);
            //This is returning null for some reason
            String number = null;//cur.getString(NUMBER);
            int contactCount = cur.getInt(COUNTS);
            long _ID = cur.getLong(ID);
            
            //Get phone number from another database
            String[] colsP = {Contacts.Phones.PERSON_ID,Contacts.Phones.NUMBER_KEY,Contacts.Phones.TYPE};
            //Uri getPhoneURI =  android.content.ContentUris.withAppendedId(Contacts.Phones.CONTENT_URI,_ID);
            String selector = Contacts.Phones.PERSON_ID + "=" + Long.toString(_ID) 
                + " AND "+ Contacts.Phones.TYPE+"="+Contacts.Phones.TYPE_MOBILE; 
            Cursor curP = cr.query(
                    Contacts.Phones.CONTENT_URI,
                    colsP,
                    selector,
                    null,
                    null);
            if (curP.getCount() > 0) {
                curP.moveToFirst();
                number = curP.getString(1);
                char[] out = number.toCharArray();
                for (int j = 0; j < out.length/2; j++) {  //Reverse 
                    char tmp = out[j];
                    out[j] = out[out.length-j-1];
                    out[out.length-j-1] = tmp;
                }
                number = new String(out);
            } else {
               number = "";
            }
            curP.close();
            
            //Load image in Bitmap format and convert to byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bmp; 
            Uri personURI = android.content.ContentUris.withAppendedId(Contacts.People.CONTENT_URI,cur.getLong(ID));
            Bitmap btmap = Contacts.People.loadContactPhoto(activity.getApplicationContext(),personURI, R.drawable.icon, null);
            btmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            bmp = baos.toByteArray();
            
            Agent newAgent = new Agent();
            newAgent.name = name;
            newAgent.number = number;
            Log.d("ContactListAgentSource","phone number:"+number);
            newAgent.photo = bmp;
            newAgent.responsiveness = contactCount; 
            
            returnMe.add(newAgent);
        }
        
        cur.close();
           
        return returnMe;
    }
    
    /**
     * Not implemented
     */
    public ArrayList<Agent> getAgents(AgentSourceFilterI asf) {
        throw new UnsupportedOperationException();
        //return null;
    }
}
