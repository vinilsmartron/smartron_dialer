package com.smartron.dialer.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.smartron.dialer.R;
import com.smartron.dialer.adapter.CallLogAdapter;
import com.smartron.dialer.beans.CallLogBeans;

/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014. Website:
 * http://alamkanak.github.io/
 */
public class DialerActivity extends ActionBarActivity implements
		WeekView.MonthChangeListener, WeekView.EventClickListener,
		WeekView.EventLongPressListener {
	ArrayList<CallLogBeans> mCallLogList;
	private ListView mCallLogListView;
	private CallLogAdapter mCallLogAdapter;
	private static final int TYPE_DAY_VIEW = 1;
	private static final int TYPE_THREE_DAY_VIEW = 2;
	private static final int TYPE_WEEK_VIEW = 3;
	private int mWeekViewType = TYPE_THREE_DAY_VIEW;
	private WeekView mWeekView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialer);
		mCallLogList = new ArrayList<CallLogBeans>();
		mCallLogListView = (ListView) findViewById(R.id.call_log_list);
		Cursor managedCursor = getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		while (managedCursor.moveToNext()) {
			// String id_contact = managedCursor.getString(managedCursor
			// .getColumnIndex(ContactsContract.Contacts._ID));
			// String name_contact = managedCursor.getString(managedCursor
			// .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String phNum = managedCursor.getString(number);
			String callTypeCode = managedCursor.getString(type);
			String strcallDate = managedCursor.getString(date);
			Date callDate = new Date(Long.valueOf(strcallDate));
			String callDuration = managedCursor.getString(duration);
			String callType = null;
			int callcode = Integer.parseInt(callTypeCode);
			switch (callcode) {
			case CallLog.Calls.OUTGOING_TYPE:
				callType = "Outgoing";
				break;
			case CallLog.Calls.INCOMING_TYPE:
				callType = "Incoming";
				break;
			case CallLog.Calls.MISSED_TYPE:
				callType = "Missed";
				break;
			}
			CallLogBeans clb = new CallLogBeans();
			clb.setCallerContactId("0");
			clb.setCallerNumber(phNum);
			clb.setCallername("Vinil");
			Calendar c = Calendar.getInstance();
			c.setTime(callDate);
			clb.setCallmonth(c.get(Calendar.MONTH));
			clb.setCallYear(c.get(Calendar.YEAR));
			clb.setCalldate(callDate);
			clb.setCallDuration(callDuration);
			clb.setCallType(callType);
			mCallLogList.add(clb);
		}
		managedCursor.close();
		ArrayList<CallLogBeans> logB = new ArrayList<CallLogBeans>();
		for (int i = 0; i < 30; i++) {
			logB.add(mCallLogList.get(i));

		}
		mCallLogAdapter = new CallLogAdapter(this, logB);
		mCallLogListView.setAdapter(mCallLogAdapter);
		View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.footer_list_row, null, false);
		TextView mMoreTextView = (TextView) footerView
				.findViewById(R.id.footer_1);

		mCallLogListView.addFooterView(footerView);
		// Get a reference for the week view in the layout.
		mWeekView = (WeekView) findViewById(R.id.weekView);

		// Show a toast message about the touched event.
		mWeekView.setOnEventClickListener(this);

		// The week view has infinite scrolling horizontally. We have to provide
		// the events of a
		// month every time the month changes on the week view.
		mWeekView.setMonthChangeListener(this);

		// Set long press listener for events.
		mWeekView.setEventLongPressListener(this);

		// Set up a date time interpreter to interpret how the date and time
		// will be formatted in
		// the week view. This is optional.

		mMoreTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mWeekView.setVisibility(View.VISIBLE);
				mCallLogListView.setVisibility(View.GONE);
				setupDateTimeInterpreter(false);

			}
		});
	}

	/**
	 * Set up a date time interpreter which will show short date values when in
	 * week view and long date values otherwise.
	 * 
	 * @param shortDate
	 *            True if the date values should be short.
	 */
	private void setupDateTimeInterpreter(final boolean shortDate) {
		mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
			@Override
			public String interpretDate(Calendar date) {
				SimpleDateFormat weekdayNameFormat = new SimpleDateFormat(
						"EEE", Locale.getDefault());
				String weekday = weekdayNameFormat.format(date.getTime());
				SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale
						.getDefault());

				// All android api level do not have a standard way of getting
				// the first letter of
				// the week day name. Hence we get the first char
				// programmatically.
				// Details:
				// http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
				if (shortDate)
					weekday = String.valueOf(weekday.charAt(0));
				return weekday.toUpperCase() + format.format(date.getTime());
			}

			@Override
			public String interpretTime(int hour) {
				return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM"
						: hour + " AM");
			}
		});
	}

	@Override
	public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

		// Populate the week view with some events.
		List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

		WeekViewEvent event;
		for (int i = 0; i < mCallLogList.size(); i++) {
			Calendar startTime = Calendar.getInstance();
			Calendar endTime = (Calendar) startTime.clone();
			//
			if (newYear == mCallLogList.get(i).getCallYear()
					&& (mCallLogList.get(i).getCallmonth() == newMonth - 1)) {
				addEventToCalendar(newYear, newMonth, events, i, startTime);
				// startTime = Calendar.getInstance();
				// startTime.set(Calendar.HOUR_OF_DAY, 3);
				// startTime.set(Calendar.MINUTE, 30);
				// startTime.set(Calendar.MONTH, newMonth - 1);
				// startTime.set(Calendar.YEAR, newYear);
				// startTime.set(Calendar.DAY_OF_MONTH, );
				// endTime = (Calendar) startTime.clone();
				// endTime.set(Calendar.HOUR_OF_DAY, 4);
				// endTime.set(Calendar.MINUTE, 30);
				// endTime.set(Calendar.MONTH, newMonth - 1);
				// event = new WeekViewEvent(10, getEventTitle(startTime),
				// startTime, endTime);
				// event.setColor(getResources().getColor(R.color.event_color_02));
				// events.add(event);
			}

		}

		return events;
	}

	private void addEventToCalendar(int newYear, int newMonth,
			List<WeekViewEvent> events, int i, Calendar startTime) {
		WeekViewEvent event;
		Calendar endTime = (Calendar) startTime.clone();
		// startTime.setTimeInMillis(startTime.getTimeInMillis());
		startTime.set(Calendar.DAY_OF_MONTH, mCallLogList.get(i).getCalldate()
				.getDay());
		startTime.set(Calendar.HOUR_OF_DAY, mCallLogList.get(i).getCalldate()
				.getHours());
		startTime.set(Calendar.MINUTE, mCallLogList.get(i).getCalldate()
				.getMinutes());
		startTime.set(Calendar.SECOND, mCallLogList.get(i).getCalldate()
				.getSeconds());
		startTime.set(Calendar.MONTH, newMonth - 1);
		startTime.set(Calendar.YEAR, newYear);
		Date endDate = new Date(
				(mCallLogList.get(i).getCalldate().getTime() + Integer
						.parseInt(mCallLogList.get(i).getCallDuration())) * 1000);
		endTime.set(Calendar.DAY_OF_MONTH, endDate.getDay());
		endTime.set(Calendar.HOUR_OF_DAY, endDate.getHours());
		endTime.set(Calendar.MINUTE, endDate.getMinutes());
		endTime.set(Calendar.SECOND, endDate.getSeconds());
		endTime.set(Calendar.MONTH, newMonth - 1);
		endTime.set(Calendar.YEAR, newYear);
		// endTime.set(Calendar.DAY_OF_MONTH, mCallLogList.get(i)
		// .getCalldate().getDay());
		endTime = (Calendar) startTime.clone();
		endTime.set(Calendar.HOUR_OF_DAY, mCallLogList.get(i).getCalldate()
				.getHours() + 1);
		// endTime.set(Calendar.MINUTE,
		// mCallLogList.get(i).getCalldate()
		// .getMinutes());
		// endTime.set(Calendar.SECOND,
		// mCallLogList.get(i).getCalldate()
		// .getSeconds());
		// endTime.set(Calendar.MONTH, newMonth - 1);
		// endTime.set(Calendar.YEAR, newYear);
		event = new WeekViewEvent(i, mCallLogList.get(i).getCallername(),
				startTime, endTime);
		event.setColor(getResources().getColor(R.color.event_color_02));
		events.add(event);
	}

	private String getEventTitle(Calendar time) {
		return String.format("Event of %02d:%02d %s/%d",
				time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE),
				time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onEventClick(WeekViewEvent event, RectF eventRect) {
		Toast.makeText(DialerActivity.this, "Clicked " + event.getName(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
		Toast.makeText(DialerActivity.this,
				"Long pressed event: " + event.getName(), Toast.LENGTH_SHORT)
				.show();
	}
}
