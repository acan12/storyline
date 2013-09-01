package app.xzone.storyline.helper;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.R;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.model.Story;
import app.xzone.storyline.util.StringManipulation;

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

	public static void buildUIEventBuble(DBAdapter db) {

	}
	
	public static void showPopupNewStory(final Activity activity){
		// launch search dialog
		final Dialog dialog = new Dialog(activity,
				R.style.dialog_style);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_insert_story);

		dialog.show();

		Button okButton = (Button) dialog
				.findViewById(R.id.submitStoryButton);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				DBAdapter db = new DBAdapter(activity);
				Story story = Helper.buildObjectStory(
						new Story(), dialog);
				long sid = db.insertStoryRecord(story);
				
				if(sid > 0) Helper.buildUIMain(activity, story);
				dialog.dismiss();
			}
		});

	}

}
