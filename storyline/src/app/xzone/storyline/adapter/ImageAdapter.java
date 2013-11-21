package app.xzone.storyline.adapter;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import app.xzone.storyline.helper.Helper;

public class ImageAdapter {
	
    public static void takePhoto(Activity a){
    	Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	a.startActivityForResult(camera, Helper.REQUEST_CODE_IMAGE_CAMERA);
    }
    
}
