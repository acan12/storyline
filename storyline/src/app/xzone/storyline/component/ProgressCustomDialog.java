package app.xzone.storyline.component;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressCustomDialog {
	ProgressDialog progress ;
	
	

	public static final int LOAD_PROGRESS = 5;
	
	public ProgressCustomDialog(Activity a){
		this.progress = ProgressDialog.show(a, null, "Loading");
	}
	
	public static ProgressCustomDialog newInstance(Activity a){
		return new ProgressCustomDialog(a);
	}
	
	
	public void close(){
		if(this.progress != null){
			this.progress.dismiss();
		}
	}
	

}
