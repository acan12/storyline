package app.xzone.storyline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import app.core.adapter.FacebookAdapter;

import com.facebook.Session;

public class FacebookLoginActivity extends Activity implements OnClickListener {

	private Button fbButton;
	private ProgressDialog progress;
	private ProgressBar progressSignin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_facebook);

		fbButton = (Button) findViewById(R.id.fblogin);
		fbButton.setOnClickListener(this);
		
		progressSignin = (ProgressBar) findViewById(R.id.progressSignin);
		progressSignin.setVisibility(View.GONE);
		
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fblogin:
			
			progressSignin.setVisibility(View.VISIBLE);
			Session session = FacebookAdapter.openConnection(this);
			
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

}
