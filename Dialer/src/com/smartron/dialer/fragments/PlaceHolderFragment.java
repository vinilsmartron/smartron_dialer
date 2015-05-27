package com.smartron.dialer.fragments;

import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smartron.dialer.R;
import com.smartron.dialer.adapter.CallLogAdapter;
import com.smartron.dialer.beans.CallLogBeans;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {
	ArrayList<CallLogBeans> mCallLogList;
	private ListView mCallLogListView;
	private CallLogAdapter mCallLogAdapter;
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
		@SuppressWarnings("deprecation")
		Cursor managedCursor = getActivity().managedQuery(
				CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		while (managedCursor.moveToNext()) {
//			String id_contact = managedCursor.getString(managedCursor
//					.getColumnIndex(ContactsContract.Contacts._ID));
//			String name_contact = managedCursor.getString(managedCursor
//					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
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
}
