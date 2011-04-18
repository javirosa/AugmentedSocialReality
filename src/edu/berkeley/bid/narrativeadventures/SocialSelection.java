package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SocialSelection extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.socisele_2);
		
		Button backToProb = (Button) findViewById(R.id.sociSeleToProb);
		backToProb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), AProblemInput.class);
				startActivityForResult(i, 0);
				finish();
			}
		});
		
		Button backToMain = (Button) findViewById(R.id.sociSeleToMain);
		backToMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), NarrativeAdventures.class);
				startActivityForResult(i, 0);
				finish();
			}
		});
		
	    // TODO Auto-generated method stub
	}

}
