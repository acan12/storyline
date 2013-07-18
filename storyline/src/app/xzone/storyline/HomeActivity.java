package app.xzone.storyline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import app.xzone.storyline.component.Sliding;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class HomeActivity extends SlidingActivity implements OnClickListener {

	private ImageButton _addButton;
	private Intent intent;

	private View menu;
	private View app;
	private ImageView btnSlide;
	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	private ImageButton _listButton;
	private ImageButton _recomend_button;
	private ImageButton _addEventButton;
	
	private RelativeLayout _bubleEvent;
	int key = 0;
	int key2 = 0;
	private Sliding popup;
	private Button _submitButton;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		setContentView(R.layout.main);
		setBehindContentView(R.layout.sliding_menu);
		getSlidingMenu().setSecondaryMenu(R.layout.sliding_recomendation);
		

		getSlidingMenu().setBehindOffset(80);

		Log.d("info", "testing entrance form activity");

		popup = (Sliding) findViewById(R.id.sliding1);
		popup.setVisibility(View.GONE);

//		_addButton = (ImageButton) findViewById(R.id.imgAddButton);
//		_addEventButton = (ImageButton) findViewById(R.id.imgAddEvent);
		_listButton = (ImageButton) findViewById(R.id.menuListButton);
		_recomend_button = (ImageButton) findViewById(R.id.imgRemove);
		
		_submitButton = (Button) findViewById(R.id.submitButton);

//		_addButton.setOnClickListener(this);
//		_addEventButton.setOnClickListener(this);
		_listButton.setOnClickListener(this);
		_recomend_button.setOnClickListener(this);

		_submitButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Log.d("debug::::", "---------- " + v.getId());
		switch (v.getId()) {
		case R.id.menuListButton:
			// put your code here
			toggle();
			break;

//		case R.id.imgAddButton:
//			intent = new Intent(this, FormActivity.class);
//
//			startActivity(intent);
//			break;
//
//		case R.id.imgAddEvent:
//			intent = new Intent(this, EventFormActivity.class);
//
//			startActivity(intent);
//			break;
			
		case R.id.imgRemove:
			intent = new Intent(this, EventFormActivity.class);
			startActivity(intent);
			
			break;

		case R.id.submitButton:
			key = 0;
			popup.setVisibility(View.GONE);
			break;

		}

	}

	// Testing functionality
	public void handleBubleEvent(View v) {
		if (key == 0) {
			key = 1;
			popup.setVisibility(View.VISIBLE);
		} else if (key == 1) {
			key = 0;
			popup.setVisibility(View.GONE);
		}
	}

}