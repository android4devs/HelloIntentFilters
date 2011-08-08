package pl.froger.hello.intentfilters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser.BookmarkColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BookmarkPicker extends Activity {
	private ListView lvBookmarksList;

	private Intent incomingIntent;
	private String path;
	private Cursor cursor;
	private Uri data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		getDataFromIntent();
		initListView();
	}

	private void getDataFromIntent() {
		incomingIntent = getIntent();
		path = incomingIntent.getDataString();
	}

	private void initListView() {
		SimpleCursorAdapter adapter = getListViewAdapterWithBookmarks();
		lvBookmarksList = (ListView) findViewById(R.id.lvBookmarksList);
		lvBookmarksList.setAdapter(adapter);
		lvBookmarksList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,	long id) {
				setBookmarkResultFrom(pos);
				finish();
			}
		});
	}

	private SimpleCursorAdapter getListViewAdapterWithBookmarks() {
		String[] bookmarks = new String[] { BookmarkColumns.TITLE };
		int[] rows = new int[] { android.R.id.text1 };
		data = Uri.parse(path + "/bookmarks/");
		cursor = managedQuery(data, bookmarks, null, null, null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				cursor, bookmarks, rows);
		return adapter;
	}

	
	private void setBookmarkResultFrom(int position) {
		cursor.moveToPosition(position);
		int rowId = cursor.getInt(cursor.getColumnIndexOrThrow("_ID"));
		Uri uriToOutput = Uri.parse(data.toString() + rowId);
		Intent outputIntent = new Intent();
		outputIntent.setData(uriToOutput);
		setResult(Activity.RESULT_OK, outputIntent);
	}
}