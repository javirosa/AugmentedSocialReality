package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ProgresssionManagement extends Activity {
    ProgressionArrayAdapter adapter;
    private static final String[] items={"paul", "peter", "ermenegildo", "kayo", "johnathan", "ron", "javier","pablo","rick",
        "paul2", "peter2", "ermenegildo2", "kayo2", "johnathan2", "ron2", "javier2","pablo2","rick2"};
    private List<Persona> personaList = new ArrayList<Persona>();
    
    private int mRuntimeOrientation;
    private boolean mDisableScreenRotation;

    protected int getScreenOrientation() {
       Display display = getWindowManager().getDefaultDisplay();
       int orientation = display.getOrientation();

       if (orientation == Configuration.ORIENTATION_UNDEFINED) {
          orientation = getResources().getConfiguration().orientation;

          if (orientation == Configuration.ORIENTATION_UNDEFINED) {
             if (display.getWidth() == display.getHeight())
                orientation = Configuration.ORIENTATION_SQUARE;
             else if(display.getWidth() < display.getHeight())

                orientation = Configuration.ORIENTATION_PORTRAIT;
             else
                orientation = Configuration.ORIENTATION_LANDSCAPE;
             }
          }
       return orientation;
    }
    
    public void filler(){
        for (int i=0; i<items.length; i++) {
            personaList.add(new Persona());
            personaList.get(i).name=items[i];
        }       
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRuntimeOrientation = this.getScreenOrientation();
        setContentView(R.layout.progmana);
        
        filler();
        adapter = new ProgressionArrayAdapter(getApplicationContext(), R.layout.twoicononrow, personaList);       
        ListView lv = (ListView) this.findViewById(R.id.progManaPers);
        lv.setAdapter(adapter);     
        
    Button backToMain = (Button) findViewById(R.id.progManaButt);
    backToMain.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(),
                    NarrativeAdventures.class);
            startActivityForResult(i, 0);
            finish();
            }
        });
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
       if (mDisableScreenRotation) {
          super.onConfigurationChanged(newConfig);
          this.setRequestedOrientation(mRuntimeOrientation);
       } else {
         //  filler();
          mRuntimeOrientation = this.getScreenOrientation();
          super.onConfigurationChanged(newConfig);
       }
    }
}
