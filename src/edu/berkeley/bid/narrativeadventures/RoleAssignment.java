package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleAssignment extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roleassign);
        
        
        
    
    Button backToMain = (Button) findViewById(R.id.roleAssiToMain);
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
