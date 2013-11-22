package app.xzone.storyline.helper;

import java.io.IOException;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.exception.app.AppException;
import com.kii.cloud.storage.exception.app.BadRequestException;
import com.kii.cloud.storage.exception.app.ConflictException;
import com.kii.cloud.storage.exception.app.ForbiddenException;
import com.kii.cloud.storage.exception.app.NotFoundException;
import com.kii.cloud.storage.exception.app.UnauthorizedException;
import com.kii.cloud.storage.exception.app.UndefinedException;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.R;
import app.xzone.storyline.util.ViewUtil;

public class SignFragmentHelper extends Fragment implements OnClickListener {
	
	private View root;
	
	public static SignFragmentHelper newInstance(){
		return new SignFragmentHelper();
	}

	/*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	root = inflater.inflate(R.layout.signin_form, container, false);
    	
    	
    	root.findViewById(R.id.registerButton).setOnClickListener(this);;
    	root.findViewById(R.id.submitSignin).setOnClickListener(this);;
        
        
    	
		return root;
    	
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.submitSignin:
			EditText edit = (EditText) root.findViewById(R.id.valueUsernamex);
			doSignin(root);
			break;
			
		case R.id.registerButton:
			showDialogRegister(this);
			break;
		}
		
	}

	private void doSignin(View v) {
		String username = ViewUtil.getValueOfEditText(v, R.id.valueUsernamex);
		String password = ViewUtil.getValueOfEditText(v, R.id.valuePasswordx);
		
		
		if(!KiiUser.isValidUserName(username)){
			AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
			alert.setMessage("Invalid Username!!");
			alert.show();
		}
		
		if(!KiiUser.isValidPassword(password)){
			AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
			alert.setMessage("Invalid Password!!");
			alert.show();
		}
		
		try {
//			KiiUser user = KiiUser.logIn(username, password);
			
			onLoginFinished();
			
			
			
//		} catch (IOException e) {
//			AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
//			alert.setMessage("Invalid Sigin!!");
//			alert.show();
		} catch (Exception e) {
			AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
			alert.setMessage("Invalid Sigin!!");
			alert.show();
		}
		
		
		
		
	}

	private void showDialogRegister(SignFragmentHelper fragment) {
	
		RegisterDialogHelper dialog = RegisterDialogHelper.newInstance();
		dialog.setTargetFragment(fragment, 0);
		dialog.show(fragment.getFragmentManager(), "");
	}
	
	
	/**
     * This method is called when user registration is finished. 
     * See {@link RegisterCallback#onRegisterCompleted(int, KiiUser, Exception)}
     */
    void onLoginFinished() {
    	
        FragmentActivity activity = getActivity();
        if (activity == null) { return; }
        ViewUtil.showToast(activity, "Register success");

        Intent intent = new Intent(activity, HomeActivity.class);
		activity.startActivity(intent);
    }
    
	
}
