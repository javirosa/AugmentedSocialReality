package edu.berkeley.bid.narrativeadventures;

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
import edu.berkeley.bid.narrativeadventures.model.Agent;

public class IconArrayAdapter extends ArrayAdapter<Agent>{
    private List<Agent> agents = new ArrayList<Agent>();
    
    public IconArrayAdapter(Context context, int textViewResourceId, List<Agent> agents) 
    {
        super(context, textViewResourceId, agents);
        this.agents = agents;
    }
    
    @Override
    public int getCount()
    {
        return this.agents.size();
    }
    
    @Override
    public Agent getItem(int index) 
    {
        return this.agents.get(index);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Create item view from scratch
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate( R.layout.onlyiconrow, parent, false);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        
        //Get data from Agent
        Agent persona = getItem(position);
        byte[] photo = persona.photo;
        Bitmap bmp = android.graphics.BitmapFactory.decodeByteArray(photo, 0, photo.length);
        
        //Set elements in item view
        if (bmp != null) {
            icon.setImageBitmap(bmp);
        } else {
            icon.setImageResource(R.drawable.icon);
        }
        return row;
    }
}

