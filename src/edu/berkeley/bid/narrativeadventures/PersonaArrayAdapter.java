package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonaArrayAdapter extends ArrayAdapter<Persona>{
    private static final String tag = "PersonaArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;
    private ImageView icon;
    private TextView label;
    private List<Persona> personas = new ArrayList<Persona>();
    
    public PersonaArrayAdapter(Context context, int textViewResourceId, List<Persona> objects) {
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
        row = inflater.inflate(R.layout.simplerow, parent, false);
        
        Persona persona = getItem(position);
        label = (TextView) row.findViewById(R.id.label);
        icon = (ImageView) row.findViewById(R.id.icon);
        label.setText(persona.name);

        if (persona.name.length() > 4) 
            icon.setImageResource(R.drawable.man);
        else
            icon.setImageResource(R.drawable.man2);            
        return row;
    }
    
}
