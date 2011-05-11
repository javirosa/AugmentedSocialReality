package edu.berkeley.bid.narrativeadventures.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.berkeley.bid.narrativeadventures.R;
import edu.berkeley.bid.narrativeadventures.R.drawable;
import edu.berkeley.bid.narrativeadventures.R.id;
import edu.berkeley.bid.narrativeadventures.R.layout;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Role;

public class RoleArrayAdapter extends ArrayAdapter<Agent>{
    private static final String tag = "ProgessionArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon;
    private TextView label;
    private List<Agent> agents = new ArrayList<Agent>();
    
    public RoleArrayAdapter(Context context, int textViewResourceId, List<Agent> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.agents = objects;
    }
    
    public int getCount(){
        return this.agents.size();
    }
    
    public Agent getItem(int index) {
        return this.agents.get(index);
    }
    
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.oneiconrow, parent, false);
        
        label = (TextView) row.findViewById(R.id.label);
        icon = (ImageView) row.findViewById(R.id.icon);
        
        //Get data from Agent
        Agent persona = getItem(position);
        String name = persona.name;
        byte[] agentPhoto = persona.photo;
        Bitmap agentBmp = android.graphics.BitmapFactory.decodeByteArray(agentPhoto, 0, agentPhoto.length);
        
        //get data from Role
        byte[] roleIcon = new byte[] {};
        if (persona.roles.size() > 0) {
            roleIcon = persona.roles.get(0).roleIcon;
        }
        Bitmap roleBmp = android.graphics.BitmapFactory.decodeByteArray(roleIcon, 0, roleIcon.length);
        
        //Set elements in item view
        label.setText(name);
        if (agentBmp != null) {
            icon.setImageBitmap(agentBmp);
        } else {
            icon.setImageResource(R.drawable.icon);
        }
        return row;
    }
}
