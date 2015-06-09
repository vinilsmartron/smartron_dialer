package com.smartron.dialer.fragments;

import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.smartron.dialer.R;
import com.smartron.dialer.activity.MainActivity;
import com.smartron.dialer.adapter.CallLogAdapter;
import com.smartron.dialer.beans.CallLogBeans;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {
	ArrayList<CallLogBeans> mCallLogList;
	private ListView mCallLogListView;
	private CallLogAdapter mCallLogAdapter;
	private ImageView mNumpadImage;
	// private static final int TYPE_DAY_VIEW = 1;
	// private static final int TYPE_THREE_DAY_VIEW = 2;
	// private static final int TYPE_WEEK_VIEW = 3;
	// private int mWeekViewType = TYPE_THREE_DAY_VIEW;
	// private WeekView mWeekView;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceHolderFragment newInstance(int sectionNumber) {
		PlaceHolderFragment fragment = new PlaceHolderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceHolderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mCallLogList = new ArrayList<CallLogBeans>();
		mCallLogListView = (ListView) rootView.findViewById(R.id.call_log_list);
		mNumpadImage = (ImageView) rootView
				.findViewById(R.id.keypad_enable_image_view);
		mNumpadImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				((MainActivity) getActivity()).mShowNumpad = true;
				((MainActivity) getActivity()).mViewPager.setCurrentItem(1);

			}
		});
		// Get a reference for the week view in the layout.
		// mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
		//
		// // Show a toast message about the touched event.
		// mWeekView.setOnEventClickListener(this);
		//
		// // The week view has infinite scrolling horizontally. We have to
		// provide
		// // the events of a
		// // month every time the month changes on the week view.
		// mWeekView.setMonthChangeListener(this);
		//
		// // Set long press listener for events.
		// mWeekView.setEventLongPressListener(this);
		// setupDateTimeInterpreter(false);
		Cursor managedCursor = getActivity().getContentResolver().query(
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
			clb.setCalldate(callDate);
			clb.setCallDuration(callDuration);
			clb.setCallType(callType);
			mCallLogList.add(clb);
		}
		managedCursor.close();
		mCallLogAdapter = new CallLogAdapter(getActivity(), mCallLogList);
		mCallLogListView.setAdapter(mCallLogAdapter);
		return rootView;
	}

	/**
	 * Set up a date time interpreter which will show short date values when in
	 * week view and long date values otherwise.
	 * 
	 * @param shortDate
	 *            True if the date values should be short.
	 */
	// private void setupDateTimeInterpreter(final boolean shortDate) {
	// mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
	// @Override
	// public String interpretDate(Calendar date) {
	// SimpleDateFormat weekdayNameFormat = new SimpleDateFormat(
	// "EEE", Locale.getDefault());
	// String weekday = weekdayNameFormat.format(date.getTime());
	// SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale
	// .getDefault());
	//
	// // All android api level do not have a standard way of getting
	// // the first letter of
	// // the week day name. Hence we get the first char
	// // programmatically.
	// // Details:
	// //
	// http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
	// if (shortDate)
	// weekday = String.valueOf(weekday.charAt(0));
	// return weekday.toUpperCase() + format.format(date.getTime());
	// }
	//
	// @Override
	// public String interpretTime(int hour) {
	// return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM"
	// : hour + " AM");
	// }
	// });
	// }
	//
	// @Override
	// public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
	// // Populate the week view with some events.
	// List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
	//
	// Calendar startTime = Calendar.getInstance();
	// startTime.set(Calendar.HOUR_OF_DAY, 3);
	// startTime.set(Calendar.MINUTE, 0);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// Calendar endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR, 1);
	// endTime.set(Calendar.MONTH, newMonth - 1);
	// WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime),
	// startTime, endTime);
	// event.setColor(getResources().getColor(R.color.event_color_01));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.HOUR_OF_DAY, 3);
	// startTime.set(Calendar.MINUTE, 30);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.set(Calendar.HOUR_OF_DAY, 4);
	// endTime.set(Calendar.MINUTE, 30);
	// endTime.set(Calendar.MONTH, newMonth - 1);
	// event = new WeekViewEvent(10, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_02));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.HOUR_OF_DAY, 4);
	// startTime.set(Calendar.MINUTE, 20);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.set(Calendar.HOUR_OF_DAY, 5);
	// endTime.set(Calendar.MINUTE, 0);
	// event = new WeekViewEvent(10, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_03));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.HOUR_OF_DAY, 5);
	// startTime.set(Calendar.MINUTE, 30);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR_OF_DAY, 2);
	// endTime.set(Calendar.MONTH, newMonth - 1);
	// event = new WeekViewEvent(2, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_02));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.HOUR_OF_DAY, 5);
	// startTime.set(Calendar.MINUTE, 0);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// startTime.add(Calendar.DATE, 1);
	// endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR_OF_DAY, 3);
	// endTime.set(Calendar.MONTH, newMonth - 1);
	// event = new WeekViewEvent(3, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_03));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.DAY_OF_MONTH, 15);
	// startTime.set(Calendar.HOUR_OF_DAY, 3);
	// startTime.set(Calendar.MINUTE, 0);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR_OF_DAY, 3);
	// event = new WeekViewEvent(4, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_04));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.DAY_OF_MONTH, 1);
	// startTime.set(Calendar.HOUR_OF_DAY, 3);
	// startTime.set(Calendar.MINUTE, 0);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR_OF_DAY, 3);
	// event = new WeekViewEvent(5, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_01));
	// events.add(event);
	//
	// startTime = Calendar.getInstance();
	// startTime.set(Calendar.DAY_OF_MONTH,
	// startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
	// startTime.set(Calendar.HOUR_OF_DAY, 15);
	// startTime.set(Calendar.MINUTE, 0);
	// startTime.set(Calendar.MONTH, newMonth - 1);
	// startTime.set(Calendar.YEAR, newYear);
	// endTime = (Calendar) startTime.clone();
	// endTime.add(Calendar.HOUR_OF_DAY, 3);
	// event = new WeekViewEvent(5, getEventTitle(startTime), startTime,
	// endTime);
	// event.setColor(getResources().getColor(R.color.event_color_02));
	// events.add(event);
	// return null;
	// }
	//
	// @Override
	// public void onEventClick(WeekViewEvent event, RectF eventRect) {
	// Toast.makeText(getActivity(), "Clicked " + event.getName(),
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
	// Toast.makeText(getActivity(), "Long pressed event: " + event.getName(),
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// private String getEventTitle(Calendar time) {
	// return String.format("Event of %02d:%02d %s/%d",
	// time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE),
	// time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
	// }
}
