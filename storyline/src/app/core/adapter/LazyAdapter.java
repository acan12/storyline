package app.core.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.core.model.Story;
import app.ui.helper.StoryHelper;
import app.util.TimeUtil;
import app.xzone.storyline.R;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Story> data;
	private LayoutInflater inflater=null;

	public LazyAdapter(Activity a, ArrayList<Story> d){
		this.activity = a;
		this.data=d;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        LinearLayout thumbnail =(LinearLayout)vi.findViewById(R.id.thumbnail); // thumb image
 
        
        Story s = data.get(position);
 
        // Setting all values in listview
        title.setText(s.getName());
        artist.setText(s.getDescription());
        duration.setText( TimeUtil.dateFormat(TimeUtil.fromEpochFormat(s.getStartDate()), "MMM d, yyyy") );
        StoryHelper.setImageStoryType(thumbnail, s.getCategory());

        return vi;
	}

}
