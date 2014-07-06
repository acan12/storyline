package app.core.component;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressCustomDialog extends ProgressDialog{
	static ProgressDialog progress ;
	
	

	public static final int LOAD_PROGRESS = 5;
	
	public ProgressCustomDialog(Activity a){
		super(a);
		this.progress.show(a, null, "Loading");
	}
	
	public static ProgressDialog newInstance(Activity a){
		if(progress == null)
			return new ProgressCustomDialog(a);
		else 
			return progress;
	}
	

}
