package app.xzone.storyline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import app.xzone.storyline.adapter.FacebookAdapter;

import com.easy.facebook.android.data.Home;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;
import com.facebook.Session;

public class FacebookLoginActivity extends Activity implements OnClickListener {

	private FBLoginManager fbManager;
	private Button fbButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_facebook);
		
		fbButton = (Button) findViewById(R.id.fb_signin);
		fbButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.fb_signin:
			
			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Authenticate ...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			
			progressDialog.show();
			FacebookAdapter.SigninWithFacebook(this, progressDialog);
			
			break;
		}
			
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		Log.d(this.getClass().toString(), "---- entering facebook activity onactivityresult ----");
	}
	
	
}
