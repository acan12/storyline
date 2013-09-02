package app.xzone.storyline.helper;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import app.xzone.storyline.R;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.model.Story;
import app.xzone.storyline.util.StringManipulation;
import app.xzone.storyline.util.TimeUtil;

public class Helper {
	private static EditText t;

	public static Story buildObjectStory(Story story, Dialog dialog) {

		t = (EditText) dialog.findViewById(R.id.storyName);
		story.setName(t.getText().toString());

		t = (EditText) dialog.findViewById(R.id.storyDescription);
		story.setDescription(t.getText().toString());

		story.setStatus(0);
		story.setShared(0);
		story.setStartDate(String.valueOf(Calendar.getInstance()
				.getTimeInMillis()));
		story.setStartTime(String.valueOf(Calendar.getInstance()
				.getTimeInMillis()));
		story.setEndDate(String.valueOf(Calendar.getInstance()
				.getTimeInMillis()));
		story.setEndTime(String.valueOf(Calendar.getInstance()
				.getTimeInMillis()));

		return story;
	}

	public static void buildUIMain(Activity activity, Story story) {

		if (story == null)
			return;
		TextView tv = (TextView) activity.findViewById(R.id.titleStory);
		tv.setText(StringManipulation.ellipsis(story.getName().toUpperCase(),
				100));

		// while (c.moveToNext()) {
		// ae = new ActivityEvent();
		// ae.setId(c.getInt(c.getColumnIndex("id")));
		// ae.setIcon(c.getString(c.getColumnIndex("icon")));
		// ae.setName(c.getString(c.getColumnIndex("name")));
		// ae.setDescription(c.getString(c.getColumnIndex("description")));
		// ae.setLat(c.getDouble(c.getColumnIndex("lat")));
		// ae.setLng(c.getDouble(c.getColumnIndex("lng")));
		//
		// ae.setStartDate(c.getString(c.getColumnIndex("st_date")));
		// ae.setStartTime(c.getString(c.getColumnIndex("st_time")));
		// ae.setStatus(c.getInt(c.getColumnIndex("status")));
		//
		//
		// list.add(ae);
		// }
	}

	public static void showPopupNewStory(final Activity activity) {
		// launch search dialog
		final Dialog dialog = new Dialog(activity, R.style.dialog_style);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_insert_story);

		dialog.show();

		Button okButton = (Button) dialog.findViewById(R.id.submitStoryButton);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				DBAdapter db = new DBAdapter(activity);
				Story story = Helper.buildObjectStory(new Story(), dialog);
				long sid = db.insertStoryRecord(story);

				if (sid > 0)
					Helper.buildUIMain(activity, story);
				dialog.dismiss();
			}
		});

	}

	// Helper for handle date time picker
	public static void showDatePicker(final Dialog dialog,
			final int resourceIdTarget) {
		DateTime dt = new DateTime();
		DatePickerDialog dp = null;

		dp = new DatePickerDialog(dialog.getContext(), new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				DateTimeFormatter fmt = DateTimeFormat
						.forPattern("MMM dd, yyyy");

				TextView dateText = (TextView) dialog
						.findViewById(resourceIdTarget);
				dateText.setText((new DateTime(year, monthOfYear+1, dayOfMonth,
						0, 0, 0, 0)).toString(fmt));

			}
		}, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		dp.show();

	}

	public static void showTimePicker(final Dialog dialog,
			final int resourceIdTarget) {
		DateTime dt = new DateTime();
		TimePickerDialog tp = null;

		tp = new TimePickerDialog(dialog.getContext(), new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				TextView timeText = (TextView) dialog
						.findViewById(resourceIdTarget);
				timeText.setText(hourOfDay + ":" + minute
						+ TimeUtil.timeZone(hourOfDay));

			}
		}, dt.getHourOfDay(), dt.getMinuteOfHour(), true);
		tp.show();
	}

	public static void buildUIEventBuble(DBAdapter db) {

	}

}
