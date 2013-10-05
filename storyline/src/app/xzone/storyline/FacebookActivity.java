package app.xzone.storyline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.easy.facebook.android.data.Home;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

public class FacebookActivity extends Activity implements LoginListener, OnClickListener {

	private FBLoginManager fbManager;
	private Button fbButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_facebook);
		
		fbButton = (Button) findViewById(R.id.fb_signin);
		fbButton.setOnClickListener(this);
		
		
		fbButton.performClick();
//		shareFacebook();
	}

	private void shareFacebook() {
		// change the permission according to the function you want to use
		String permissions[] = { "read_stream", "user_relationship_detail",
				"user_religion_politics", "user_work_history",
				"user_relationships", "user_interests", "user_likes",
				"user_location", "user_hometown", "user_education_history",
				"user_activities", "offline_access" };
		
		// change the paremeters with those of your application
//		fbManager = new FBLoginManager(this, R.layout.black, "FacebookApplicationID", permissions);
		
		if(fbManager.existsSavedFacebook()){
			fbManager.loadFacebook();
		}else{
			fbManager.login();
		}

	}

	@Override
	public void loginSuccess(Facebook facebook) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logoutSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loginFail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.fb_signin:
			
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			break;
		}
			
	}

}
