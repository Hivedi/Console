package com.hivedi.console;

import android.util.Log;

public class LogWriterLogCat implements LogWriterBase {
	@Override
	public void saveHandler(String data, int type, Exception e) {
		final String TAG = Console.getTag();
		switch(type) {
			default:
			case 0: Log.v(TAG, data); break;
			case 1: Log.e(TAG, data); break;
			case 2: Log.d(TAG, data); break;
			case 3: Log.w(TAG, data); break;
			case 4: Log.i(TAG, data); break;
		}
		if (e != null) 
			e.printStackTrace();
	}
}
