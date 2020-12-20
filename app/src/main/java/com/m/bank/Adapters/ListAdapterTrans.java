package com.m.bank.Adapters;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m.bank.Modules.Transaction;
import com.m.bank.R;
import java.util.ArrayList;

public class ListAdapterTrans  extends BaseAdapter {
    ArrayList<Transaction> transactions;
    public ListAdapterTrans(ArrayList<Transaction> data ){

        transactions = data;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from( parent.getContext()).inflate(R.layout.transaction_layout,parent,false);
        }
        TextView price,num,date;
        num = convertView.findViewById(R.id.numOfTrans);
        price = convertView.findViewById(R.id.price);
        date = convertView.findViewById(R.id.dateOfTrans);
        num.setText(String.valueOf(getItem(position).getNum()));
        price.setText(String.valueOf(getItem(position).getPrice()));
        date.setText(getItem(position).getDate());

        return convertView;
    }
}
