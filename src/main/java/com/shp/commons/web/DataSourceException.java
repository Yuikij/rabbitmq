package com.shp.commons.web;

public class DataSourceException extends Exception {

	private static final long serialVersionUID = 2406961912059973858L;
	
	public static final String UNKNOWN_ERROR_CODE = "-1";
	
	private String errorCode;

	public DataSourceException() {
		super();
	}
	
	public DataSourceException(String errorCode, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.errorCode = errorCode;
	}

	public DataSourceException(String arg0, Throwable arg1) {
		this(UNKNOWN_ERROR_CODE, arg0, arg1);
	}
	
	public DataSourceException(String errorCode, String arg0) {
		super(arg0);
		this.errorCode = errorCode;
	}

	public DataSourceException(String arg0) {
		this(UNKNOWN_ERROR_CODE, arg0);
	}

	public DataSourceException(Throwable arg0) {
		super(arg0);
		if (arg0 instanceof DataSourceException) {
			this.errorCode = ((DataSourceException)arg0).getErrorCode();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

}
