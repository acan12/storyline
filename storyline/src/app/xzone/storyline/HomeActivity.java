package app.xzone.storyline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.adapter.ImageAdapter;
import app.xzone.storyline.component.DateTimePicker;
import app.xzone.storyline.component.PanelButtons;
import app.xzone.storyline.component.ProgressCustomDialog;
import app.xzone.storyline.component.Sliding;
import app.xzone.storyline.helper.ListViewHelper;
import app.xzone.storyline.helper.EventHelper;
import app.xzone.storyline.helper.Helper;
import app.xzone.storyline.model.Event;
import app.xzone.storyline.model.Story;
import app.xzone.storyline.util.StringManipulation;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class HomeActivity extends SlidingActivity implements OnClickListener {

	private Sliding popup;
	private ImageView imageEvent;
	private ImageButton listButton, allButton, storyButton;
	private Button submitEventButton, cancelEventButton;
	private LinearLayout newStoryButton, pickDateEvent, pickTimeEvent;
	private Dialog dialog;
	private ProgressDialog progress = null;
	private ViewGroup viewGroup;

	private final Context context = this;
	private DBAdapter db = null;
	private Story story;

	private ArrayList<Event> events; // list of events consists previous event +
										// draft event
	private ArrayList<Event> prevEvents; // list of events consists saved event
	private Map<String, String> params;

	int key1 = 0;
	int key2 = 0;
	private boolean showEditPanel;
	private boolean showButtonsPanel = false;;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		setBehindContentView(R.layout.sliding_menu);
		getSlidingMenu().setSecondaryMenu(R.layout.sliding_recomendation);
		getSlidingMenu().setBehindOffset(80);

		popup = (Sliding) findViewById(R.id.sliding);
		popup.setVisibility(View.GONE);

		listButton = (ImageButton) findViewById(R.id.menuListButton);
		allButton = (ImageButton) findViewById(R.id.allButton);
		storyButton = (ImageButton) findViewById(R.id.storyButton);
		newStoryButton = (LinearLayout) findViewById(R.id.newStorySliding);
		submitEventButton = (Button) findViewById(R.id.submitEventButton);
		cancelEventButton = (Button) findViewById(R.id.cancelEventButton);
		pickDateEvent = (LinearLayout) findViewById(R.id.pickDateEvent);
		pickTimeEvent = (LinearLayout) findViewById(R.id.pickTimeEvent);
		

		listButton.setOnClickListener(this);
		allButton.setOnClickListener(this);
		storyButton.setOnClickListener(this);
		newStoryButton.setOnClickListener(this);
		submitEventButton.setOnClickListener(this);
		cancelEventButton.setOnClickListener(this);
		pickDateEvent.setOnClickListener(this);
		pickTimeEvent.setOnClickListener(this);

		prevEvents = new ArrayList<Event>();
		db = new DBAdapter(context);

		// set global var 'story' from parameters passing to activity
		setStoryWithParams(getIntent().getExtras());

		// set ui
		loadAvatarImageAsync();
		Helper.buildUIMain(HomeActivity.this, story);
		ListViewHelper.buildListViewAdapter(HomeActivity.this);

		// set array event collection
		events = (ArrayList<Event>) prevEvents.clone();

		// if null will go to new page in mode edit
		if (story == null || story.getEvents() == null) {
			Helper.modeEdit(this, viewGroup);
			showEditPanel = true;

		} else {
			renderTimeline(story.getEvents());
			showEditPanel = false;
		}

	}

	private void setStoryWithParams(Bundle bundle) {
		boolean hasBundle = (bundle != null);

		if (!hasBundle) {
			if (story == null) {
				Story s = db.getLastStory();
				if (s != null) {
					prevEvents = s.getEvents();
				}

				// set global var 'story' equal 's'
				story = s;
			} else {
				Helper.modeEdit(this, viewGroup);

			}

		} else {
			// calling from menu list story
			story = (Story) bundle.getSerializable("app.story");

			// store all event from this story
			if (story != null) {
				prevEvents = story.getEvents();
			}

		}

	}

	public void renderTimeline(ArrayList<Event> items) {

		for (int i = 0; i < items.size(); i++) {

			viewGroup = ListViewHelper.buildBubbleEventAdapter(this,
					items.get(i), true);
		}
		Helper.modeNormal(this, viewGroup);

		View bubble = findViewById(R.id.body_content);
		bubble.setVisibility(View.GONE);
	}

	public void renderEmptyTimeline() {
		Helper.modeNormal(this, viewGroup);

		View bubble = findViewById(R.id.body_content);
		bubble.setVisibility(View.GONE);
	}

	// event handler when cancel edit button clicked
	public void saveEdit(View v) {

		story = (Story) Helper.getStoryFromTag(this);

		// enter code show alert => your story not valid
		if (story == null)
			return;

		// save your story
		if (story.hasObject()) {
			db.updateStoryRecord(story);
		} else {
			story.setId((int) db.insertStoryRecord(story));
		}
		// make empty collection for ready to fill
		if (prevEvents != null) {
			prevEvents.clear();
		}

		// insert / update event to db
		for (int i = 0; i < events.size(); i++) {
			Event e = (Event) events.get(i);

			if (e.getId() > 0) {
				db.updateEventRecord(e);
			} else {
				e.setId((int) db.insertEventRecord(e, story.getId()));
			}

			prevEvents.add(e);
		}

		Helper.modeNormal(this, viewGroup);
		ListViewHelper.buildListViewAdapter(HomeActivity.this);

	}

	public void cancelEdit(View v) {
		TextView title = (TextView) findViewById(R.id.titleStory);

		story = (Story) title.getTag();

		// revert into last event data
		rollbackTimeline(this, prevEvents);

		// rollback events as global var into equal with saved events
		events.clear();
		for (int i = 0; i < prevEvents.size(); i++) {
			events.add(prevEvents.get(i));
		}
	}

	public void showNewEvent(View v) {

		Sliding popup = (Sliding) findViewById(R.id.sliding);
		popup.setVisibility(View.VISIBLE);
		EventHelper.buildUISliding(this, null);
	}

	// rollback configuration before modified, if cancel
	public void rollbackTimeline(Activity a, ArrayList<Event> prevs) {
		if (prevs.size() > 0) {

			if (events.size() > 0)
				viewGroup.removeViews(1, events.size());
			renderTimeline(prevEvents);
		} else {
			renderEmptyTimeline();
		}
	}

	// event handler when notif icon clicked
	public void addStoryPopup(View v) {
		story = Helper.getStoryFromTag(this);

		LinearLayout ll = null;
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.dialog_pick_story);
		dialog.setTitle(R.string.pick_story);
		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.paper_plane);

		Helper.buildUIStoryPopup(story, dialog, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// initialize image container
		LinearLayout bubbleImage = (LinearLayout) findViewById(R.id.bubble_image_header);
		
		
		switch (requestCode) {
		case Helper.REQUEST_CODE_IMAGE_CAMERA:
			if (resultCode == RESULT_OK) {

				Bitmap photo = (Bitmap) data.getExtras().get("data");
				
	            Bitmap bmp = ImageAdapter.getResizedBitmap(photo, 56, 70);
	            
	            imageEvent = new ImageView(this);
	            imageEvent.setImageBitmap(bmp);
	            imageEvent.setPadding(5, 1, 5, 3);
	            bubbleImage.addView(imageEvent, 0);
	            
	         // store into file 
	            try {
	            	Event event = EventHelper.getEventFromTag(this);
					ImageAdapter.copyFile(photo, event, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;

		case Helper.REQUEST_CODE_IMAGE_GALLERY:
			if (resultCode == RESULT_OK && null != data) {

	            Uri selectedImageUri = data.getData();
	            String selectedImagePath= ImageAdapter.getPath(selectedImageUri, this);
	            
	            Bitmap bmp = ImageAdapter.getResizedBitmap(BitmapFactory.decodeFile(selectedImagePath), 56, 70);
	            
	            imageEvent = new ImageView(this);
	            imageEvent.setImageBitmap(bmp);
	            imageEvent.setPadding(5, 1, 5, 3);
	            
	            
	            bubbleImage.addView(imageEvent, 0);
	            
	            
	            // store into file 
	            System.out.println("---- store file");
	            try {
	            	Event event = EventHelper.getEventFromTag(this);
					ImageAdapter.copyFile(bmp, event, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
			}
			break;
		case Helper.REQUEST_CODE_PICK_LOCATION:
			if (resultCode == RESULT_OK && data != null) {
				if (data.getExtras().containsKey("location")) {
					String locname = data.getStringExtra("location");

					double[] coordinates = { data.getDoubleExtra("lat", 0d),
							data.getDoubleExtra("lng", 0d) };

					TextView tv = (TextView) findViewById(R.id.locationEvent);
					tv.setText(StringManipulation.ellipsis(locname, 30));
					tv.setTag(coordinates);
				}
			}
			break;
		}
	}

	// * Hide menu from standar android *
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// MenuItem item01 = menu.add("Map").setOnMenuItemClickListener(
	// new OnMenuItemClickListener() {
	//
	// @Override
	// public boolean onMenuItemClick(MenuItem item) {
	// Intent intent = new Intent(HomeActivity.this,
	// MapPlaceActivity.class);
	// startActivity(intent);
	//
	// return true;
	// }
	// });
	//
	// MenuItem item02 = menu.add("Edit").setOnMenuItemClickListener(
	// new OnMenuItemClickListener() {
	//
	// @Override
	// public boolean onMenuItemClick(MenuItem item) {
	// Helper.modeEdit(HomeActivity.this, viewGroup);
	//
	// return true;
	// }
	// });
	// item01.setIcon(R.drawable.map);
	// item02.setIcon(R.drawable.list);
	// return true;
	// }

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.menuListButton:
			// put your code here
			toggle();
			break;

//		case R.id.allButton:
//
//			// set show buttons panel
//			showButtonsPanel = PanelButtons.showPanel(this,
//					R.id.footer_function, showButtonsPanel);
//
//			// Hide the Panel
//			Helper.modeNormal(HomeActivity.this, viewGroup);
//			showEditPanel = false;
//			break;

		case R.id.storyButton:
			showSecondaryMenu();
			break;

		case R.id.newStorySliding:

			progress = ProgressDialog.show(this, null, "Loading");
			// routes to main activity
			finish();
			Intent intent = getIntent();
			intent.putExtra("app.story", (Story) null);
			startActivityForResult(intent, ProgressCustomDialog.LOAD_PROGRESS);

			break;

		case R.id.pickDateEvent:
			DateTimePicker.showDatePicker(context,
					findViewById(R.id.valueDateEvent));
			break;

		case R.id.pickTimeEvent:
			DateTimePicker.showTimePicker(context,
					findViewById(R.id.valueTimeEvent));
			break;

		case R.id.submitEventButton:
			key1 = 0;
			popup.setVisibility(View.GONE);

			// check if existed event or new event
			Event event = EventHelper.getEventFromTag(this);

			if (event != null) {

				// create duplicate object to keep save value not modified yet
				Event e = null;
				try {
					e = (Event) event.clone();

					// update / modify event data
					EventHelper.buildEvent(this, event, story);

				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

				viewGroup = ListViewHelper.updateBubbleEvent(this, event);

				// replace with modified event
				int index = events.indexOf(event);

				events.set(index, event);
				prevEvents.set(index, e);
			} else {

				// event handler for save event to storage
				try {
					event = EventHelper.buildEvent(this,
							(event == null) ? new Event() : event, story);
				} catch (ParseException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				viewGroup = ListViewHelper.buildBubbleEventAdapter(this, event,
						false);

				events.add(event);
			}

			// reset object event
			event = null;
			break;

		case R.id.cancelEventButton:

			key1 = 0;
			popup.setVisibility(View.GONE);

			break;

		}

	}

	// * pick location from map view
	public void pickLocation(View v) {
		Intent intent = new Intent(this, LocationPickerActivity.class);
		startActivityForResult(intent, Helper.REQUEST_CODE_PICK_LOCATION);
	}

	// * routes to map page
	public void goMap(View v) {
		Intent intent = new Intent(HomeActivity.this, MapPlaceActivity.class);
		startActivity(intent);
	}

	// * pick image from camera device
	public void goCamera(View v) {
		ImageAdapter.takeCamera(this);
	}

	public void goGallery(View v) {
		ImageAdapter.takeGallery(this);
	}

	public void showNavigation(View v) {

		if (!showEditPanel) {
			Helper.modeEdit(HomeActivity.this, viewGroup);
			showEditPanel = true;
			
		} else {
			// Hide the Panel
			Helper.modeNormal(HomeActivity.this, viewGroup);
			showEditPanel = false;
			
		}
	}

	private void loadAvatarImageAsync() {
		// Fetch data from shared preferences
		SharedPreferences settings = getSharedPreferences("facebook",
				Activity.MODE_WORLD_READABLE);
		String avatarLink = settings.getString("avatar", "");

		new DownloadImageTask((ImageView) findViewById(R.id.avatar))
				.execute(avatarLink);
	}

	// # Async Task
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap bmp) {
			bmImage.setImageBitmap(bmp);
		}
	}
}
