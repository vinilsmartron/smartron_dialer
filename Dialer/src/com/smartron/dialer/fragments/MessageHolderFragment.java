package com.smartron.dialer.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smartron.dialer.R;
import com.smartron.dialer.adapter.CallLogAdapter;
import com.smartron.dialer.adapter.SMSLogAdapter;
import com.smartron.dialer.beans.CallLogBeans;
import com.smartron.dialer.beans.SmsMsg;

/**
 * A placeholder fragment containing a simple view.
 */
public class MessageHolderFragment extends Fragment {
	ArrayList<CallLogBeans> mCallLogList;
	private ListView mCallLogListView;
	private SMSLogAdapter mCallLogAdapter;
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
	public static MessageHolderFragment newInstance(int sectionNumber) {
		MessageHolderFragment fragment = new MessageHolderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MessageHolderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_messages, container,
				false);
		mCallLogList = new ArrayList<CallLogBeans>();
		mCallLogListView = (ListView) rootView.findViewById(R.id.messages_list);
		getSMS();
		return rootView;
	}

	public void getSMS() {

		// Init
		ArrayList<SmsMsg> smsMsgs = new ArrayList<SmsMsg>();
		TreeSet<Integer> threadIds = new TreeSet<Integer>();

		Uri mSmsinboxQueryUri = Uri.parse("content://sms");
		Cursor cursor = getActivity().getContentResolver().query(
				mSmsinboxQueryUri,
				new String[] { "_id", "thread_id", "address", "date", "body",
						"type" }, null, null, null);

		String[] columns = new String[] { "address", "thread_id", "date",
				"body", "type" };
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				SmsMsg smsMsg = new SmsMsg();

				String address = null, displayName = null, date = null, msg = null, type = null, threadId = null;
				Uri photoUri = null;

				threadId = cursor.getString(cursor.getColumnIndex(columns[1]));

				type = cursor.getString(cursor.getColumnIndex(columns[4]));

				if (Integer.parseInt(type) == 1 || Integer.parseInt(type) == 2) {

					address = cursor.getString(cursor
							.getColumnIndex(columns[0]));

					if (address.length() > 0) {

						String[] contactData = getContactByNumber(address);
						if (contactData != null) {
							displayName = contactData[0];
							if (contactData[1] != null)
								photoUri = Uri.parse(contactData[1]);
						}
					} else
						address = null;

					date = cursor.getString(cursor.getColumnIndex(columns[2]));
					msg = cursor.getString(cursor.getColumnIndex(columns[3]));

					smsMsg.setDisplayName(displayName);
					smsMsg.setThreadId(threadId);
					smsMsg.setAddress(address);
					smsMsg.setPhotoUri(photoUri);
					smsMsg.setDate(date);
					smsMsg.setMsg(msg);
					smsMsg.setType(type);

					// Log.e("SMS-inbox", "\n\nNAME: " + displayName
					// + "\nTHREAD_ID: " + threadId + "\nNUMBER: "
					// + address + "\nPHOTO_URI: " + photoUri + "\nTIME: "
					// + date + "\nMESSAGE: " + msg + "\nTYPE: " + type);

					smsMsgs.add(smsMsg);

					// Add threadId to Tree
					threadIds.add(Integer.parseInt(threadId));
				}

			}

		}
		mCallLogAdapter = new SMSLogAdapter(getActivity(), smsMsgs);
		mCallLogListView.setAdapter(mCallLogAdapter);

	}

	public String[] getContactByNumber(final String number) {
		String[] data = new String[2];

		try {

			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
					Uri.encode(number));

			Cursor cur = getActivity().getContentResolver().query(uri,
					new String[] { PhoneLookup.DISPLAY_NAME, PhoneLookup._ID },
					null, null, null);

			if (cur.moveToFirst()) {
				int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				data[0] = cur.getString(nameIdx);

				String contactId = cur.getString(cur
						.getColumnIndex(PhoneLookup._ID));

				Uri photoUri = getContactPhotoUri(Long.parseLong(contactId));

				if (photoUri != null)
					data[1] = photoUri.toString();
				else
					data[1] = null;

				cur.close();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Uri getContactPhotoUri(long contactId) {

		Uri photoUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
				contactId);
		photoUri = Uri.withAppendedPath(photoUri,
				Contacts.Photo.CONTENT_DIRECTORY);
		return photoUri;

	}
}
