package app.xzone.storyline;

import java.text.ParseException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private ImageButton listButton;
	private LinearLayout newStoryButton;
	private ImageButton _addEventButton;

	int key = 0;
	int key2 = 0;
	private Sliding popup;
	private Button _submitButton;

	private Story story;

	final Context context = this;

	// accessing model storage
	DBAdapter db = null;
	private Button cancelButton;

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

		listButton = (ImageButton) findViewById(R.id.menuListButton);
		newStoryButton = (LinearLayout) findViewById(R.id.newStorySliding);
		_submitButton = (Button) findViewById(R.id.submitEventButton);
		

		listButton.setOnClickListener(this);
		newStoryButton.setOnClickListener(this);
		_submitButton.setOnClickListener(this);
		
		
		
		db = new DBAdapter(context);
		story = db.getLastStory();
		Helper.buildUIMain(HomeActivity.this, story);

	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem item01 = menu.add("Map").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Intent intent = new Intent(HomeActivity.this,
								MapPlaceActivity.class);
						startActivity(intent);

						return true;
					}
				});

		MenuItem item02 = menu.add("Edit").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Helper.modeEdit(HomeActivity.this);

						
						return true;
					}
				});
		item01.setIcon(R.drawable.map);
		item02.setIcon(R.drawable.list);
		return true;
	}

	@Override
	public void onClick(View v) {
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
		
		case R.id.newStorySliding:
			listButton.performClick();
			Helper.modeEdit(HomeActivity.this);
			showDatePopup(v);

			break;
		// case R.id.storyButton:
		//
		// // Helper.showPopupNewStory(HomeActivity.this, story);
		// final Dialog dialog = new Dialog(this, R.style.dialog_style);
		//
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.setContentView(R.layout.dialog_insert_story);
		//
		// dialog.show();
		//
		// Button okButton = (Button) dialog
		// .findViewById(R.id.submitStoryButton);
		// okButton.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// // DBAdapter db = new DBAdapter(context);
		// story = Helper.buildFromTitleStory(story, dialog);
		//
		// // long sid = db.insertStoryRecord(story);
		// // if (sid > 0)
		//
		// System.out.println("1.name :"+story.getName());
		// System.out.println("2.desc :"+story.getDescription());
		// Helper.buildUIMain(HomeActivity.this, story);
		// dialog.dismiss();
		// }
		// });
		//
		// break;

		case R.id.submitEventButton:
			key = 0;
			popup.setVisibility(View.GONE);
			break;

		}

	}

//	event handler when cancel edit button clicked
	public void saveEdit(View v){
		View title = (View) findViewById(R.id.titleStory);
		story = (Story) title.getTag();
		System.out.println("----after save cek object story="+story.getName());
	}
	
	public void cancelEdit(View v){
		Helper.modeNormal(HomeActivity.this);
	}
	
	// event handler when notif icon clicked
	public void showDatePopup(View v) {
		LinearLayout ll = null;
		final Dialog dialog = new Dialog(HomeActivity.this);

		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.dialog_insert_datetime);

		dialog.setTitle("Pick Story");

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.paper_plane);

		// pick date
		ll = (LinearLayout) dialog.findViewById(R.id.pickDate);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Helper.showDatePicker(dialog, R.id.valueStartDate);
			}
		});

		// pick time
		ll = (LinearLayout) dialog.findViewById(R.id.pickTime);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Helper.showTimePicker(dialog, R.id.valueStartTime);
			}
		});

		// pick date end
		ll = (LinearLayout) dialog.findViewById(R.id.pickDateEnd);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Helper.showDatePicker(dialog, R.id.valueEndDate);
			}
		});

		// pick time end
		ll = (LinearLayout) dialog.findViewById(R.id.pickTimeEnd);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Helper.showTimePicker(dialog, R.id.valueEndTime);
			}
		});

		// submit button
		Button okButton = (Button) dialog.findViewById(R.id.submitDateButton);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					story = Helper.buildFromDateTimeStory(story, dialog);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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