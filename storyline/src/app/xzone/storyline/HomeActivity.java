package app.xzone.storyline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HomeActivity extends Activity implements OnClickListener{
    private ImageButton _addButton;
	private Intent intent;

	/** Called when the activity is first created. Testing */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _addButton = (ImageButton) findViewById(R.id.imgAddButton);
        
        _addButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == _addButton) {
			intent = new Intent(this, FormActivity.class);
		}
		
		startActivity(intent);
	}
}