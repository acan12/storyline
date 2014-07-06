package app.ui.helper;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import app.core.adapter.DBAdapter;
import app.core.component.DateTimePicker;
import app.core.component.PanelButtons;
import app.core.model.Story;
import app.util.StringManipulation;
import app.util.TimeUtil;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.R;

public class Helper {

	public static final int REQUEST_CODE_IMAGE_CAMERA = 0;
	public static final int REQUEST_CODE_IMAGE_GALLERY = 1;
	public static final int REQUEST_CODE_PICK_LOCATION = 2;

	public static final int MODE_NORMAL = 0;
	public static final int MODE_EDIT = 1;

	public static int OFFSET_VIEWGROUP = 3;

	public static Story singletonStory(Story story) {
		if (story == null) {
			story = new Story();
		}
		return story;
	}

	public static Story getStoryFromTag(Activity a) {
		View title = (View) a.findViewById(R.id.titleStory);
		return (Story) title.getTag();
	}

	public static int getBubbleIndex(int index, ViewGroup viewGroup) {
		if (viewGroup == null)
			return 0;
		return index + viewGroup.getChildCount() - Helper.OFFSET_VIEWGROUP;
	}

	public static void buildUIMain(Activity activity, Story story) {
		TextView tv = (TextView) activity.findViewById(R.id.titleStory);
		tv.setTag(story);

		if (story == null)
			tv.setText("");
		else
			tv.setText(StringManipulation.ellipsis(story.getName()
					.toUpperCase(), 100));
		
		tv = (TextView) activity.findViewById(R.id.top_storydate_button);
		if(story != null) {		tv.setText(TimeUtil.dateFormat(TimeUtil.fromEpochFormat(story.getStartDate())).toString()); 	}
		
		
		tv = (TextView) activity.findViewById(R.id.infoStart);
		if (story == null)
			tv.setText("");
		else
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getStartDate())).toString());

		tv = (TextView) activity.findViewById(R.id.bottom_storydate_button);
		if(story != null) {	tv.setText(		TimeUtil.dateFormat(TimeUtil.fromEpochFormat(story.getEndDate())).toString()	); }
		
		
		tv = (TextView) activity.findViewById(R.id.infoEnd);
		if (story == null)	tv.setText("");
		else tv.setText(TimeUtil.dateFormat(	TimeUtil.fromEpochFormat(story.getEndDate())).toString()	);

	}

	public static void buildUIStoryPopup(final Story story,
			final Dialog dialog, final Activity a) {
		if (story != null) {
			String[] items = a.getApplication().getResources()
					.getStringArray(R.array.categoryItems);
			List<String> list = Arrays.asList(items);

			EditText t = (EditText) dialog.findViewById(R.id.valueNameStory);
			t.setText(story.getName());
			t = (EditText) dialog.findViewById(R.id.valueDescriptionStory);
			t.setText(story.getDescription());

			Spinner sp = (Spinner) dialog.findViewById(R.id.categorySpinner);
			int pos = list.indexOf(story.getCategory());
			sp.setSelection(pos);

			TextView tv = (TextView) dialog.findViewById(R.id.valueDateStart);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getStartDate()),
					"EEE MMM d, yyyy"));
			tv = (TextView) dialog.findViewById(R.id.valueTimeStart);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getStartDate()), "k:mm a"));

			tv = (TextView) dialog.findViewById(R.id.valueDateEnd);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getEndDate()),
					"EEE MMM d, yyyy"));
			tv = (TextView) dialog.findViewById(R.id.valueTimeEnd);
			tv.setText(TimeUtil.dateFormat(
					TimeUtil.fromEpochFormat(story.getEndDate()), "k:mm a"));

		}

		// pick date
		LinearLayout ll;
		ll = (LinearLayout) dialog.findViewById(R.id.pickDateStart);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePicker.showDatePicker(dialog.getContext(),
						dialog.findViewById(R.id.valueDateStart));
			}
		});

		// pick time
		ll = (LinearLayout) dialog.findViewById(R.id.pickTimeStart);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePicker.showTimePicker(dialog.getContext(),
						dialog.findViewById(R.id.valueTimeStart));
			}
		});

		// pick date end
		ll = (LinearLayout) dialog.findViewById(R.id.pickDateEnd);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePicker.showDatePicker(dialog.getContext(),
						dialog.findViewById(R.id.valueDateEnd));
			}
		});

		// pick time end
		ll = (LinearLayout) dialog.findViewById(R.id.pickTimeEnd);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePicker.showTimePicker(dialog.getContext(),
						dialog.findViewById(R.id.valueTimeEnd));
			}
		});

		// submit button
		Button okButton = (Button) dialog.findViewById(R.id.submitStoryButton);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Story story2 = null;
				try {
					story2 = Helper.buildStoryFromDialog(story, dialog);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (story2 != null)
					Helper.buildUIMain(a, story2);

				dialog.dismiss();
			}
		});
	}

	public static Story buildStoryFromDialog(Story story, Dialog dialog)
			throws ParseException {

		story = singletonStory(story);

		EditText t01 = (EditText) dialog.findViewById(R.id.valueNameStory);
		EditText t02 = (EditText) dialog
				.findViewById(R.id.valueDescriptionStory);

		Spinner sp = (Spinner) dialog.findViewById(R.id.categorySpinner);

		TextView date01 = (TextView) dialog.findViewById(R.id.valueDateStart);
		TextView time01 = (TextView) dialog.findViewById(R.id.valueTimeStart);
		TextView date02 = (TextView) dialog.findViewById(R.id.valueDateEnd);
		TextView time02 = (TextView) dialog.findViewById(R.id.valueTimeEnd);

		if (t01.getText().toString().equals("")
				|| t02.getText().toString().equals("")
				|| date01.getText().toString().equals("")
				|| time01.getText().toString().equals("")
				|| date02.getText().toString().equals("")
				|| time02.getText().toString().equals(""))
			return null;

		int hour01 = Integer
				.parseInt(time01.getText().toString().split(":")[0]);
		int minute01 = Integer
				.parseInt(time01.getText().toString().split(":")[1].split(" ")[0]);

		int hour02 = Integer
				.parseInt(time02.getText().toString().split(":")[0]);
		int minute02 = Integer
				.parseInt(time02.getText().toString().split(":")[1].split(" ")[0]);

		story.setName(t01.getText().toString());
		story.setDescription(t02.getText().toString());
		story.setCategory((String) sp.getSelectedItem());
		story.setStartDate(TimeUtil.toEpochFormat(date01.getText().toString(),
				hour01, minute01));
		story.setEndDate(TimeUtil.toEpochFormat(date02.getText().toString(),
				hour02, minute02));

		return story;
	}

	// Handle Mode screen (normal, edit)
	public static void modeNormal(final Activity ac, ViewGroup viewGroup) {
		TextView mode = (TextView) ac.findViewById(R.id.mode);
		mode.setTag(MODE_NORMAL);

		ImageButton modeButton = (ImageButton) ac.findViewById(R.id.modeButton);
		modeButton.setImageDrawable(ac.getResources().getDrawable(
				R.drawable.mode_edit));

		ImageButton sb = (ImageButton) ac.findViewById(R.id.storyButton);
		sb.setImageDrawable(ac.getResources().getDrawable(
				R.drawable.paper_plane));
		sb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((HomeActivity) ac).showSecondaryMenu();
			}
		});

		PanelButtons.showPanel(ac, R.id.footer, false);

		View v = null;

		if (viewGroup != null) {
			v = (View) ac.findViewById(R.id.bubble_right);
			v.setClickable(false);

		}
		ac.findViewById(R.id.top_storydate_button).setVisibility(View.GONE);
		ac.findViewById(R.id.infoStart).setVisibility(View.VISIBLE);
		
		ac.findViewById(R.id.bottom_storydate_button).setVisibility(View.GONE);
		ac.findViewById(R.id.infoEnd).setVisibility(View.VISIBLE);
		
		ac.findViewById(R.id.addEventButton).setVisibility(View.GONE);

		// hide pointer add new event button
		if (viewGroup != null) {
			View bubble = ac.findViewById(R.id.body_content);
			bubble.setVisibility(View.GONE);

			Button deleteEvent;
			for (int i = 0; i < getBubbleIndex(0, viewGroup); i++) {
				deleteEvent = (Button) viewGroup.getChildAt(i + 1)
						.findViewById(R.id.delete_event);
				deleteEvent.setVisibility(View.VISIBLE);
			}
		}

	}

	public static void modeEdit(final Activity ac, ViewGroup viewGroup) {
		TextView mode = (TextView) ac.findViewById(R.id.mode);
		mode.setTag(MODE_EDIT);

		ImageButton modeButton = (ImageButton) ac.findViewById(R.id.modeButton);
		modeButton.setImageDrawable(ac.getResources().getDrawable(
				R.drawable.mode_normal));

		ImageButton sb = (ImageButton) ac.findViewById(R.id.storyButton);
		sb.setImageDrawable(ac.getResources().getDrawable(R.drawable.trash));
		sb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(ac);
				builder.setMessage("Are you sure remove this Story?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								TextView tv = (TextView) ac
										.findViewById(R.id.titleStory);
								Story story = (Story) tv.getTag();

								if (story != null) {

									DBAdapter db = new DBAdapter(ac
											.getApplicationContext());
									db.deleteStoryRecord(story.getId());

									ac.finish();
									ac.startActivity(ac.getIntent());
								}

								dialog.cancel();

							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

				builder.show();
			}
		});

		PanelButtons.showPanel(ac, R.id.footer, true);
		
//		top Button
		ac.findViewById(R.id.infoStart).setVisibility(View.GONE);
		TextView tv = (TextView) ac.findViewById(R.id.infoStart);
		String date = tv.getText().toString();	// date start
		
		if(!date.equals("")) ((TextView) ac.findViewById(R.id.top_storydate_button)).setText(date);
		ac.findViewById(R.id.top_storydate_button).setVisibility(View.VISIBLE);
		
		
		
//		bottom Button
		ac.findViewById(R.id.infoEnd).setVisibility(View.GONE);
		tv = (TextView) ac.findViewById(R.id.infoEnd);
		date = tv.getText().toString();	// date end
		
		if(!date.equals(""))  ((TextView) ac.findViewById(R.id.bottom_storydate_button)).setText(date);
		ac.findViewById(R.id.bottom_storydate_button).setVisibility(View.VISIBLE);
		
		
//		middle button
		ac.findViewById(R.id.addEventButton).setVisibility(View.VISIBLE);
		((TextView) ac.findViewById(R.id.addEventButton)).setText(ac.getResources().getString(R.string.label_add_event));

		
		if (viewGroup != null) {

			Button deleteEvent;
			for (int i = 0; i < getBubbleIndex(0, viewGroup); i++) {
				deleteEvent = (Button) viewGroup.getChildAt(i + 1)
						.findViewById(R.id.delete_event);
				deleteEvent.setVisibility(View.INVISIBLE);
			}

		}

		// show pointer add new event button
		View bubble = ac.findViewById(R.id.body_content);
		viewGroup = (ViewGroup) bubble.getParent();
		bubble.setVisibility(View.VISIBLE);

	}

	public static int getCurrentMode(Activity a) {
		TextView mode = (TextView) a.findViewById(R.id.mode);

		return (Integer) mode.getTag();
	}

}
