package jp.co.iti.mynfcwriter2;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.widget.RadioGroup;
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
	private static final String TARGET_URL_LINE = "http://line.me/R/msg/text/?somemessage";
//	private static final String TARGET_URL_TWITTER = "twitter://";
//	private static final String TARGET_URL_TWITTER = "https://twitter.com/";

	private NfcWriter nfcWriter = null;
	private String targetUrl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.nfcWriter = new NfcWriter(this);
		this.targetUrl = TARGET_URL_WEB;

		// Set up radio group control.
		this.setRadioGroup();
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

		if (this.nfcWriter.write(this, intent, this.targetUrl)) {
			Toast.makeText(this, "Completed.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, this.nfcWriter.getErrorMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		this.nfcWriter = null;
		this.targetUrl = null;
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

	// Set up radio group control.
	private void setRadioGroup() {
		RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupFunction);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Change the target url.
				
				switch (checkedId) {
				case R.id.radioWeb:
					MainActivity.this.targetUrl = TARGET_URL_WEB;
					break;
				case R.id.radioTel:
					MainActivity.this.targetUrl = TARGET_URL_TEL;
					break;
				case R.id.radioSms:
					MainActivity.this.targetUrl = TARGET_URL_SMS;
					break;
				case R.id.radioMail:
					MainActivity.this.targetUrl = TARGET_URL_MAIL;
					break;
				case R.id.radioMap:
					MainActivity.this.targetUrl = TARGET_URL_MAP;
					break;
				case R.id.radioYoutube:
					MainActivity.this.targetUrl = TARGET_URL_YOUTUBE;
					break;
				case R.id.radioLine:
					MainActivity.this.targetUrl = TARGET_URL_LINE;
					break;
//				case R.id.radioTwitter:
//					MainActivity.this.targetUrl = TARGET_URL_TWITTER;
//					break;
				}

				Toast.makeText(MainActivity.this, 
							MainActivity.this.targetUrl, 
							Toast.LENGTH_SHORT).show();
			}
		});
	}
}
