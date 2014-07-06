package app.core.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import app.core.model.Event;
import app.xzone.storyline.R;


public class ImageGalleryAdapter extends BaseAdapter{
	private Context ctx;
	int imageBackground;
	private Event event;
	private Bitmap[] bmps;
	
	public ImageGalleryAdapter(Context c, Event event){
		this.event = event;
		bmps = ImageAdapter.getPhotosEvent(event);
		
		
		ctx = c;
		TypedArray ta = c.obtainStyledAttributes(R.styleable.gallery1);
		imageBackground = ta.getResourceId(R.styleable.gallery1_android_galleryItemBackground, 1);
		ta.recycle();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bmps.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = new ImageView(ctx);
		iv.setImageBitmap(bmps[position]);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setLayoutParams(new Gallery.LayoutParams(150,120));
		iv.setBackgroundResource(imageBackground);
		return iv;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

}
