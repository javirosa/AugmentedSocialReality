package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class NarrativeSelection extends TabActivity {
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrsele);
    TabHost host = getTabHost();
    Log.d("Narrative Selection Screen, ", Boolean.toString(host == null));
    host.setup();
   
    TabHost.TabSpec humor = host.newTabSpec("humor");
    humor.setIndicator("HUMOR");
            //getResources().getString(R.id.narrSeleTab1Titl));
    humor.setContent(R.id.narrSeleTab1Cont);
    host.addTab(humor);
        
    TabHost.TabSpec epic = host.newTabSpec("epic");
    epic.setIndicator("EPIC");
            //getResources().getString(R.id.narrSeleTab2Titl));
    epic.setContent(R.id.narrSeleTab2Cont);
    host.addTab(epic);
    
    Button ToRole = (Button) findViewById(R.id.narrSeleToRole);
    ToRole.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
          //  Intent i = new Intent(view.getContext(), NarrativeSelection.class);
            //startActivityForResult(i, 0);
//          Intent intent = new Intent();
//              setResult(RESULT_OK, intent);
        //    finish();
        }
    });
    
    Button backToMain = (Button) findViewById(R.id.narrSeleToMain);
    backToMain.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(),
                    NarrativeAdventures.class);
            startActivityForResult(i, 0);
            finish();
        }
    });
    }
}
