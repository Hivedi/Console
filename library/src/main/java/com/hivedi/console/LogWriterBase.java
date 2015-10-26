package com.hivedi.console;

public interface LogWriterBase {
	public abstract void saveHandler(String data, int type, Exception e);
}
