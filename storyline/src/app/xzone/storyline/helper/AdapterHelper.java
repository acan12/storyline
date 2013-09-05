package app.xzone.storyline.helper;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import app.xzone.storyline.R;
import app.xzone.storyline.adapter.DBAdapter;
import app.xzone.storyline.adapter.LazyAdapter;
import app.xzone.storyline.model.Story;

public class AdapterHelper {
	private static ListView list;
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
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ImageButton listButton = (ImageButton) a.findViewById(R.id.menuListButton);
				listButton.performClick();
				Helper.buildUIMain(a, (Story) adapter.getItem(position));
				
			}
        	
		});
	}
}
