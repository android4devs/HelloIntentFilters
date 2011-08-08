package pl.froger.hello.intentfilters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LinkServer extends Activity {
	private TextView tvLinkServer;
	private Button btnStartNextMatching;
	private Intent incomingIntent;

	String intentData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getDataFromIntent();
		setContentView(R.layout.link_server);
		tvLinkServer = (TextView) findViewById(R.id.tvLinkServer);
		btnStartNextMatching = (Button) findViewById(R.id.btnStartNextMatching);
		btnStartNextMatching.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNextMatchingActivity(incomingIntent);
				finish();
			}
		});
		tvLinkServer.setText("Obs³ugujesz adres: " + intentData);
	}

	private void getDataFromIntent() {
		intentData = "";
		incomingIntent = getIntent();
		if (incomingIntent != null) {
			intentData = incomingIntent.getDataString();
		}
	}
}