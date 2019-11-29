package com.example.roombook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

private TextView checkindate,checkoutdate;
private Spinner spinActivity;
private AutoCompleteTextView autoCompleteTextView;
private Button btn;
private EditText troom,fname,adult,children;
private TextView Mtotal,Mvat,MGtotal;
AlertDialog.Builder builder;

//int toalroom;
    int dat=0;
 int validatedat=0;
   // Calendar thatDay = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For Drop down
        String[] Country={"Nepal","Bangladesh","Pakistan","New Zealand","Iceland","Brazil"};
        spinActivity=findViewById(R.id.spinCountry);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,Country);
        spinActivity.setAdapter(adapter);




        //FOr Date
        checkindate=findViewById(R.id.checkin);
        checkoutdate=findViewById(R.id.checkout);

        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dat=1;
                loadDatePicker();

            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dat=0;
                loadDatePicker();
            }
        });

        //For autocomplete tex view i.e. room type
        autoCompleteTextView=findViewById(R.id.roomtype);

        final String[] room={"Platinum","Deluxe","Presidential"};
        ArrayAdapter roomadapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,room);
        autoCompleteTextView.setAdapter(roomadapter);
        autoCompleteTextView.setThreshold(1);



        //For Calculation

        troom=findViewById(R.id.rooms);
        btn=findViewById(R.id.btnCalc);
        Mtotal=findViewById(R.id.total);
        Mvat=findViewById(R.id.vat);
        MGtotal=findViewById(R.id.gtotal);
        builder=new AlertDialog.Builder(this);
        adult=findViewById(R.id.adult);
        fname=findViewById(R.id.name);
        children=findViewById(R.id.children);


        btn.setOnClickListener(new View.OnClickListener() {
            int platinum=200000;
            int deluxe=150000;
            int presidential=300000;
            int total;
            float vat;
            float gtotal;




            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(fname.getText()))
                {
                    fname.setError("Please Enter Your Full Name");
                    fname.requestFocus();
                    return;

                }
                if(TextUtils.isEmpty((adult.getText())))
                {
                    adult.setError("Please Enter the number of adults");
                    adult.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(children.getText()))
                {
                    children.setError("Please Enter the number of children");
                    children.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(troom.getText()))
                {
                    troom.setError("Please enter the number of rooms you want to book");
                    troom.requestFocus();
                    return;
                }

                if(validatedat<2)
                {
                    builder.setMessage("Please select both check in and out date");
                    AlertDialog alert= builder.create();
                    alert.setTitle("Unchecked Date");
                    alert.show();
                    return;
                }
                if(TextUtils.isEmpty(autoCompleteTextView.getText()))
                {
                    autoCompleteTextView.setError("Select the type of room");
                    autoCompleteTextView.requestFocus();
                    return;
                }
                int totalroom=Integer.parseInt(troom.getText().toString());


                if(autoCompleteTextView.getText().toString().equals("Platinum"))
                {


                    total=totalroom*platinum;




                }
                else if(autoCompleteTextView.getText().toString().equals("Deluxe"))
                {
                    total=totalroom*deluxe;

                }
                else if(autoCompleteTextView.getText().toString().equals("Presidential"))
                {
                    total=totalroom*presidential;

                }
                else
                {
                    builder.setMessage("Please Choose the available room type");
                    AlertDialog alert= builder.create();
                    alert.setTitle("Unavailable room type");
                    alert.show();
                    return;

                }
                vat=(13*total)/100;
                gtotal=total+vat;
                Mtotal.setText(Integer.toString(total));
                Mvat.setText(Float.toString(vat));
                MGtotal.setText(Float.toString(gtotal));




            }
        });






    }

    public void loadDatePicker()
    {

        final Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);





       validatedat=validatedat+1;
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

       /* if(validatedat>0)
        {




            c.add(Calendar.DATE, datePickerDialog.getDatePicker().getDayOfMonth());

        }*/
        datePickerDialog.show();

    }




    @Override
    public void onDateSet(DatePicker view, int year, int month, int dateofmonth) {
        String date= dateofmonth + "/" + (month+1) + "/" + year;
        if(dat==1)
        {
            checkindate.setText(date);
        }
        else
        {
            checkoutdate.setText(date);
        }


    }





}
