package jp.co.iti.mynfcwriter2;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Target URLs
	private static final String TARGET_URL_WEB = "http://bit.ly/1cBnsuk";
	private static final String TARGET_URL_TEL = "tel:09000000000";
	private static final String TARGET_URL_SMS = "sms:09000000000";
	private static final String TARGET_URL_MAIL = "mailto:xxx@xxx.com";
	private static final String TARGET_URL_MAP = "geo:35.6703915,139.7758437";
//	private static final String TARGET_URL_YOUTUBE = "http://www.youtube.com/watch?v=sGF6bOi1NfA";
	private static final String TARGET_URL_YOUTUBE = "vnd.youtube://sGF6bOi1NfA";
//	private static final String TARGET_URL_TWITTER = "https://twitter.com/";
	private static final String TARGET_URL_TWITTER = "Twitter://";
	private static final String TARGET_URL_LINE = "http://line.me/R/msg/text/?somemessage";

	private NfcWriter nfcWriter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.nfcWriter = new NfcWriter(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		PendingIntent pendingIntent = this.createPendingIntent();

		// Enable the NFC adapter
		this.nfcWriter.enable(this, pendingIntent);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Disable the NFC adapter
		this.nfcWriter.disable(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (this.nfcWriter.write(this, intent, TARGET_URL_WEB)) {
			Toast.makeText(this, "Completed.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, this.nfcWriter.getErrorMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		this.nfcWriter = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private PendingIntent createPendingIntent() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
		return PendingIntent.getActivity(this, 0, intent, 0);
	}
}
