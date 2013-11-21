package app.xzone.storyline;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import app.xzone.storyline.adapter.KiiAdapter;
import app.xzone.storyline.helper.SignFragmentHelper;

public class LoginActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_layout);
		
		KiiAdapter.newInstance();
		
		toSigninForm();

	}

	private void toSigninForm() {

		 FragmentManager manager = getSupportFragmentManager();
		 FragmentTransaction transaction = manager.beginTransaction();
		
		 SignFragmentHelper next = SignFragmentHelper.newInstance();
		 transaction.replace(R.id.main, next);
		
		 transaction.commit();
	}

}
