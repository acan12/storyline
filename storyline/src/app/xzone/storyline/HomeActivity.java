package app.xzone.storyline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.component.Sliding;
import app.xzone.storyline.helper.Helper;
import app.xzone.storyline.model.Story;

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
	private ImageButton _story_button;
	private ImageButton _addEventButton;

	int key = 0;
	int key2 = 0;
	private Sliding popup;
	private Button _submitButton;

	final Context context = this;

	// accessing model storage
	DBAdapter db = null;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getSlidingMenu().setMode(SlidingMenu.LEFT);
		// getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setContentView(R.layout.main);
		setBehindContentView(R.layout.sliding_menu);
		// getSlidingMenu().setSecondaryMenu(R.layout.sliding_recomendation);

		getSlidingMenu().setBehindOffset(80);

		popup = (Sliding) findViewById(R.id.sliding1);
		popup.setVisibility(View.GONE);

		_listButton = (ImageButton) findViewById(R.id.menuListButton);
		_story_button = (ImageButton) findViewById(R.id.storyButton);
		_submitButton = (Button) findViewById(R.id.submitEventButton);

		_listButton.setOnClickListener(this);
		_story_button.setOnClickListener(this);
		_submitButton.setOnClickListener(this);

		db = new DBAdapter(context);
		Story story = db.getLastStory();
		Helper.buildUIMain(HomeActivity.this, story);

	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem item = menu.add("Map").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Intent intent = new Intent(HomeActivity.this,
								MapPlaceActivity.class);
						startActivity(intent);

						return true;
					}
				});
		item.setIcon(R.drawable.map);
		return true;
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

		// case R.id.imgAddButton:
		// intent = new Intent(this, FormActivity.class);
		//
		// startActivity(intent);
		// break;
		//
		// case R.id.imgAddEvent:
		// intent = new Intent(this, EventFormActivity.class);
		//
		// startActivity(intent);
		// break;

		case R.id.storyButton:

			Helper.showPopupNewStory(HomeActivity.this);

			break;

		case R.id.submitEventButton:
			key = 0;
			popup.setVisibility(View.GONE);
			break;

		
		}

	}

//	event handler when notif icon clicked
	public void showDatePopup(View v){
		final Dialog dialog = new Dialog(HomeActivity.this);

		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.dialog_insert_datetime);

		dialog.setTitle("Pick DateTime");

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.clock_white);

		Button okButton = (Button) dialog
				.findViewById(R.id.submitDateButton);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("masuk click event");
				// db = new DBAdapter(HomeActivity.this);
				//
				// Story story = Helper.buildObjectStory(
				// new Story(), dialog);
				// long sid = db.insertStoryRecord(story);
				//
				// if(sid > 0) Helper.buildUIMain(HomeActivity.this, story);
				dialog.dismiss();
			}
		});
	}
	
	// event handler direct from layout xml
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