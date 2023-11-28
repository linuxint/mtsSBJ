package com.devkbil.mtssbj.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.devkbil.mtssbj.common.Context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysException extends RuntimeException {
    protected Throwable cause;
    @Getter
    protected String errorCode;
    @Getter
    protected String instanceId;

    public SysException() {
        this("FRS99999", "시스템 장애입니다..");
    }

    public SysException(String message) {
        this("FRS99999", message);
    }

    public SysException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.instanceId = System.getProperty("INSTANCE_ID");
    }

    public SysException(Context ctx, String message) {
        this(ctx, "FRS99999", message);
    }

    public SysException(Context ctx, String errorCode, String message) {
        super(message + (ctx == null ? "" : "(UserInfo:" + ctx + ")"));
        this.errorCode = errorCode;
        this.instanceId = System.getProperty("INSTANCE_ID");
    }

    public SysException(Throwable e) {
        this(null, "FRS99999", null, e);
    }

    public SysException(String errorCode, Throwable e) {
        this(null, errorCode, null, e);
    }

    public SysException(String errorCode, String message, Throwable e) {
        this(null, errorCode, message, e);
    }

    public SysException(Context ctx, Throwable e) {
        this(ctx, "FRS99999", null, e);
    }

    public SysException(Context ctx, String message, Throwable e) {
        this(ctx, "FRS99999", message, e);
    }

    public SysException(Context ctx, String errorCode, String message, Throwable e) {
        super(message + "::" + e.toString() + (ctx == null ? "" : "(UserInfo:" + ctx + ")"));
        this.errorCode = errorCode;
        this.cause = e;
        this.instanceId = System.getProperty("INSTANCE_ID");
    }

    public static String getTraceString(Throwable ex) {
        return getTraceString(ex, 10);
    }

    public static String getTraceString(Throwable ex, int traceDepth) {
        StringBuffer traceStr = new StringBuffer(1024);
        traceStr.append(ex.toString() + " TraceInfo");
        StackTraceElement[] trace = ex.getStackTrace();

        int endIndex = traceDepth;
        if (trace.length < traceDepth) {
            endIndex = trace.length;
        }
        for (int i = 0; i < endIndex; i++) {
            traceStr = traceStr.append("\n\tat " + trace[i]);
        }

        return traceStr.toString().trim();
    }
	
	/*
	public void setErrorCode(String errCode)
	{
		this.errorCode = errCode;
	}
	*/

    public Throwable getCause() {
        return this.cause == this ? null : this.cause;
    }

    public String getMessage() {
        log.debug("getMessage");
        return super.getMessage();
    }

    public String getTrace() {
        if (this.cause != null) {
            return getTraceString(this.cause);
        }
        return getTraceString(this);
    }

    public void printStackTrace(PrintWriter pw) {
        if (this.cause != null) {
            pw.println(getTrace());
        } else {
            super.printStackTrace(pw);
        }
    }

    public void printStackTrace(PrintStream pw) {
        if (this.cause != null) {
            pw.println(getTrace());
        } else {
            super.printStackTrace(pw);
        }
    }
}
