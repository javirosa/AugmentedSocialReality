package edu.berkeley.bid.narrativeadventures.io;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
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

    private static String hasNumberSelector = Contacts.Phones.PERSON_ID + "=?" 
    + " AND "+ Contacts.Phones.TYPE+"="+Contacts.Phones.TYPE_MOBILE;
    private static String hasPhotoSelector = Contacts.Photos.PERSON_ID + "=?";
    private static byte[] empty = {};
    private static String[] cols = {
        Contacts.People.DISPLAY_NAME,
        Contacts.People.NUMBER,
        Contacts.People.TIMES_CONTACTED,
        Contacts.People._ID};
    private static String[] colsPhone = {Contacts.Phones.PERSON_ID,Contacts.Phones.NUMBER_KEY,Contacts.Phones.TYPE};
    private static String[] colsPhoto = {Contacts.Photos.PERSON_ID,Contacts.Photos.DATA};
    private static String hasNameSelector = Contacts.People.NAME + "<>\'\'";
    private static int NAME = 0;
    private static int NUMBER = 1;
    private static int COUNTS = 2;
    private static int ID = 3;
        
    
    public ContactListAgentSource(Activity activity)
    {
        cr = activity.getContentResolver();
        this.activity = activity;
    }
    
    public Agent getAgent(Long _ID)
    {
        Uri uri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, _ID);
        Cursor cur = cr.query(uri, cols, hasNameSelector, null, Contacts.People.DEFAULT_SORT_ORDER);
        Agent returnMe = new Agent();
        if (! cur.moveToFirst()) return null;
        
        //Bitmap bmp = BitmapFactory.decodeByteArray(cur.getBlob(PHOTO), 0, cur.getBlob(PHOTO).length);
        String name = cur.getString(NAME);
        //This is returning null for some reason
        String number = null;//cur.getString(NUMBER);
        int contactCount = cur.getInt(COUNTS);
        String[] str_ID = new String[] {Long.toString(ID)};
        
        //Get phone number from another database
        Cursor curP = cr.query(Contacts.Phones.CONTENT_URI, colsPhone, hasNumberSelector, str_ID, null);
        if (curP.getCount() > 0) {                      //If user has a number
            curP.moveToFirst();
            number = curP.getString(1);
            char[] out = number.toCharArray();
            for (int j = 0; j < out.length/2; j++) {    //Reverse 
                char tmp = out[j];
                out[j] = out[out.length-j-1];
                out[out.length-j-1] = tmp;
            }
            number = new String(out);
        
            
            //Get raw image
            Cursor curPhoto = cr.query(Contacts.Photos.CONTENT_URI, colsPhoto, hasPhotoSelector, str_ID, null);
            curPhoto.moveToFirst();
            byte[] bmp = curPhoto.getBlob(1);
            if (bmp == null) {
                bmp = empty;
            }
            
            returnMe.name = name;
            returnMe.number = number;
            Log.d("ContactListAgentSource","phone number:"+number);
            returnMe.photo = bmp;
            returnMe.responsiveness = contactCount; 
            curP.close();
            return returnMe;
        }
        
        curP.close();
        return null;
    }
    
    //getAgents from contact list
    public ArrayList<Agent> getAgents()
    {
        ArrayList<Agent> returnMe = new ArrayList<Agent>();
        
        Cursor cur = cr.query(Contacts.People.CONTENT_URI, cols, hasNameSelector, null, Contacts.People.DEFAULT_SORT_ORDER);        
        for (int i = 0; i < cur.getCount(); i++){
            //Pull data from the next contact
            cur.moveToNext();
            //Bitmap bmp = BitmapFactory.decodeByteArray(cur.getBlob(PHOTO), 0, cur.getBlob(PHOTO).length);
            String name = cur.getString(NAME);
            String number = null;//cur.getString(NUMBER) seems to return null
            int contactCount = cur.getInt(COUNTS);
            long _ID = cur.getLong(ID);
            
            //Get phone number from another database
            
            //Uri getPhoneURI =  android.content.ContentUris.withAppendedId(Contacts.Phones.CONTENT_URI,_ID);
           
            Cursor curP = cr.query(
                    Contacts.Phones.CONTENT_URI,
                    colsPhone,
                    hasNumberSelector,
                    new String[] {Long.toString(_ID)},
                    null);
            if (curP.getCount() > 0) {                      //If user has a number
                curP.moveToFirst();
                number = curP.getString(1);
                char[] out = number.toCharArray();
                for (int j = 0; j < out.length/2; j++) {    //Reverse 
                    char tmp = out[j];
                    out[j] = out[out.length-j-1];
                    out[out.length-j-1] = tmp;
                }
                number = new String(out);
            
                
                //Get raw image
                Cursor curPhoto = cr.query(Contacts.Photos.CONTENT_URI, colsPhoto, hasPhotoSelector, new String[] {Long.toString(_ID)}, null);
                byte[] bmp = null;
                if(curPhoto.moveToFirst()) {
                    bmp = curPhoto.getBlob(1);
                }
                if (bmp == null) {
                    bmp = empty;
                }
                
                /*/Load image in Bitmap format and convert to byte[]
                /ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bmp; 
                Uri personURI = android.content.ContentUris.withAppendedId(Contacts.People.CONTENT_URI,cur.getLong(ID));
                Bitmap btmap = Contacts.People.loadContactPhoto(activity.getApplicationContext(),personURI, R.drawable.icon, null);
                btmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                bmp = baos.toByteArray();*/
                
                Agent newAgent = new Agent();
                newAgent.name = name;
                newAgent.number = number;
                Log.d("ContactListAgentSource","phone number:"+number);
                newAgent.photo = bmp;
                newAgent.responsiveness = contactCount; 
            
                returnMe.add(newAgent);
            }
            curP.close();
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
