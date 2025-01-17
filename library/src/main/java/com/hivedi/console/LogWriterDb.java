package com.hivedi.console;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

public class LogWriterDb implements LogWriterBase {
	private final SQLiteOpenHelper dbx;
	private final Context ctx;
	private static final String DB_FILE_NAME = "log.sqlite";
	public LogWriterDb(Context c) {
		ctx = c;
		dbx = new SQLiteOpenHelper(ctx, DB_FILE_NAME, null, 1){
			@Override
			public void onCreate(SQLiteDatabase d) {
				d.execSQL(
					"CREATE TABLE IF NOT EXISTS log (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"time INTEGER," + 
					"data TEXT," + 
					"type INTEGER" + 
					")"
				);
			}
			@Override
			public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			}
		};
	}
	@Override
	public void saveHandler(String data, int type, Exception e) {
		String dataText = data;
		if (e != null) {
			StringWriter sw = new StringWriter(); 
			PrintWriter pw = new PrintWriter(sw); 
			e.printStackTrace(pw);
			dataText += "\n--- STACK TRACE ---\n" + sw.toString();
		}
		
		ContentValues cv = new ContentValues();
		cv.put("time", Calendar.getInstance().getTimeInMillis());
		cv.put("data", dataText);
		cv.put("type", type);
		dbx.getWritableDatabase().insert("log", null, cv);
	}
	public File getLogDbFile() {
		return ctx.getDatabasePath(DB_FILE_NAME);
	}
}
