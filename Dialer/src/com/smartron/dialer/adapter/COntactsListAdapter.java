package com.smartron.dialer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartron.dialer.R;
import com.smartron.dialer.beans.ContactBeans;

/**
 * 
 * @author Vinil.S
 * 
 */
public class COntactsListAdapter extends BaseAdapter {
	public Context context;
	public ArrayList<ContactBeans> callLogArrayList;

	public COntactsListAdapter(Context context, ArrayList<ContactBeans> list) {
		this.context = context;
		this.callLogArrayList = list;
	}

	@Override
	public int getCount() {
		return callLogArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return callLogArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		LayoutInflater infalInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = infalInflater.inflate(R.layout.list_row_contacts,
					null);

			holder.mCallerName = (TextView) convertView
					.findViewById(R.id.contact_name);
			holder.mCallerTime = (TextView) convertView
					.findViewById(R.id.contact_number);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.mCallerName.setText(callLogArrayList.get(position)
				.getCallername());
		holder.mCallerTime.setText(callLogArrayList.get(position)
				.getCallerNumber());

		return convertView;
	}

	public class Holder {
		public TextView mCallerName, mCallerTime;
	}

}
