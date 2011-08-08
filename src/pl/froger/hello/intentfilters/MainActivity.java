package pl.froger.hello.intentfilters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser.BookmarkColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final int BOOKMARK_PICK = 1234;

	private EditText etLink;
	private Button btnServeSpecifyLink;
	private Button btnPickBookmark;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etLink = (EditText) findViewById(R.id.etLink);
		btnServeSpecifyLink = (Button) findViewById(R.id.btnServeSpecifyLink);
		btnPickBookmark = (Button) findViewById(R.id.btnPickBookmark);
		initButtonsOnClick();
	}

	private void initButtonsOnClick() {
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btnServeSpecifyLink:
					serveLinkFromEditText();
					break;
				case R.id.btnPickBookmark:
					pickBookmark();
					break;
				default:
					break;
				}
			}
		};
		btnServeSpecifyLink.setOnClickListener(listener);
		btnPickBookmark.setOnClickListener(listener);
	}

	private void serveLinkFromEditText() {
		Uri uri = Uri.parse(etLink.getText().toString());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	private void pickBookmark() {
		Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://browser"));
		startActivityForResult(intent, BOOKMARK_PICK);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case BOOKMARK_PICK:
			if (resultCode == Activity.RESULT_OK) {
				Uri bookmarkUri = data.getData();
				String[] bookmarks = new String[] { BookmarkColumns.URL };
				Cursor cursor = managedQuery(
						bookmarkUri, 
						bookmarks,
						null,
						null, 
						null);
				cursor.moveToFirst();
				String url = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkColumns.URL));
				etLink.setText(url);
			}
			break;

		default:
			break;
		}
	}
}