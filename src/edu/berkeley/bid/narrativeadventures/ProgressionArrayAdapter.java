package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgressionArrayAdapter extends ArrayAdapter<Persona>{
    private static final String tag = "ProgessionArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon1;
    private ImageView icon2;
    private TextView label;
    private List<Persona> personas = new ArrayList<Persona>();
    
    public ProgressionArrayAdapter(Context context, int textViewResourceId, List<Persona> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.personas = objects;
    }
    
    public int getCount(){
        return this.personas.size();
    }
    
    public Persona getItem(int index) {
        return this.personas.get(index);
    }
    
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.twoicononrow, parent, false);
        
        Persona persona = getItem(position);
        label = (TextView) row.findViewById(R.id.label);
        icon1 = (ImageView) row.findViewById(R.id.icon1);
        icon2 = (ImageView) row.findViewById(R.id.icon2);
        label.setText(persona.name);

        if (persona.name.length() > 4) {
            icon1.setImageResource(R.drawable.man);
            icon2.setImageResource(R.drawable.icon); 
        }
        else
            icon1.setImageResource(R.drawable.man2);       
            icon2.setImageResource(R.drawable.icon); 
        return row;
    }
}
