package com.smartron.dialer.beans;

import java.util.Date;

public class CallLogBeans {

	private String callername, callDuration, callType,callerContactId,callerNumber;
	private int callmonth,callYear;
	public int getCallmonth() {
		return callmonth;
	}

	public void setCallmonth(int callmonth) {
		this.callmonth = callmonth;
	}

	public int getCallYear() {
		return callYear;
	}

	public void setCallYear(int callYear) {
		this.callYear = callYear;
	}

	public String getCallerNumber() {
		return callerNumber;
	}

	public void setCallerNumber(String callerNumber) {
		this.callerNumber = callerNumber;
	}

	public String getCallerContactId() {
		return callerContactId;
	}

	public void setCallerContactId(String callerContactId) {
		this.callerContactId = callerContactId;
	}

	private Date calldate;

	public String getCallername() {
		return callername;
	}

	public void setCallername(String callername) {
		this.callername = callername;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Date getCalldate() {
		return calldate;
	}

	public void setCalldate(Date calldate) {
		this.calldate = calldate;
	}

}
