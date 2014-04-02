package app.xzone.storyline;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import app.xzone.storyline.adapter.ImageAdapter;
import app.xzone.storyline.adapter.ImageGalleryAdapter;
import app.xzone.storyline.model.Event;

public class GalleryPhotosActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_photos_layout);
		
		Bundle extras = getIntent().getExtras();
		final Event event = (Event) extras.get("app.event.photos");
		
		Gallery ga = (Gallery) findViewById(R.id.gallery01);
		ga.setAdapter(new ImageGalleryAdapter(this, event));
		
		ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap[] pics = ImageAdapter.getPhotosEvent(event);
				Toast.makeText(getBaseContext(), "You have selected picture " + (arg2+1) + " of '"+event.getStory().getName()+"'", 
				        Toast.LENGTH_SHORT).show();
				ImageView imageView = (ImageView) findViewById(R.id.photos_event);
				imageView.setImageBitmap(pics[arg2]);
			}
		});
	}
	
	
	
}
