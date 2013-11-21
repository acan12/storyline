package app.xzone.storyline.helper;

import java.io.IOException;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.KiiUser.Builder;
import com.kii.cloud.storage.exception.app.AppException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import app.xzone.storyline.R;
import app.xzone.storyline.util.ViewUtil;

public class RegisterDialogHelper extends DialogFragment {

	public static RegisterDialogHelper newInstance() {

		RegisterDialogHelper fragment = new RegisterDialogHelper();

		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View root = inflater.inflate(R.layout.dialog_register_form, null);

		// set button
		Button submitSignup = (Button) root.findViewById(R.id.submitRegister);
		submitSignup.setOnClickListener(new ClickListener(this, root));

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(root);

		return builder.create();

	}

	private static class ClickListener implements View.OnClickListener {

		private RegisterDialogHelper dialog;
		private View root;

		public ClickListener(RegisterDialogHelper dialog, View root) {
			this.dialog = dialog;
			this.root = root;
		}

		@Override
		public void onClick(View v) {
			System.out.println("----- masuk onclick register activity");
			String username = ViewUtil.getValueOfEditText(root,
					R.id.valueUsername);
			String password = ViewUtil.getValueOfEditText(root,
					R.id.valuePassword);
//			String phone = ViewUtil.getValueOfEditText(root, R.id.valuePhone);
//			String country = ViewUtil.getValueOfEditText(root,
//					R.id.valueCountry);

			Builder builder = KiiUser.builderWithName(username);
			builder.withEmail("arycancer@gmail.com");
			builder.withPhone("+6288808666900");
			KiiUser user =  builder.build();
			
			try{
				user.register(password);
				Toast.makeText(v.getContext(), "Register success", 200).show();
			}catch(AppException e){
				Toast.makeText(v.getContext(), "Error AppException", 200).show();
			}catch(IOException e){
				Toast.makeText(v.getContext(), "Error IO Exception", 200).show();
			}
			dialog.dismiss();
			
//			// show progress
//			ProgressDialogHelper progress = ProgressDialogHelper.newInstance(
//					v.getContext(), R.string.register, R.string.register);
//			
//			progress.show(dialog.getFragmentManager(), ProgressDialogHelper.FRAGMENT_TAG);
//			
//			// call user registration API
//			RegisterCallbackHelper callback = new RegisterCallbackHelper(dialog);
//			KiiUser user = KiiUser.createWithUsername(username);
//			user.register(callback, password);
//			System.out.println("------ success register Kii cloud");
			
		}

	}
}
