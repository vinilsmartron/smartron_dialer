package com.smartron.dialer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartron.dialer.R;
import com.smartron.dialer.beans.SmsMsg;

/**
 * 
 * @author Vinil.S
 * 
 */
public class SMSLogAdapter extends BaseAdapter {
	public Context context;
	public ArrayList<SmsMsg> callLogArrayList;

	public SMSLogAdapter(Context context, ArrayList<SmsMsg> list) {
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
			convertView = infalInflater.inflate(R.layout.list_row_sms, null);

			holder.mCallerName = (TextView) convertView
					.findViewById(R.id.msg_name);
			holder.mCallerTime = (TextView) convertView
					.findViewById(R.id.msg_text);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.mCallerName.setText("Vinil");
		holder.mCallerTime.setText(callLogArrayList.get(position).getMsg());

		return convertView;
	}

	public class Holder {
		public TextView mCallerName, mCallerTime;
	}

}
