package com.smartron.dialer.fragments;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.smartron.dialer.R;
import com.smartron.dialer.adapter.COntactsListAdapter;
import com.smartron.dialer.beans.ContactBeans;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactHolderFragment extends Fragment implements OnClickListener {
	ArrayList<ContactBeans> mCallLogList;
	private ListView mCallLogListView;
	private COntactsListAdapter mCallLogAdapter;
	Button btn[] = new Button[14];
	EditText userinput;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ContactHolderFragment newInstance(int sectionNumber) {
		ContactHolderFragment fragment = new ContactHolderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ContactHolderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_contact,
				container, false);
		mCallLogList = new ArrayList<ContactBeans>();
		mCallLogListView = (ListView) rootView.findViewById(R.id.contact_list);
		btn[0] = (Button) rootView.findViewById(R.id.button1);
		btn[1] = (Button) rootView.findViewById(R.id.button2);
		btn[2] = (Button) rootView.findViewById(R.id.button3);
		btn[3] = (Button) rootView.findViewById(R.id.button4);
		btn[4] = (Button) rootView.findViewById(R.id.button5);
		btn[5] = (Button) rootView.findViewById(R.id.button6);
		btn[6] = (Button) rootView.findViewById(R.id.button7);
		btn[7] = (Button) rootView.findViewById(R.id.button8);
		btn[8] = (Button) rootView.findViewById(R.id.button9);
		btn[9] = (Button) rootView.findViewById(R.id.button0);
		btn[10] = (Button) rootView.findViewById(R.id.button);
		btn[11] = (Button) rootView.findViewById(R.id.butto);
		userinput = (EditText) rootView.findViewById(R.id.numberpadtext);
		userinput.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int DRAWABLE_LEFT = 0;
				final int DRAWABLE_TOP = 1;
				final int DRAWABLE_RIGHT = 2;
				final int DRAWABLE_BOTTOM = 3;

				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (event.getRawX() >= (userinput.getRight() - userinput
							.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
							.width())) {
						// your action here
						userinput
								.setText(userinput
										.getText()
										.toString()
										.substring(
												0,
												userinput.getText().toString()
														.length() - 1));
						userinput.setSelection(userinput.getText().toString().length());
						return true;
					}
				}
				return false;
			}
		});
		// register onClick event
		for (int i = 0; i < 12; i++) {
			btn[i].setOnClickListener(this);
		}
		ContentResolver cr = getActivity().getContentResolver();
		String[] projection = new String[] { Contacts.DISPLAY_NAME,
				Phone.NUMBER, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI };

		String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";
		String[] selectionArgs = { String.valueOf(1) };
		Cursor cur;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			cur = cr.query(Phone.CONTENT_URI, projection, selection,
					selectionArgs, null);
		} else {
			cur = cr.query(Phone.CONTENT_URI, projection, null, null, null);
		}
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
						+ " ";
				String phoneNo = cur
						.getString(cur.getColumnIndex(Phone.NUMBER));
				final String photoUri = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
				ContactBeans contacts_Invitation = new ContactBeans();
				contacts_Invitation.setCallername(name);
				contacts_Invitation.setCallerNumber(phoneNo);

				if (phoneNo.length() < 17 && phoneNo.length() > 7) {

					mCallLogList.add(contacts_Invitation);
				}
			}
			cur.close();
			mCallLogAdapter = new COntactsListAdapter(getActivity(),
					mCallLogList);
			mCallLogListView.setAdapter(mCallLogAdapter);
		}

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			addtoarray("1");
			break;
		case R.id.button2:
			addtoarray("2");
			break;
		case R.id.button3:
			addtoarray("3");
			break;
		case R.id.button4:
			addtoarray("4");
			break;
		case R.id.button5:
			addtoarray("5");
			break;
		case R.id.button6:
			addtoarray("6");
			break;
		case R.id.button7:
			addtoarray("7");
			break;
		case R.id.button8:
			addtoarray("8");
			break;
		case R.id.button9:
			addtoarray("9");
			break;
		case R.id.button0:
			addtoarray("0");
			break;
		case R.id.button:
			addtoarray("*");
			break;
		case R.id.butto:
			addtoarray("#");
			break;
		default:
			break;
		}
	}

	public void addtoarray(String numbers) {
		// register TextBox

		userinput.append(numbers);
	}
}
