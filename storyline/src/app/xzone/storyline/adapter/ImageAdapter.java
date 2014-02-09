package app.xzone.storyline.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import app.xzone.storyline.R;
import app.xzone.storyline.helper.Helper;

public class ImageAdapter {
	private Context context;
	private static ImageAdapter imageAdapter;
	private static File imageFolder;
	private static String photoDir = "photos";
	private static String facebookDir = "facebook";
	private static DecimalFormat formatter = new DecimalFormat("000");

	private static String rootPath;

	public ImageAdapter(Context context) {
		this.context = context;

	}

	public ImageAdapter getInstance(Context context) {
		if (context == null) {
			imageAdapter = new ImageAdapter(context);
		}
		return imageAdapter;
	}

	public static void takePhoto(Activity a){
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		rootPath = Environment.getExternalStorageDirectory() + File.separator + AppAdapter.getInstance(a).getAppName();
		String photoPath = rootPath + File.separator + photoDir;
		
		// storing file
		int imageNum = 1;
		
		// Create Directory App
		imageFolder = new File(photoPath);
		imageFolder.mkdirs();

		String fileName = "image_" + String.valueOf(formatter.format(imageNum)) + ".jpg";
		File output = new File(imageFolder, fileName);
		while (output.exists()) {
			imageNum++;
			fileName = "image_" + String.valueOf(formatter.format(imageNum)) + ".jpg";
			output = new File(imageFolder, fileName);
		}
		Uri uriSavedImage = Uri.fromFile(output);
		System.out.println("------ uri camera:"+uriSavedImage);
		
		a.startActivityForResult(camera, Helper.REQUEST_CODE_IMAGE_CAMERA);
	}
	
	public static void takePictureGallery(Activity a){

		Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		rootPath = Environment.getExternalStorageDirectory() + "/" + AppAdapter.getInstance(a).getAppName();
		String photoPath = rootPath + File.separator + photoDir;
		
		// storing file
		int imageNum = 1;
		
		// Create Directory App
		imageFolder = new File(photoPath);
		imageFolder.mkdirs();

		String fileName = "image_" + String.valueOf(formatter.format(imageNum)) + ".jpg";
		File output = new File(imageFolder, fileName);
		while (output.exists()) {
			imageNum++;
			fileName = "image_" + String.valueOf(formatter.format(imageNum)) + ".jpg";
			output = new File(imageFolder, fileName);
		}
		
		Uri uriSavedImage = Uri.fromFile(output);
		System.out.println("------ uri gallery:"+uriSavedImage);
		
		a.startActivityForResult(i, Helper.REQUEST_CODE_IMAGE_GALLERY);
	}

	public static String cachedBitmap(Context context, Bitmap bmp) {
		rootPath = Environment.getExternalStorageDirectory() + "/"
				+ AppAdapter.getInstance(context).getAppName();
		String facebookPath = rootPath + facebookDir;

		// cache image into sdcard

		return null;
	}

	public static Bitmap decodeUri(Context c, Uri uri,final int requireSize) throws FileNotFoundException{
		BitmapFactory.Options o =  new BitmapFactory.Options();
		o.inJustDecodeBounds = true;

		BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while(true){
			if(width_tmp / 2 < requireSize || height_tmp /2 < requireSize)
				break;
			width_tmp /=2;
			height_tmp /=2;
			scale *=2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize =scale;
		return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
	}


	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;

	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();

	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);

	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

	    return resizedBitmap;
	}


	public static String getPath(Uri uri, Activity a)
    {    
	    String[] projection={MediaStore.Images.Media.DATA}; 
	    Cursor cursor= a.managedQuery(uri,projection,null,null,null);
	    int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
	    cursor.moveToFirst(); 
	    return cursor.getString(column_index); 
	}  
}
