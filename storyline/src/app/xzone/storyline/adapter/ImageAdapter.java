package app.xzone.storyline.adapter;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.http.conn.ManagedClientConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import app.xzone.storyline.helper.Helper;

public class ImageAdapter {
	private Context context;
	private static ImageAdapter imageAdapter;
	private static File imageFolder;
	private static String photoDir = "photos";
	private static DecimalFormat formatter = new DecimalFormat("000");

	private static String rootPath;
	
	public ImageAdapter(Context context){
		this.context = context;
		
	}
	
	public ImageAdapter getInstance(Context context){
		if(context == null){
			imageAdapter = new ImageAdapter(context);
		}
		return imageAdapter;
	}

	public static void takePhoto(Activity a) {
		Intent camera = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		rootPath = Environment.getExternalStorageDirectory() + File.separator
				+ AppAdapter.getInstance(a).getAppName();
		String photoPath = rootPath + File.separator +photoDir;
		// storing file
		int imageNum = 1;
		// Create Directory App
		imageFolder = new File(photoPath);
		imageFolder.mkdirs();

		String fileName = "image_" + String.valueOf(formatter.format(imageNum))
				+ ".jpg";
		File output = new File(imageFolder, fileName);
		while (output.exists()) {
			imageNum++;
			fileName = "image_" + String.valueOf(formatter.format(imageNum))
					+ ".jpg";
			output = new File(imageFolder, fileName);
		}
		Uri uriSavedImage = Uri.fromFile(output);
		 
//		Bitmap bmp = BitmapFactory.decodeFile(output.getPath());
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);


		a.startActivityForResult(camera, Helper.REQUEST_CODE_IMAGE_CAMERA);
	}

	public static void takePictureFile(Activity a) {
		Intent gallery = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		rootPath = Environment.getExternalStorageDirectory() + "/"
				+ AppAdapter.getInstance(a).getAppName();
		String photoPath = rootPath + photoDir;
		// storing file
		int imageNum = 1;
		// Create Directory App
		imageFolder = new File(photoPath);
		imageFolder.mkdirs();

		String fileName = "image_" + String.valueOf(formatter.format(imageNum))
				+ ".jpg";
		File output = new File(imageFolder, fileName);
		while (output.exists()) {
			imageNum++;
			fileName = "image_" + String.valueOf(formatter.format(imageNum))
					+ ".jpg";
			output = new File(imageFolder, fileName);
		}
		Uri uriSavedImage = Uri.fromFile(output);
		gallery.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

		a.startActivityForResult(gallery, Helper.REQUEST_CODE_IMAGE_GALLERY);
	}

}
