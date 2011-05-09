package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import edu.berkeley.bid.narrativeadventures.model.Agent;
import edu.berkeley.bid.narrativeadventures.model.Mission;

public class MissionArrayAdapter extends ArrayAdapter<Mission>{
    private static final String tag = "MissionArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon;
    private TextView label;
    private List<Mission> missions = new ArrayList<Mission>();
    
    public MissionArrayAdapter(Context context, int textViewResourceId, List<Mission> missions) 
    {
        super(context, textViewResourceId, missions);
        this.context = context;
        this.missions = missions;
    }
    
    @Override
    public int getCount()
    {
        return this.missions.size();
    }
    
    @Override
    public Mission getItem(int index) 
    {
        return this.missions.get(index);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        //Create item view from scratch
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.oneiconrow, parent, false);
         label = (TextView) row.findViewById(R.id.label);
         icon = (ImageView) row.findViewById(R.id.icon);
        
        //Get data from Agent
        Mission mission = getItem(position);
        String description = mission.description;
        byte[] photo = mission.icon;
        Bitmap bmp = android.graphics.BitmapFactory.decodeByteArray(photo, 0, photo.length);
        //Set elements in item view
        label.setText(description);
   //     label2.setText("Select Icon: ");
//        edit.setText(name);
        
        if (bmp != null) {
            icon.setImageBitmap(bmp);
        } else {
            icon.setImageResource(R.drawable.back_arrow);
        }
        return row;
    }
}
