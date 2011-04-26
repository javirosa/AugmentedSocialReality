package edu.berkeley.bid.narrativeadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SocialSelection extends Activity {
	int posi;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.socisele_2);

		Button ToNarr = (Button) findViewById(R.id.sociSeleToNarr);
		ToNarr.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), NarrativeSelection.class);
				startActivityForResult(i, 0);
//		        Intent intent = new Intent();
  //              setResult(RESULT_OK, intent);
			//    finish();
			}
		});

		Button backToMain = (Button) findViewById(R.id.sociSeleToMain);
		backToMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(),
						NarrativeAdventures.class);
				startActivityForResult(i, 0);
				finish();
			}
		});
		
		ListView listin = (ListView) findViewById(R.id.sociSeleIn);
		listin.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                    long id) {
                // TODO Auto-generated method stub
  //              TextView comment = (TextView) findViewById(R.id.sociSeleNarrTitl);
    //            comment.setText("COMENTARIO DEL ITEM: " + position);
      //          posi=position;
                view.setBackgroundColor(0xfff00000); 
            //  view.performLongClick();
                return false;
            }
		});
	
/* THIS IS THE ONCLICK		
		listin.setOnItemClickListener(new OnItemClickListener() {
		   int oldPosi;
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
			    TextView comment = (TextView) findViewById(R.id.sociSeleNarrTitl);
				comment.setText("COMENTARIO DEL ITEM: " + position);
				posi=position;
				view.setBackgroundColor(0xfff00000); 
				oldPosi=position;
			//	view.performLongClick();
			}
		});
		
*/
		
/*THIS IS THE LISTVIEW DRAG DROP		
		listin.setOnTouchListener(new View.OnTouchListener() {
			private final static int START_DRAGGING = 0;
			private final static int STOP_DRAGGING = 1;
			private int status;
			@Override
			public boolean onTouch(View view, MotionEvent me) {	
				TextView comment = (TextView) findViewById(R.id.sociSeleNarrTitl);
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					status = START_DRAGGING;
//					view.setClickable(true);
//					view.setPressed(true);
//					boolean clicking = view.isClickable();
					//view.setSelected(true);
					//final ListView papa2 = (ListView) view.getParent();
	//				AdapterView granpa = (AdapterView) papa2.getParent();
		//			granpa.setOnItemClickListener(listener);
					
//					listin.setOnItemSelectedListener(listener);
					//papa2.getOnItemClickListener();
					
	//				Long itemId =  papa2.getSelectedItemId();
//					comment.setText("START DRAGGING: " + " " + clicking);
					//view.getTag();
					
					
				}
				if (me.getAction() == MotionEvent.ACTION_UP) {
					status = STOP_DRAGGING;
	//				comment.setText("STOP DRAGGING");
//					comment.setText("STOP DRAGGING: " + " " + ((ListView) view).getSelectedItemPosition());

					comment.setText("STOP DRAGGING: " + " " + posi);

				} else if (me.getAction() == MotionEvent.ACTION_MOVE) {
					if (status == START_DRAGGING) {
						AbsoluteLayout papa = (AbsoluteLayout) findViewById(R.id.sociSeleToucPad);
						papa.invalidate();
						int testx = view.getLeft();
						float posx=me.getX();
						comment.setText("DRAGGING: "+ (int) posx + " " + testx);
						if (posx+testx <= 0){
							view.offsetLeftAndRight(-testx);
						}else if (posx+testx > papa.getWidth()-view.getWidth()) {
							view.offsetLeftAndRight(papa.getWidth()-view.getWidth()-testx);
						}else {
							view.offsetLeftAndRight((int) posx);
						}
					}
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
*/
		ImageButton dot = (ImageButton) findViewById(R.id.sociSeleDot);
		dot.setOnTouchListener(new View.OnTouchListener() {
			private final static int START_DRAGGING = 0;
			private final static int STOP_DRAGGING = 1;
			private int status;
//			TextView comment = (TextView) findViewById(R.id.sociSeleNarrTitl);
			@Override
			public boolean onTouch(View view, MotionEvent me) {
	//			comment.setText("hope never ends");
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					status = START_DRAGGING;
	//				comment.setText("START DRAGGING");
				}
				if (me.getAction() == MotionEvent.ACTION_UP) {
					status = STOP_DRAGGING;
		//				comment.setText("STOP DRAGGING");
				} else if (me.getAction() == MotionEvent.ACTION_MOVE) {
					if (status == START_DRAGGING) {
						AbsoluteLayout papa = (AbsoluteLayout) findViewById(R.id.sociSeleToucPad);
						papa.invalidate();
						int testx = view.getLeft();
						int testy = view.getTop();
						float posx=me.getX()-view.getWidth()/2;
						float posy=me.getY()-view.getWidth()/2;
	//					comment.setText("DRAGGING: "+ (int) posx + " " + (int) posy + "test: " + testx + " " + testy);
						if (posy+testy <= 0){
							view.offsetTopAndBottom(-testy);
						}else if (posy+testy > papa.getHeight()-view.getHeight()) {
							view.offsetTopAndBottom(papa.getHeight()-view.getHeight()-testy);
						}else {
							view.offsetTopAndBottom((int) posy);
						}
						if (posx+testx <= 0){
							view.offsetLeftAndRight(-testx);
						}else if (posx+testx > papa.getWidth()-view.getWidth()) {
							view.offsetLeftAndRight(papa.getWidth()-view.getWidth()-testx);
						}else {
							view.offsetLeftAndRight((int) posx);
						}
					}
				}
				return false;
			}
		});
	}
}
