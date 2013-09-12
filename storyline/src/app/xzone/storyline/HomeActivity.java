package app.xzone.storyline;

import java.text.ParseException;
import java.util.ArrayList;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.component.Sliding;
import app.xzone.storyline.helper.AdapterHelper;
import app.xzone.storyline.helper.EventHelper;
import app.xzone.storyline.helper.Helper;
import app.xzone.storyline.model.Event;
import app.xzone.storyline.model.Story;
import app.xzone.storyline.util.TimeUtil;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class HomeActivity extends SlidingActivity implements OnClickListener {

	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	private ImageButton listButton;
	private ImageButton storyButton;
	private LinearLayout newStoryButton;
	private ViewGroup viewGroup;

	private int noBubble = 0; // index last saved bubble
	private int countBubble = 0; // count of bubble ready to save

	int key = 0;
	int key2 = 0;
	private Sliding popup;
	private Button submitEventButton;
	private Button cancelEventButton;

	private Story story;
	private Event event;
	private ArrayList<Event> events;

	final Context context = this;

	// accessing model storage
	DBAdapter db = null;

	// constanta
	private int OFFSET_VIEWGROUP = 3;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		// getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setContentView(R.layout.main);
		setBehindContentView(R.layout.sliding_menu);
		getSlidingMenu().setSecondaryMenu(R.layout.sliding_recomendation);

		getSlidingMenu().setBehindOffset(80);

		popup = (Sliding) findViewById(R.id.sliding1);
		popup.setVisibility(View.GONE);

		listButton = (ImageButton) findViewById(R.id.menuListButton);
		storyButton = (ImageButton) findViewById(R.id.storyButton);
		newStoryButton = (LinearLayout) findViewById(R.id.newStorySliding);
		submitEventButton = (Button) findViewById(R.id.submitEventButton);
		cancelEventButton = (Button) findViewById(R.id.cancelEventButton);

		listButton.setOnClickListener(this);
		storyButton.setOnClickListener(this);
		newStoryButton.setOnClickListener(this);
		submitEventButton.setOnClickListener(this);
		cancelEventButton.setOnClickListener(this);

		db = new DBAdapter(context);

		// read parameters passing to activity
		Bundle b = getIntent().getExtras();
		if (b == null) {
			if (story == null)
				story = db.getLastStory();
		} else {
			story = (Story) b.getSerializable("app.story");
		}

		events = new ArrayList<Event>();

		Helper.buildUIMain(HomeActivity.this, story);
		AdapterHelper.buildListViewAdapter(HomeActivity.this);

		if (story != null && story.getEvents() != null) {
			loadFirstTime();
		}

	}

	public void loadFirstTime() {
		for (int i = 0; i < story.getEvents().size(); i++) {
			viewGroup = AdapterHelper.buildBubbleEventAdapter(this, story
					.getEvents().get(i));
		}
		Helper.modeNormal(this, viewGroup);

		// reset viewGroup into null value
		noBubble = viewGroup.getChildCount() - OFFSET_VIEWGROUP;
		countBubble = 0;

		View bubble = findViewById(R.id.body_content);
		bubble.setVisibility(View.GONE);
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
						Helper.modeEdit(HomeActivity.this, viewGroup);

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

		case R.id.storyButton:
			showSecondaryMenu();
			break;

		case R.id.newStorySliding:
			listButton.performClick();
			story = null;
			Helper.buildUIMain(HomeActivity.this, story);
			Helper.modeEdit(HomeActivity.this, viewGroup);

			// routes to main activity
			finish();
			Intent intent = getIntent();
			intent.putExtra("app.story", (Story) null);
			startActivity(intent);

			break;

		case R.id.submitEventButton:
			key = 0;
			popup.setVisibility(View.GONE);

			event = new Event();
			// event handler for save event to storage
			event = EventHelper.buildEvent(this, event, story);

			viewGroup = AdapterHelper.buildBubbleEventAdapter(this, event);
			countBubble++;

			events.add(event);
			for (int i = 0; i < events.size(); i++) {
				Event e = (Event) events.get(i);
			}

			break;
		case R.id.cancelEventButton:

			key = 0;
			popup.setVisibility(View.GONE);

			break;
		}

	}

	// event handler when cancel edit button clicked
	public void saveEdit(View v) {
		View title = (View) findViewById(R.id.titleStory);
		story = (Story) title.getTag();

		// enter code show alert => your story not valid
		if (story == null)
			return;

		// save your story
		if (!story.isExist())
			story.setId((int) db.insertStoryRecord(story));
		else
			db.updateStoryRecord(story);

		// insert event to db
		for (int i = 0; i < events.size(); i++) {
			Event e = (Event) events.get(i);
			db.insertEventRecord(e, story.getId());
		}

		events.clear();
		Helper.modeNormal(this, viewGroup);
		AdapterHelper.buildListViewAdapter(HomeActivity.this);

		if (viewGroup != null) {
			// reset viewGroup into null value
			noBubble = viewGroup.getChildCount() - OFFSET_VIEWGROUP;
			countBubble = 0;

			viewGroup = null;
		}

	}

	public void cancelEdit(View v) {
		TextView title = (TextView) findViewById(R.id.titleStory);

		story = (Story) title.getTag();

		events.clear();

		if (story != null) {

			story = db.getStoryRecord(story.getId());
		} else {
			finish();
			Intent i = new Intent(this, HomeActivity.class);
			startActivity(i);
			return;
		}

		// remove bubble event still draft
		if (viewGroup != null) {
			viewGroup.removeViews((noBubble + 1), countBubble);
			countBubble = 0;

			// reset viewGroup into null value
			if (noBubble == 0)
				viewGroup = null;
		}

		Helper.buildUIMain(this, story);
		Helper.modeNormal(HomeActivity.this, viewGroup);

	}

	// event handler when notif icon clicked
	public void showDatePopup(View v) {
		TextView title = (TextView) findViewById(R.id.titleStory);
		story = (Story) title.getTag();

		LinearLayout ll = null;
		final Dialog dialog = new Dialog(HomeActivity.this);

		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.dialog_pick_story);

		dialog.setTitle(R.string.pick_story);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.paper_plane);

		if (story != null) {
			EditText t = (EditText) dialog.findViewById(R.id.valueNameStory);
			t.setText(story.getName());
			t = (EditText) dialog.findViewById(R.id.valueDescriptionStory);
			t.setText(story.getDescription());

			TextView tv = (TextView) dialog.findViewById(R.id.valueStartDate);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getStartDate()),
					"EEE MMM d, yyyy"));
			tv = (TextView) dialog.findViewById(R.id.valueStartTime);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getStartDate()), "k:mm a"));

			tv = (TextView) dialog.findViewById(R.id.valueEndDate);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getEndDate()),
					"EEE MMM d, yyyy"));
			tv = (TextView) dialog.findViewById(R.id.valueEndTime);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getEndDate()), "k:mm a"));

		}

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
				Story s = null;
				try {
					s = Helper.buildFromDateTimeStory(story, dialog);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (s != null)
					Helper.buildUIMain(HomeActivity.this, s);

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