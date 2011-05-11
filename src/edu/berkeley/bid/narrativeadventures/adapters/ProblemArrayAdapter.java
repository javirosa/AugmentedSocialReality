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
import edu.berkeley.bid.narrativeadventures.model.Problem;

/**
 * Assumes the problem already has a narrative and a narrator
 * @author javirosa
 *
 */
public class ProblemArrayAdapter extends ArrayAdapter<Problem>{
    private Context context;
    private List<Problem> problems = new ArrayList<Problem>();
    
    public ProblemArrayAdapter(Context context, int textViewResourceId, List<Problem> missions) 
    {
        super(context, textViewResourceId, missions);
        this.context = context;
        this.problems = missions;
    }
    
    @Override
    public int getCount()
    {
        return this.problems.size();
    }
    
    @Override
    public Problem getItem(int index) 
    {
        return this.problems.get(index);
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView icon;
        TextView label;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Create item view from scratch
        View row = inflater.inflate(R.layout.oneiconrow, parent, false);
        label = (TextView) row.findViewById(R.id.label);
        icon = (ImageView) row.findViewById(R.id.icon);
        
        //Get data from problem 
        Problem problem = getItem(position);
        String description = "No Narrative";
        byte[] photo = new byte[]{};
        
        //A problem can start off without a narrative
        if (problem.narrative != null){
            description = problem.narrative.title;
            if ( problem.narrative.narrator != null) {
                photo = problem.narrative.narrator.photo;
            }
        }
        Bitmap bmp = android.graphics.BitmapFactory.decodeByteArray(photo, 0, photo.length);
        
        //Set elements in item view
        label.setText(description);
        
        //If there isn't a decoding problem set the bitmap to bmp
        if (bmp != null) {
            icon.setImageBitmap(bmp);
        } else {
            icon.setImageResource(R.drawable.icon);
        }
        return row;
    }
}
