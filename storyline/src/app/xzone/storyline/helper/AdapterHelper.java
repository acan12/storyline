package app.xzone.storyline.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.xzone.storyline.R;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.adapter.LazyAdapter;
import app.xzone.storyline.component.Sliding;
import app.xzone.storyline.model.Event;
import app.xzone.storyline.model.Story;

public class AdapterHelper {
	private static ListView list;
	private static LinearLayout ll;
	private static LazyAdapter adapter;

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
				ImageButton listButton = (ImageButton) a
						.findViewById(R.id.menuListButton);
				listButton.performClick();

				Story selectedStory = (Story) adapter.getItem(position);

				Helper.buildUIMain(a, selectedStory);

				// routes to main activity
				a.finish();
				Intent intent = a.getIntent();
				intent.putExtra("app.story", selectedStory);
				a.startActivity(intent);
			}

		});
	}

	public static ViewGroup updateBubbleEvent(final Activity a,
			final Event event) {

		int pointerNo = 0;

		View vi = event.getView();

		if (event.getTransportation().equals("Car")
				|| event.getTransportation().equals("Bus")
				|| event.getTransportation().equals("Walk")) {

			pointerNo = R.drawable.pointer_car;

		} else {
			// set plane pointer
			pointerNo = R.drawable.pointer_plane;
		}

		if (pointerNo > 0) {
			// show selected transport icon
			ImageView tr = (ImageView) vi.findViewById(R.id.transportation);
			tr.setBackgroundDrawable(vi.getResources().getDrawable(pointerNo));
		}
		vi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Helper.getCurrentMode(a) == Helper.MODE_EDIT) {
					Sliding popup = (Sliding) a.findViewById(R.id.sliding1);
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
		TextView title = (TextView) vi.findViewById(R.id.titleEvent);
		ImageView trans = (ImageView) vi.findViewById(R.id.transportation);
		TextView loc = (TextView) vi.findViewById(R.id.location);
		TextView note = (TextView) vi.findViewById(R.id.bubble_note);
		TextView date = (TextView) vi.findViewById(R.id.time_note);

		// set object event into element
		title.setTag(event);

		// set transportation pointer
		int pointerIcon = (event.getTransportation().equals("Flight")) ? R.drawable.pointer_plane
				: R.drawable.pointer_car;
		trans.setImageDrawable(a.getResources().getDrawable(pointerIcon));

		// set title of event
		title.setText(event.getName());
		// set location name
		loc.setText(event.getLocname());
		// set bubble note
		note.setText(event.getMessage());
		// set bubble date
		date.setText("7 Aug 2013 10:15 AM");

		View bubble = a.findViewById(R.id.body_content);
		ViewGroup parent = (ViewGroup) bubble.getParent();

		return parent;
	}

	public static ViewGroup buildBubbleEventAdapter(final Activity a,
			final Event event) {
		int pointerNo = 0;

		LayoutInflater inflater = (LayoutInflater) a.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View vi = inflater.inflate(R.layout.main_bubble_right, null);
		;

		if (event.getTransportation().equals("Car")
				|| event.getTransportation().equals("Bus")
				|| event.getTransportation().equals("Walk")) {
			vi = inflater.inflate(R.layout.main_bubble_right_images, null);

			pointerNo = R.drawable.pointer_car;

		} else if (event.getTransportation().equals("Flight")) {
			// set plane pointer
			pointerNo = R.drawable.pointer_plane;
		}

		if (pointerNo > 0) {
			// show selected transport icon
			ImageView tr = (ImageView) vi.findViewById(R.id.transportation);
			tr.setBackgroundDrawable(vi.getResources().getDrawable(pointerNo));
		}

		// set view into event
		event.setView(vi);

		vi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Helper.getCurrentMode(a) == Helper.MODE_EDIT) {
					Sliding popup = (Sliding) a.findViewById(R.id.sliding1);
					popup.setVisibility(View.VISIBLE);

					if (event.getView() == null)
						System.out.println("---- view null 01 ---");
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

		final View vi2 = vi;
		Button deleteEvent = (Button) vi.findViewById(R.id.deleteEvent);
		deleteEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(a).create();
				dialog.setTitle("Confirmation Remove The Event");
				dialog.setMessage("This will remove your event !!");
				dialog.setCancelable(false);
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								View bubble = (RelativeLayout) vi2
										.findViewById(R.id.bubbleEvent);
								bubble.setBackgroundColor(Color.GRAY);
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
		TextView title = (TextView) vi.findViewById(R.id.titleEvent);
		ImageView trans = (ImageView) vi.findViewById(R.id.transportation);
		TextView loc = (TextView) vi.findViewById(R.id.location);
		TextView note = (TextView) vi.findViewById(R.id.bubble_note);
		TextView date = (TextView) vi.findViewById(R.id.time_note);

		// set object event into element
		title.setTag(event);

		// set transportation pointer
		int pointerIcon = (event.getTransportation().equals("Flight")) ? R.drawable.pointer_plane
				: R.drawable.pointer_car;
		trans.setImageDrawable(a.getResources().getDrawable(pointerIcon));

		// set title of event
		title.setText(event.getName());
		// set location name
		loc.setText(event.getLocname());
		// set bubble note
		note.setText(event.getMessage());
		// set bubble date
		date.setText("7 Aug 2013 10:15 AM");

		View bubble = a.findViewById(R.id.body_content);
		ViewGroup parent = (ViewGroup) bubble.getParent();
		int index = parent.indexOfChild(bubble);

		parent.addView(vi, index, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		return parent;
	}
}
