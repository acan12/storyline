package app.xzone.storyline.adapter;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.xzone.storyline.model.Event;
import app.xzone.storyline.model.Story;

public class DBAdapter extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "tracodb";
	private static final String DATABASE_TABLE_STORY = "stories";
	private static final String DATABASE_TABLE_EVENT = "events";
	public static final int DATABASE_VERSION = 1;

	// private DatabaseHelper dbHelper;
	private SQLiteDatabase sqliteDB;

	// final variable field story table
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_MESSAGE = "message";
	private static final String FIELD_CATEGORY = "category";
	private static final String FIELD_TRANSPORT = "transportation";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_SHARED = "shared";
	private static final String FIELD_LOCNAME = "locname";
	private static final String FIELD_LAT = "lat";
	private static final String FIELD_LNG = "lng";
	private static final String FIELD_PHOTOS = "photos";

	private static final String FIELD_START_DATE = "start_date";
	private static final String FIELD_START_TIME = "start_time";
	private static final String FIELD_END_DATE = "end_date";
	private static final String FIELD_END_TIME = "end_time";

	// Foreign Key fields
	private static final String FIELD_FR_EVENT_STORY = "event_story";

	// variable assign sql command
	private static final String TABLE_STORY_CREATE = "create table "
			+ DATABASE_TABLE_STORY + "(id integer primary key autoincrement, "
			+ FIELD_NAME + " text not null, " + FIELD_DESCRIPTION
			+ " text not null, " + FIELD_STATUS + " integer default 0, "
			+ FIELD_SHARED + " integer default 0, " + FIELD_CATEGORY
			+ " integer, " + FIELD_START_DATE + " integer, " + FIELD_START_TIME
			+ " text, " + FIELD_END_DATE + " integer, " + FIELD_END_TIME
			+ " text); ";

	private static final String TABLE_EVENT_CREATE = "create table "
			+ DATABASE_TABLE_EVENT + "(id integer primary key autoincrement, "
			+ FIELD_NAME + " text not null, " + FIELD_MESSAGE
			+ " text not null, " + FIELD_TRANSPORT + " text, " + FIELD_STATUS
			+ " integer default 0, " + FIELD_SHARED + " integer default 0, "
			+ FIELD_LOCNAME + " text, " + FIELD_PHOTOS + " text not null, " + FIELD_LAT + " double, " + FIELD_LNG
			+ " double, " + FIELD_START_DATE + " integer, " + FIELD_START_TIME
			+ " text," + FIELD_FR_EVENT_STORY + " integer," + "FOREIGN KEY("
			+ FIELD_FR_EVENT_STORY + ") REFERENCES story(id) ) ;";

	public DBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_STORY_CREATE);
		db.execSQL(TABLE_EVENT_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS name");
		onCreate(db);
	}

	public void open() throws SQLException {
		sqliteDB = this.getWritableDatabase();
	}

	public long insertStoryRecord(Story story) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_NAME, story.getName());
		initialValues.put(FIELD_DESCRIPTION, story.getDescription());
		initialValues.put(FIELD_STATUS, story.getStatus());
		initialValues.put(FIELD_SHARED, story.getShared());
		initialValues.put(FIELD_CATEGORY, story.getCategory());
		initialValues.put(FIELD_START_DATE, story.getStartDate());
		initialValues.put(FIELD_START_TIME, story.getStartTime());
		initialValues.put(FIELD_END_DATE, story.getEndDate());
		initialValues.put(FIELD_END_TIME, story.getEndTime());

		long rowid = sqliteDB.insert(DATABASE_TABLE_STORY, null, initialValues);
		this.close();

		return rowid;
	}

	public long insertEventRecord(Event event, int storyId) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_NAME, event.getName());
		initialValues.put(FIELD_MESSAGE, event.getMessage());
		initialValues.put(FIELD_TRANSPORT, event.getTransportation());
		initialValues.put(FIELD_STATUS, event.getStatus());
		initialValues.put(FIELD_SHARED, event.getShared());
		initialValues.put(FIELD_LOCNAME, event.getLocname());
		initialValues.put(FIELD_PHOTOS, event.getPhotos());
		initialValues.put(FIELD_LAT, event.getLat());
		initialValues.put(FIELD_LNG, event.getLng());
		initialValues.put(FIELD_START_DATE, event.getStartDate());
		initialValues.put(FIELD_FR_EVENT_STORY, Integer.valueOf(storyId));

		long rowid = sqliteDB.insert(DATABASE_TABLE_EVENT, null, initialValues);
		this.close();

		return rowid;
	}

	public boolean deleteStoryRecord(long rowId) {
		this.open();
		boolean rows = sqliteDB.delete(DATABASE_TABLE_STORY, FIELD_ID + "="
				+ rowId, null) > 0;
		this.close();

		return rows;
	}

	public boolean deleteEventRecord(long rowId) {
		this.open();
		boolean rows = sqliteDB.delete(DATABASE_TABLE_EVENT, FIELD_ID + "="
				+ rowId, null) > 0;
		this.close();

		return rows;
	}

	public ArrayList<Story> getAllStory() {
		ArrayList<Story> items = new ArrayList<Story>();
		Story story;

		this.open();
		String query = "SELECT * FROM stories ORDER BY id desc";

		Cursor c = sqliteDB.rawQuery(query, null);
		
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			story = getStoryRecord(id);

			items.add(story);
		}

		this.close();
		return items;
	}

	public Story getLastStory() throws SQLException {
		this.open();
		String query = "SELECT id FROM stories ORDER BY id desc LIMIT 1";
		Cursor c = sqliteDB.rawQuery(query, null);

		if (c != null && c.moveToLast()) {
			return getStoryRecord(c.getLong(0));
		}
		
		this.close();
		
		return null;
	}

	public Story getStoryRecord(long rowId) throws SQLException {
		this.open();
		Cursor mCursor = sqliteDB.query(true, DATABASE_TABLE_STORY,
				new String[] { FIELD_ID, FIELD_NAME, FIELD_DESCRIPTION,
						FIELD_STATUS, FIELD_SHARED, FIELD_CATEGORY,
						FIELD_START_DATE, FIELD_START_TIME, FIELD_END_DATE,
						FIELD_END_TIME }, FIELD_ID + "=" + rowId, null, null,
				null, null, null);

		Story story = new Story();

		if (mCursor.moveToFirst()) {
			mCursor.moveToFirst();
			story.setId(Integer.parseInt(mCursor.getString(0)));
			story.setName(mCursor.getString(1));
			story.setDescription(mCursor.getString(2));
			story.setStatus(Integer.parseInt(mCursor.getString(3)));
			story.setShared(Integer.parseInt(mCursor.getString(4)));
			story.setCategory(mCursor.getString(5));
			story.setStartDate(Long.parseLong(mCursor.getString(6)));
			story.setStartTime(mCursor.getString(7));
			story.setEndDate(Long.parseLong(mCursor.getString(8)));
			story.setEndTime(mCursor.getString(9));

			String q = "SELECT * FROM events where event_story = "
					+ story.getId() + " ORDER BY start_date";

			Cursor c = sqliteDB.rawQuery(q, null);

			while (c.moveToNext()) {
				Event event = new Event();
				event.setId(c.getInt(c.getColumnIndex("id")));
				event.setName(c.getString(c.getColumnIndex("name")));
				event.setMessage(c.getString(c.getColumnIndex("message")));
				// event.setCategory(c.getString(c.getColumnIndex("category")));
				event.setTransportation(c.getString(c
						.getColumnIndex("transportation")));
				event.setStatus(c.getInt(c.getColumnIndex("status")));
				event.setShared(c.getInt(c.getColumnIndex("shared")));
				event.setLocname(c.getString(c.getColumnIndex("locname")));
				event.setPhotos(c.getString(c.getColumnIndex("photos")));
				event.setLat(c.getLong(c.getColumnIndex("lat")));
				event.setLng(c.getLong(c.getColumnIndex("lng")));
				event.setStartDate(c.getLong(c.getColumnIndex("start_date")));

				// set event relation to story
				event.setStory(story);

				// set story relation to event
				story.addToEvents(event);
			}

		} else {
			story = null;
		}
		this.close();
		return story;
	}

	public Cursor getEventRecord(long rowId) throws SQLException {
		this.open();
		Cursor mCursor = sqliteDB.query(true, DATABASE_TABLE_EVENT,
				new String[] { FIELD_ID, FIELD_NAME, FIELD_MESSAGE,
						FIELD_TRANSPORT, FIELD_STATUS, FIELD_SHARED,
						FIELD_LOCNAME, FIELD_PHOTOS, FIELD_LAT, FIELD_LNG, FIELD_START_DATE,
						FIELD_START_TIME, FIELD_END_DATE, FIELD_END_TIME,
						FIELD_FR_EVENT_STORY }, FIELD_ID + "=" + rowId, null,
				null, null, null, null);

		this.close();
		return mCursor;
	}

	public boolean updateStoryRecord(Story story) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_NAME, story.getName());
		initialValues.put(FIELD_DESCRIPTION, story.getDescription());
		initialValues.put(FIELD_STATUS, story.getStatus());
		initialValues.put(FIELD_SHARED, story.getShared());
		initialValues.put(FIELD_CATEGORY, story.getCategory());
		initialValues.put(FIELD_START_DATE, story.getStartDate());
		initialValues.put(FIELD_START_TIME, story.getStartTime());
		initialValues.put(FIELD_END_DATE, story.getEndDate());
		initialValues.put(FIELD_END_TIME, story.getEndTime());

		boolean rows = sqliteDB.update(DATABASE_TABLE_STORY, initialValues,
				FIELD_ID + "=" + story.getId(), null) > 0;
		this.close();

		return rows;
	}

	public boolean updateEventRecord(Event event) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_NAME, event.getName());
		initialValues.put(FIELD_MESSAGE, event.getMessage());
//		initialValues.put(FIELD_CATEGORY, event.getCategory());
		initialValues.put(FIELD_TRANSPORT, event.getTransportation());
		initialValues.put(FIELD_STATUS, event.getStatus());
		initialValues.put(FIELD_SHARED, event.getShared());
		initialValues.put(FIELD_LOCNAME, event.getLocname());
		initialValues.put(FIELD_PHOTOS, event.getPhotos());
		initialValues.put(FIELD_LAT, event.getShared());
		initialValues.put(FIELD_LNG, event.getShared());
		initialValues.put(FIELD_START_DATE, event.getStartDate());
		// initialValues.put(FIELD_START_TIME, event.getStartTime());
		// initialValues.put(FIELD_FR_EVENT_STORY, storyid);

		boolean rows = sqliteDB.update(DATABASE_TABLE_EVENT, initialValues,
				FIELD_ID + "=" + event.getId(), null) > 0;
		this.close();

		return rows;
	}
	
	public boolean savePhoto(Event event){
		this.open();
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_PHOTOS, event.getPhotos());
		
		boolean rows = sqliteDB.update(DATABASE_TABLE_EVENT, initialValues,
				FIELD_ID + "=" + event.getId(), null) > 0;
		this.close();
		return rows;
	}

}
