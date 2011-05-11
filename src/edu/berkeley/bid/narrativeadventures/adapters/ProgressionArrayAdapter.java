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

public class ProgressionArrayAdapter extends ArrayAdapter<Agent>{
    private static final String tag = "ProgessionArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon1;
    private ImageView icon2;
    private TextView label;
    private TextView label2;
    private List<Agent> agents = new ArrayList<Agent>();
    
    public ProgressionArrayAdapter(Context context, int textViewResourceId, List<Agent> objects) {
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
        row = inflater.inflate(R.layout.twoicononrow, parent, false);
        
        label = (TextView) row.findViewById(R.id.label);
        label2 = (TextView) row.findViewById(R.id.label2);
        icon1 = (ImageView) row.findViewById(R.id.icon1);
        icon2 = (ImageView) row.findViewById(R.id.icon2);
        
        //Get data from Agent
        Agent persona = getItem(position);
        String nameLabel = persona.name;
        String roleLabel;
        if (persona.roles.get(0) == null) {
            roleLabel = "NOT ASSIGNED";
        } else {
            roleLabel = persona.roles.get(0).description;
        }
        byte[] agentPhoto = persona.photo;
        Bitmap agentBmp = android.graphics.BitmapFactory.decodeByteArray(agentPhoto, 0, agentPhoto.length);
        
        //get data from Role
        byte[] roleIcon = new byte[] {};
        if (persona.roles.size() > 0) {
            roleIcon = persona.roles.get(0).roleIcon;
        }
        Bitmap roleBmp = android.graphics.BitmapFactory.decodeByteArray(roleIcon, 0, roleIcon.length);
        
        //Set elements in item view
        label.setText(nameLabel);
        label2.setText(roleLabel);
        if (agentBmp != null) {
            icon1.setImageBitmap(agentBmp);
        } else {
            icon1.setImageResource(R.drawable.icon);
        }
        if (roleBmp != null) {
            icon2.setImageBitmap(roleBmp);
        } else {
            icon2.setImageResource(R.drawable.icon);
        }
        return row;
    }
}
