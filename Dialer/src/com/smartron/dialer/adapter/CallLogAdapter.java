package com.smartron.dialer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartron.dialer.R;
import com.smartron.dialer.beans.CallLogBeans;

/**
 * 
 * @author Vinil.S
 * 
 */
public class CallLogAdapter extends BaseAdapter {
	public Context context;
	public ArrayList<CallLogBeans> callLogArrayList;

	public CallLogAdapter(Context context,
			ArrayList<CallLogBeans> list) {
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
			convertView = infalInflater.inflate(R.layout.list_row_call_log,
					null);

			holder.mCallerName = (TextView) convertView
					.findViewById(R.id.caller_name);
			holder.mCallerTime = (TextView) convertView
					.findViewById(R.id.call_time);
			holder.mCallerNumber = (TextView) convertView
					.findViewById(R.id.call_number);
			holder.mCallerType = (TextView) convertView
					.findViewById(R.id.call_type);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.mCallerName
				.setText(callLogArrayList.get(position).getCallername());
		holder.mCallerTime.setText(callLogArrayList.get(position)
				.getCallDuration());
		holder.mCallerNumber.setText(callLogArrayList.get(position)
				.getCallerNumber());
		holder.mCallerType.setText(callLogArrayList.get(position).getCallType());

		return convertView;
	}

	public class Holder {
		public TextView mCallerName, mCallerType, mCallerNumber, mCallerTime;
	}

}
