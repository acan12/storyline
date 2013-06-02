package app.xzone.storyline.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.xzone.storyline.model.BaseModel;
import app.xzone.storyline.model.Event;
import app.xzone.storyline.model.Schedule;




public class ScheduleDBAdapter extends BaseModel {
	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ICON = "icon";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_START_DATE = "start_date";
	public static final String KEY_START_TIME = "start_time";
	public static final String KEY_END_DATE = "end_date";
	public static final String KEY_END_TIME = "end_time";
	public static final String KEY_START_LAT = "start_lat";
	public static final String KEY_START_LNG = "start_lng";
	public static final String KEY_END_LAT = "end_lat";
	public static final String KEY_END_LNG = "end_lng";
	public static final String KEY_STATUS = "status";
	private static final String TAG = "DBAdapter";
	private static final int OFFSET_DAY = 1;

	private Event evt;

	private static final String DATABASE_NAME = "mimodb";
	private static final String DATABASE_TABLE = "activities";
	public static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table schedule (id integer primary key autoincrement, "
			+ "name text not null, "
			+ "icon text not null,"
			+ "description text,"
			+ "start_date text,"
			+ "start_time text,"
			+ "end_date text,"
			+ "end_time text,"
			+ "start_lat double,"
			+ "start_lng double,"
			+ "end_lat double,"
			+ "end_lng double,"
			+ "status integer default 0);";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private Schedule schedule;

	public ScheduleDBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS name");
			onCreate(db);
		}

	}

	public ScheduleDBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long insertRecord(Schedule schedule) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, schedule.getName());
		initialValues.put(KEY_ICON, schedule.getIcon());
		initialValues.put(KEY_DESCRIPTION, schedule.getDescription());
		initialValues.put(KEY_START_DATE, schedule.getStartDate());
		initialValues.put(KEY_END_DATE, schedule.getEndDate());
		initialValues.put(KEY_START_TIME, schedule.getStartTime());
		initialValues.put(KEY_END_TIME, schedule.getEndTime());
		initialValues.put(KEY_START_LAT, schedule.getStartOfLat());
		initialValues.put(KEY_START_LNG, schedule.getStartOfLng());
		initialValues.put(KEY_END_LAT, schedule.getEndOfLat());
		initialValues.put(KEY_END_LNG, schedule.getEndOfLng());
		
		long rows_affected = db.insert(DATABASE_TABLE, null, initialValues);
		this.close();
		return rows_affected;
	}

	public boolean deleteRecord(long rowId) {
		this.open();
		boolean rows_affected = db.delete(DATABASE_TABLE, KEY_ROWID + "="
				+ rowId, null) > 0;
		this.close();
		return rows_affected;
	}

	public List<Schedule> getMostCloselyEvent() throws Exception {
		List<Schedule> list = new ArrayList<Schedule>();
		this.open();
		Cursor c = db.rawQuery(
				"SELECT * FROM schedule where " +
				"(julianday(strftime('%Y-%m-%d', st_date) ) -  julianday(strftime('%Y-%m-%d', 'now'))  )  >= 0 " +
				"order by st_date, st_time limit 1", null);

		while (c.moveToNext()) {
			schedule = new Schedule();
			schedule.setId(c.getInt(c.getColumnIndex("id")));
			schedule.setIcon(c.getString(c.getColumnIndex("icon")));
			schedule.setName(c.getString(c.getColumnIndex("name")));
			schedule.setDescription(c.getString(c.getColumnIndex("description")));
			
			schedule.setStartDate(c.getString(c.getColumnIndex("start_date")));
			schedule.setStartTime(c.getString(c.getColumnIndex("start_time")));
			schedule.setEndDate(c.getString(c.getColumnIndex("end_date")));
			schedule.setEndTime(c.getString(c.getColumnIndex("end_time")));
			
			schedule.setStartOfLat(c.getDouble(c.getColumnIndex("start_lat")));
			schedule.setStartOfLng(c.getDouble(c.getColumnIndex("start_lng")));
			schedule.setEndOfLat(c.getDouble(c.getColumnIndex("end_lat")));
			schedule.setEndOfLng(c.getDouble(c.getColumnIndex("end_lng")));
			
			schedule.setStatus(c.getInt(c.getColumnIndex("status")));

			
			list.add(schedule);
		}
		return list;
	}

	public Cursor getIconsUniqRecord() {
		this.open();
		Cursor c = db
				.rawQuery(
						"select count(*) as count_record, icon " +
						"from schedule group by icon order by st_date, st_time",
						null);
		return c;
	}

	public Cursor getAllRecord() {
		this.open();
		 Cursor c = db.query(DATABASE_TABLE, new String[]{
		 KEY_ROWID,
		 KEY_NAME,
		 KEY_ICON,
		 KEY_DESCRIPTION,
		 KEY_START_DATE,
		 KEY_START_TIME,
		 KEY_END_DATE,
		 KEY_END_TIME,
		 KEY_START_LAT,
		 KEY_START_LNG,
		 KEY_END_LAT,
		 KEY_END_LNG,
		 KEY_STATUS
		 }, null, null, null, null, KEY_START_DATE+","+KEY_START_TIME);
		return c;
	}

	public Cursor getRecord(long rowId) throws SQLException {
		this.open();
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_ICON, KEY_DESCRIPTION, KEY_START_DATE,
				KEY_START_TIME, KEY_END_DATE, KEY_END_TIME, KEY_START_LAT, KEY_START_LNG, KEY_END_LAT, KEY_END_LNG,
				KEY_STATUS }, KEY_ROWID + "=" + rowId, null, null, null, KEY_START_DATE+","+KEY_START_TIME,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		this.close();
		return mCursor;
	}

	public Cursor getRecordByIcon(String iconLabel) throws SQLException {
		this.open();
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_ICON, KEY_DESCRIPTION, KEY_START_DATE,
				KEY_START_TIME, KEY_END_DATE, KEY_END_TIME, KEY_START_LAT, KEY_START_LNG,KEY_END_LAT, KEY_END_LNG,
				KEY_STATUS }, KEY_ICON + "='" + iconLabel + "'", null, null,
				null, KEY_START_DATE+","+KEY_START_TIME, null);
		return mCursor;
	}

	public boolean updateRecord(Schedule schedule) {
		this.open();
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, schedule.getName());
		args.put(KEY_ICON, schedule.getIcon());
		args.put(KEY_DESCRIPTION, schedule.getDescription());
		args.put(KEY_START_DATE, schedule.getStartDate());
		args.put(KEY_END_DATE, schedule.getEndDate());
		args.put(KEY_START_TIME, schedule.getStartTime());
		args.put(KEY_END_TIME, schedule.getEndTime());
		args.put(KEY_START_LAT, schedule.getStartOfLat());
		args.put(KEY_START_LNG, schedule.getStartOfLng());
		args.put(KEY_END_LAT, schedule.getEndOfLat());
		args.put(KEY_END_LNG, schedule.getEndOfLng());
		boolean rows_affected = db.update(DATABASE_TABLE, args, KEY_ROWID + "="
				+ schedule.getId(), null) > 0;
		this.close();
		return rows_affected;
	}

	public boolean updateStatus(int rowid, int status) {
		this.open();
		ContentValues args = new ContentValues();
		args.put(KEY_STATUS, status);
		db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowid, null);
		this.close();
		return true;
	}
}
