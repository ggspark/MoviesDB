package com.fastacash.moviesdb.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class QLog {
	public static final int NONE = 0;
	public static final int ERRORS_ONLY = 1;
	public static final int ERRORS_WARNINGS = 2;
	public static final int ERRORS_WARNINGS_INFO = 3;
	public static final int ERRORS_WARNINGS_INFO_DEBUG = 4;
	public static final int ALL = 5;

	/** Not final so it can be set at runtime */
	public static int LOGGING_LEVEL = ALL;

	/*
	 * For filtering app specific output
	 */
	private static final String TAG = "PingPay";
	/*
	 * So any important logs can be outputted in non filtered output also
	 */
	private static final String TAG_GENERAL_OUTPUT = "PingPay QLog";

	static {
		i("Log class reloaded");
	}

	/**
	 * @param obj
	 * @param cause
	 *            The exception which caused this error, may not be null
	 */
	public static void e(final Object obj, final Throwable cause) {
		if (isELoggable()) {
			Log.e(TAG, getTrace() + String.valueOf(obj));
			Log.e(TAG, getThrowableTrace(cause));
		}
	}

	public static void e(final Object obj) {
		if (isELoggable()) {
			Log.e(TAG, getTrace() + String.valueOf(obj));
		}
	}

	public static void w(final Object obj, final Throwable cause) {
		if (isWLoggable()) {
			Log.w(TAG, getTrace() + String.valueOf(obj));
			Log.w(TAG, getThrowableTrace(cause));
		}
	}

	public static void w(final Object obj) {
		if (isWLoggable()) {
			Log.w(TAG, getTrace() + String.valueOf(obj));
		}
	}

	public static void i(final Object obj) {
		if (isILoggable()) {
			Log.i(TAG, getTrace() + String.valueOf(obj));
		}
	}

	public static void d(final Object obj) {
		if (isDLoggable()) {
			Log.d(TAG, getTrace() + String.valueOf(obj));
		}
	}

	public static void v(final Object obj) {
		if (isVLoggable()) {
			Log.v(TAG, getTrace() + String.valueOf(obj));
		}
	}

	public static boolean isVLoggable() {
		return LOGGING_LEVEL > ERRORS_WARNINGS_INFO_DEBUG;
	}

	public static boolean isDLoggable() {
		return LOGGING_LEVEL > ERRORS_WARNINGS_INFO;
	}

	public static boolean isILoggable() {
		return LOGGING_LEVEL > ERRORS_WARNINGS;
	}

	public static boolean isWLoggable() {
		return LOGGING_LEVEL > ERRORS_ONLY;
	}

	public static boolean isELoggable() {
		return LOGGING_LEVEL > NONE;
	}

	private static String getThrowableTrace(final Throwable thr) {
		final StringWriter b = new StringWriter();
		thr.printStackTrace(new PrintWriter(b));
		return b.toString();
	}

	private static String getTrace() {
		final int depth = 2;
		final Throwable t = new Throwable();
		final StackTraceElement[] elements = t.getStackTrace();
		final String callerMethodName = elements[depth].getMethodName();
		final String callerClassPath = elements[depth].getClassName();
		final int lineNo = elements[depth].getLineNumber();
		final int i = callerClassPath.lastIndexOf('.');
		final String callerClassName = callerClassPath.substring(i + 1);
		final String trace = callerClassName + ": " + callerMethodName + "() [" + lineNo + "] - ";
		return trace;
	}

	/**
	 * Prints the stack trace to mubaloo log and standard log
	 * 
	 * @param e
	 */
	public static void handleException(final Exception e) {
		QLog.e(e.toString());
		e.printStackTrace();
	}

	private QLog() {
	}
}