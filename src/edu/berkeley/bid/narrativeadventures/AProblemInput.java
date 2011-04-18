package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AProblemInput extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.probdefix2);
	
		Button backToMain = (Button) findViewById(R.id.probDefiToMain);
		backToMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		Button goToSociSele = (Button) findViewById(R.id.probDefiToSocial);
		goToSociSele.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), SocialSelection.class);
				startActivityForResult(i, 0);
				finish();
			}
		});

		
	}
	
}
