package app.xzone.storyline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import app.xzone.storyline.component.Sliding;

public class DetailActivity extends Activity {

	private Intent intent;
	private ImageButton handle;
	private Builder builder;
	private ImageButton trigger;
	
	CheckBox c1,c2,c3;
    int key=0;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_layout);
		
		final Sliding popup = (Sliding) findViewById(R.id.sliding1);
		popup.setVisibility(View.GONE);
		
//		final Button btn = (Button) findViewById(R.id.show1);
//		
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(key==0){
//                    key=1;
//                    popup.setVisibility(View.VISIBLE);
////                    btn.setBackgroundResource(R.drawable.ic_launcher);
//	            }
//	            else if(key==1){
//	                    key=0;
//	                    popup.setVisibility(View.GONE);
////	                    btn.setBackgroundResource(R.drawable.ic_launcher);
//	            }
//			}
//		});
		

//		builder = new AlertDialog.Builder(this);
//
//		trigger = (ImageButton) findViewById(R.id.triggerButton);

		// trigger.setOnClickListener(this);
	}

}
