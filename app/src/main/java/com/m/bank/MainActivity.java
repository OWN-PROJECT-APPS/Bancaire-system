package com.m.bank;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.m.bank.Adapters.ListAdapterTrans;
import com.m.bank.Modules.Transaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener ,  View.OnFocusChangeListener {
    ListView transList;
    FloatingActionButton addNew;
    ArrayList<Transaction> data = new ArrayList<>();
    ListAdapterTrans listAdapter;
    int numOfTrans = 0;
    Toolbar toolbar;
    Dialog dialog;
    EditText  price,date;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transList = findViewById(R.id.transList);
        addNew = findViewById(R.id.addNew);
        toolbar = findViewById(R.id.bt_toolbar);
        addNew.setOnClickListener(this);
        listAdapter = new ListAdapterTrans(data);
        transList.setAdapter(listAdapter);
        toolbar.setTitle(getTotalSum() + " $ ");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        dialog  = new Dialog(this);
        dialog.setContentView(R.layout.activity_transact);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.setCancelable(true);
        btnAdd   =  dialog.findViewById(R.id.add);
        price    = dialog.findViewById(R.id.addPrice);
        date     = dialog.findViewById(R.id.date);
        date.setOnFocusChangeListener(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p = price.getText().toString();
                String d = date.getText().toString();
                if(p.isEmpty()){
                    price.setError("Please Fill Price Field");
                    return;
                }
                if(d.isEmpty()){
                    date.setError("Please Fill Date Field");
                    return;
                }
                numOfTrans += 1;
                Transaction tr = new Transaction(numOfTrans,Double.parseDouble(p),d);
                setNewTransaction(tr);
            }
        });
        dialog.show();
    }

    private void setNewTransaction(Transaction tr) {
        // Get Current Debit
        double sum = getTotalSum();
        dialog.dismiss();
        if(tr.getPrice() < 0 && sum < Math.abs(tr.getPrice())){

            if(sum == 0){

                Snackbar.make(transList, "You Balance Is : 0  $ ", Snackbar.LENGTH_SHORT).show();
            }else{

                Snackbar.make(transList, "You can't Pull More Than : " + sum + " $ ", Snackbar.LENGTH_SHORT).show();
            }


            return;
        }
        this.data.add(tr);
        this.listAdapter.notifyDataSetChanged();
        Snackbar.make(transList, "Added Successfully", Snackbar.LENGTH_SHORT).show();
        toolbar.setTitle(getTotalSum() + " $ ");
    }

    private  double getTotalSum(){
        double sum = 0;
        for(Transaction t: data){

            sum += t.getPrice();

        }

        return  sum;
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notify").setMessage("Do You Wanna To Quit  !");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                }).setCancelable(false);
        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // Get Current Date

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        },mYear,mMonth,mDay);
        if(hasFocus){

            datePickerDialog.show();
        }
    }
}