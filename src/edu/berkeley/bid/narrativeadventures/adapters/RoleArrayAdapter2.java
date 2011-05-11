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

public class RoleArrayAdapter2 extends ArrayAdapter<Role>{
    private static final String tag = "RolwArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon;
    private TextView label;
    private List<Role> roles = new ArrayList<Role>();
    
    public RoleArrayAdapter2(Context context, int textViewResourceId, List<Role> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.roles = objects;
    }
    
    public int getCount(){
        return this.roles.size();
    }
    
    public Role getItem(int index) {
        return this.roles.get(index);
    }
    
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.oneiconrow, parent, false);
        
        label = (TextView) row.findViewById(R.id.label);
        icon = (ImageView) row.findViewById(R.id.icon);
        
        //Get data from Agent
        Role role = getItem(position);
        String name = role.description;
       byte[] agentPhoto = role.roleIcon;
        Bitmap agentBmp = android.graphics.BitmapFactory.decodeByteArray(agentPhoto, 0, agentPhoto.length);
        
        //get data from Role
        byte[] roleIcon = new byte[] {};
        if (role.missions.size() > 0) {
            roleIcon = role.missions.get(0).icon;
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
