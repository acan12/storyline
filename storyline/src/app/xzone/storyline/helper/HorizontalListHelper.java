package app.xzone.storyline.helper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import app.xzone.storyline.R;

public class HorizontalListHelper {
	public static void buildHorizontalView(final View vi) {
		HorizontalScrollView scrollView = (HorizontalScrollView) vi.findViewById(R.id.scrollView);
		LinearLayout bubbleImage = (LinearLayout) vi.findViewById(R.id.bubble_image_header);
		
		
//		for (int i = 0; i < 3; i++) {
//			final ImageView imagePhoto = new ImageView(vi.getContext());
//			imagePhoto.setTag(i);
//			imagePhoto.setImageResource(R.drawable.demo_pic);
//			imagePhoto.setPadding(3, 3, 3, 3);
//
//			bubbleImage.addView(imagePhoto);
//			imagePhoto.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Toast.makeText(vi.getContext(), "Tag: "+imagePhoto.getTag(), 100).show();
//					Log.e("Tag", "" + imagePhoto.getTag());
//				}
//			});
//		}
		
		
	}
}

// package app.xzone.storyline.helper;
//
// import android.app.Activity;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.BaseAdapter;
// import android.widget.HorizontalScrollView;
// import android.widget.ImageButton;
// import android.widget.TextView;
// import app.xzone.storyline.R;
// import app.xzone.storyline.ui.HorizontalListView;
//
// public class HorizontalListHelper {
// public static void buildHorizontalView(Activity a){
// HorizontalListView listView = (HorizontalListView)
// a.findViewById(R.id.scrollView);
// listView.setAdapter(madapter);
// }
//
//
// private BaseAdapter mAdapter = new BaseAdapter() {
//
// @Override
// public int getCount() {
// return dataObjects.length;
// }
//
// @Override
// public Object getItem(int position) {
// return null;
// }
//
// @Override
// public long getItemId(int position) {
// return 0;
// }
//
// @Override
// public View getView(int position, View convertView, ViewGroup parent) {
// View retval =
// LayoutInflater.from(parent.getContext()).inflate(R.layout.hscroll_image_item,
// null);
// ImageButton imgbutton = (ImageButton) retval.findViewById(R.id.photo_image);
//
//
// return retval;
// }
//
//
// };
// }
