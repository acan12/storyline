package app.ui.helper;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.core.adapter.DBAdapter;
import app.core.adapter.ImageAdapter;
import app.core.adapter.LazyAdapter;
import app.core.component.PanelButtons;
import app.core.component.Sliding;
import app.core.model.Event;
import app.core.model.Story;
import app.util.TimeUtil;
import app.xzone.storyline.R;

public class ListViewHelper {
	private static ListView list;
	private static LinearLayout ll;
	private static LazyAdapter adapter;
	private static boolean isUpload = true;
	private static boolean showButtonsPanel = false;;
	private static View currentView = null;

	public static void buildListViewAdapter(final Activity a) {
		DBAdapter db = new DBAdapter(a.getApplicationContext());

		list = (ListView) a.findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(a, db.getAllStory());
		list.setAdapter(adapter);

		// Click event handler for single row item
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Story selectedStory = (Story) adapter.getItem(position);

				ProgressDialog.show(a, null, "Loading ...");

				// routes to main activity
				a.finish();
				Intent intent = a.getIntent();
				intent.putExtra("app.story", selectedStory);

				a.startActivity(intent);

			}

		});
	}

	public static void setCurrentlyPointer(Event event, View vi) {
	
		View v = vi.findViewById(R.id.bubble_event_header);
		int pointer_id = (v.getVisibility() == View.VISIBLE) ? R.id.pointer_2 : R.id.pointer_1;
		int pointer_id_hide = (v.getVisibility() == View.VISIBLE) ? R.id.pointer_1 : R.id.pointer_2;
		
		ImageView pointer = (ImageView) vi.findViewById(pointer_id);
		pointer.setVisibility(View.VISIBLE);
		
		ImageView pointer_hide = (ImageView) vi.findViewById(pointer_id_hide);
		pointer_hide.setVisibility(View.GONE);
		
		
		if (event.getStartDate() <= System.currentTimeMillis()) {
			pointer.setImageResource(R.drawable.pointer_fill);
		} else {
			pointer.setImageResource(R.drawable.pointer);
		}
		
	}
	
	public static ViewGroup updateBubbleEvent(final Activity a,
			final Event event) {

		int pointerNo = 0;

		View vi = event.getView();

		// set pointer_full show event information submitted
		setCurrentlyPointer(event, vi);

		vi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Helper.getCurrentMode(a) == Helper.MODE_EDIT) {
					Sliding popup = (Sliding) a.findViewById(R.id.sliding);
					popup.setVisibility(View.VISIBLE);

					EventHelper.buildUISliding(a, event);

				}

				// Put here the code handle event when bubble clicked
				// Intent i = new Intent(
				// Intent.ACTION_PICK,
				// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// a.startActivityForResult(i,
				// Helper.REQUEST_CODE_IMAGE_GALLERY);
			}
		});

		// construct UI element for bubble event
		TextView title = (TextView) vi.findViewById(R.id.title_event);
		ImageView trans = (ImageView) vi.findViewById(R.id.transportation);
		TextView loc = (TextView) vi.findViewById(R.id.location);
		TextView note = (TextView) vi.findViewById(R.id.bubble_note);
		TextView date = (TextView) vi.findViewById(R.id.date_note);
		TextView time = (TextView) vi.findViewById(R.id.time_note);

		// set object event into element
		title.setTag(event);

		// set transportation pointer
		if (event.getTransportation().equals("Flight")) {
			// set plane pointer
			pointerNo = R.drawable.pointer_plane;
		} else if (event.getTransportation().equals("Car")) {
			pointerNo = R.drawable.pointer_car;
		} else if (event.getTransportation().equals("Bus")) {
			pointerNo = R.drawable.pointer_bus;
		} else if (event.getTransportation().equals("Train")) {
			pointerNo = R.drawable.pointer_train;
		} else if (event.getTransportation().equals("Walk")) {
			pointerNo = R.drawable.pointer_walking;
		} else {
			pointerNo = 0;
		}

		if (pointerNo > 0) {
			// show selected transport icon
			trans.setVisibility(View.VISIBLE);
			trans.setImageDrawable(a.getResources().getDrawable(pointerNo));
		} else
			trans.setVisibility(View.INVISIBLE);

		// set title of event
		title.setText(event.getName());
		// set location name
		if(event.getLocname() == null)
			loc.setVisibility(View.GONE);
		else
			loc.setText(event.getLocname());
		
		// set bubble note
		note.setText(event.getMessage());
		// set bubble date

		date.setTextSize(14f);
		date.setText(TimeUtil.dateFormat(
				TimeUtil.fromEpochFormat(event.getStartDate()),
				"EEE MMM d, yyyy"));
		time.setTextSize(12f);
		time.setText(TimeUtil.dateFormat(
				TimeUtil.fromEpochFormat(event.getStartDate()), "k:mm a"));

		View bubble = a.findViewById(R.id.body_content);
		ViewGroup parent = (ViewGroup) bubble.getParent();

		return parent;
	}

	public static ViewGroup buildBubbleEventAdapter(final Activity a,
			final Event event, boolean showRemoveEvent) {
		int pointerNo = 0;

		LayoutInflater inflater = (LayoutInflater) a.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// View vi = inflater.inflate(R.layout.main_bubble_right, null);
		View vi = inflater.inflate(R.layout.main_bubble_right_images, null);

		// HorizontalListHelper.buildHorizontalView(vi);

		// testing integrate with image from sdcard as external storage , must
		// use layout: main_bubble_right_image.xml
		
		String[] paths = ImageAdapter.getPhotosPath(event);
		
		if(paths != null) ImageAdapter.buildPhotosLayout(event, vi, a);
		
//		if(paths != null){
//			String fPath = "";
//			for(String f : paths){
//				fPath = "/sdcard/Storyline/photos/"+f;
//				File imageFile = new File(fPath);
//			}
//			
//		}
//		 File imageFile = new File("/sdcard/Storyline/photos/image_001.jpg");
		// ImageButton thumb = (ImageButton) vi.findViewById(R.id.pic01);
		// BitmapDrawable d = new BitmapDrawable(vi.getResources(),
		// imageFile.getAbsolutePath());
		// thumb.setImageDrawable(d);

		// set view into event
		event.setView(vi);

		vi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Helper.getCurrentMode(a) == Helper.MODE_EDIT) {
					Sliding popup = (Sliding) a.findViewById(R.id.sliding);
					popup.setVisibility(View.VISIBLE);

					EventHelper.buildUISliding(a, event);

				}
			}
		});

		vi.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				v.setTag(event);
				currentView = v;

				if (Helper.getCurrentMode(a) == Helper.MODE_NORMAL) {

					if (isUpload) {
						showButtonsPanel = PanelButtons.showPanel(a,
								R.id.footer_function, true);
						RelativeLayout bm = (RelativeLayout) v
								.findViewById(R.id.bubble_image_right);
						RelativeLayout br = (RelativeLayout) v
								.findViewById(R.id.bubble_right);

						bm.setBackgroundResource(R.drawable.bubble_image_selected);
						br.setBackgroundResource(R.drawable.bubble_right_selected);
						isUpload = false;

					} else {
						showButtonsPanel = PanelButtons.showPanel(a,
								R.id.footer_function, false);
						RelativeLayout bm = (RelativeLayout) v
								.findViewById(R.id.bubble_image_right);
						RelativeLayout br = (RelativeLayout) v
								.findViewById(R.id.bubble_right);

						bm.setBackgroundResource(R.drawable.bubble_image);
						br.setBackgroundResource(R.drawable.bubble_right);
						isUpload = true;

					}
				}

				return true;
			}
		});

		final View vi2 = vi;

		// set pointer_full show information event already pass
		setCurrentlyPointer(event, vi);

		Button deleteEvent = (Button) vi.findViewById(R.id.delete_event);
		if (showRemoveEvent)
			deleteEvent.setVisibility(View.VISIBLE);
		deleteEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(a).create();
				dialog.setTitle("Remove Event");
				dialog.setMessage("Are you sure remove this event?");
				dialog.setCancelable(false);
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								DBAdapter db = new DBAdapter(a
										.getApplicationContext());

								// go to story in main page
								TextView bubbleTitle = (TextView) vi2
										.findViewById(R.id.title_event);

								Event ev = (Event) bubbleTitle.getTag();
								Story st = ev.getStory();

								// remove event
								if (db.deleteEventRecord(ev.getId())) {
									// show success notification
									Toast.makeText(a.getApplicationContext(),
											"Remove Event Success!!", 10)
											.show();

									// remove object collection for event
									ArrayList<Event> es = st.getEvents();
									es.remove(ev);
									st.setEvents(es);

									// reload story in main page
									a.finish();
									Intent intent = a.getIntent();
									intent.putExtra("app.story", st);
									a.startActivity(intent);
								} else {
									// show fail notification
									Toast.makeText(a.getApplicationContext(),
											"Failed to remove!!", 10).show();
								}

							}
						});

				dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.show();
				return;
			}
		});

		// construct UI element for bubble event
		TextView title = (TextView) vi.findViewById(R.id.title_event);
		ImageView trans = (ImageView) vi.findViewById(R.id.transportation);
		TextView loc = (TextView) vi.findViewById(R.id.location);
		TextView note = (TextView) vi.findViewById(R.id.bubble_note);
		TextView date = (TextView) vi.findViewById(R.id.date_note);
		TextView time = (TextView) vi.findViewById(R.id.time_note);

		// set object event into element
		title.setTag(event);

		// set transportation pointer
		if (event.getTransportation().equals("Flight")) {
			// set plane pointer
			pointerNo = R.drawable.pointer_plane;
		} else if (event.getTransportation().equals("Car")) {
			pointerNo = R.drawable.pointer_car;
		} else if (event.getTransportation().equals("Bus")) {
			pointerNo = R.drawable.pointer_bus;
		} else if (event.getTransportation().equals("Train")) {
			pointerNo = R.drawable.pointer_train;
		} else if (event.getTransportation().equals("Walk")) {
			pointerNo = R.drawable.pointer_walking;
		} else {
			pointerNo = 0;
		}

		if (pointerNo > 0) {
			// show selected transport icon
			trans.setVisibility(View.VISIBLE);
			trans.setImageDrawable(a.getResources().getDrawable(pointerNo));
		} else
			trans.setVisibility(View.INVISIBLE);

		// set title of event
		title.setText(event.getName());
		// set location name
		loc.setText(event.getLocname());
		// set bubble note
		note.setText(event.getMessage());
		// set bubble date

		date.setTextSize(14f);
		date.setText(TimeUtil.dateFormat(
				TimeUtil.fromEpochFormat(event.getStartDate()),
				"EEE MMM d, yyyy"));
		time.setTextSize(12f);
		time.setText(TimeUtil.dateFormat(
				TimeUtil.fromEpochFormat(event.getStartDate()), "k:mm a"));

		View bubble = a.findViewById(R.id.body_content);
		ViewGroup parent = (ViewGroup) bubble.getParent();
		int index = parent.indexOfChild(bubble);

		parent.addView(vi, index, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		return parent;
	}


	public static View getCurrentView() {
		return currentView;
	}

}
