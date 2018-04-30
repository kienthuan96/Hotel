package com.example.thuan.hotel.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderActivity extends AppCompatActivity implements
        View.OnClickListener {
    ImageButton btnDatePicker, btnTimePicker;
    Button btnDat,btnUp,btnDown;
    EditText txtDate, txtTime,txtHoTen,txtEmail,txtSDT,txtSophong,txtGiaTien;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static DatabaseReference def;
    public static DatabaseReference Mydef;
    private void getPrice(final  int day,final int room)
    {
        txtGiaTien = (EditText)findViewById(R.id.txtGiaTien);
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    String key = t.get("id").toString();


                    if(key.equals("-LAqUFdTyh6UXVtnASG9"))
                    {
                        float price_hotel = Float.parseFloat(t.get("price").toString());
                        float tong = (price_hotel*(float)day)*(float)room;
                        txtGiaTien.setText(tong+"");
                        Log.v("GIATIEN1",String.valueOf(price_hotel));
                    }



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void getTimeInEditText()
    {
        btnDatePicker=(ImageButton)findViewById(R.id.btn_date);
        btnTimePicker=(ImageButton)findViewById(R.id.btn_time);
        btnUp = (Button)findViewById(R.id.btn_up);
        btnDown = (Button)findViewById(R.id.btn_down);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        // Date currentTime = Calendar.getInstance().getTime();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        try {
            Date dateReal = df.parse(formattedDate);
            Date datetomorrow =  addDays(dateReal, 1);
            String formattedDatetmr = df.format(datetomorrow);
            txtDate.setText(formattedDatetmr+"");
            txtTime.setText(formattedDate+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private  void setGia()
    {
        txtSophong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String ngaybatdau = txtTime.getText().toString();
                    String ngayketthuc = txtDate.getText().toString();
                    int sophong = Integer.parseInt(txtSophong.getText().toString());

                    Date datebatdau =new SimpleDateFormat("dd/MM/yyyy").parse(ngaybatdau);
                    Date dateketthuc =new SimpleDateFormat("dd/MM/yyyy").parse(ngayketthuc);
                    long a = (dateketthuc.getTime() - datebatdau.getTime())/86400000;
                    Log.v("songay1",String.valueOf(a));
                    getPrice((int) a,sophong);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String ngaybatdau = txtTime.getText().toString();
                    String ngayketthuc = txtDate.getText().toString();
                    int sophong = Integer.parseInt(txtSophong.getText().toString());
                    Date datebatdau =new SimpleDateFormat("dd/MM/yyyy").parse(ngaybatdau);
                    Date dateketthuc =new SimpleDateFormat("dd/MM/yyyy").parse(ngayketthuc);
                    long a = (dateketthuc.getTime() - datebatdau.getTime())/86400000;
                    Log.v("songay1",String.valueOf(a));
                    getPrice((int) a,sophong);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    int a =1;
    private void setUp()
    {

        btnUp = (Button)findViewById(R.id.btn_up);

        btnUp.setOnClickListener(v -> {



            Log.d("adfsd", "HHH");
            txtSophong.setText(a+"");

            a++;
        });
    }
    private void setDown()
    {

        btnDown = (Button)findViewById(R.id.btn_down);

        btnDown.setOnClickListener(v -> {



            Log.d("adfsd", "HHH");
            txtSophong.setText(a+"");

            a--;
        });
    }
    private void Dathang()
    {
        Mydef = FirebaseDatabase.getInstance().getReference("order");
        btnDat = (Button)findViewById(R.id.btn_datphong);

        btnDat.setOnClickListener(v -> {
            String HoTen = txtHoTen.getText().toString();
            String ngaynhanphong = txtTime.getText().toString();
            String ngaytraphong = txtDate.getText().toString();
            String email = txtEmail.getText().toString();
            String dienthoai = txtSDT.getText().toString();
            float giatien = Float.parseFloat(txtGiaTien.getText().toString());
            int Sophong = Integer.parseInt(txtSophong.getText().toString());
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher matcher = pattern.matcher(email);
            // Empty
            if(HoTen.equals("")) {
                Toast.makeText(this, "Bạn chưa nhập họ tên", Toast.LENGTH_LONG).show();
                return;
            }

            if(email.equals("")) {
                Toast.makeText(this, "Bạn chưa nhập email", Toast.LENGTH_LONG).show();
                return;
            }

            if(dienthoai.equals("")) {
                Toast.makeText(this, "Bạn chưa nhập điện thoại", Toast.LENGTH_LONG).show();
                return;
            }

            // Regex
            if(!isEmailValid(email)) {
                Toast.makeText(this, "Không đúng định dạng email", Toast.LENGTH_LONG).show();
                return;
            }

            if(!PhoneNumberUtils.isGlobalPhoneNumber(dienthoai)) {
                Toast.makeText(this, "Không đúng định dạng số điện thoại", Toast.LENGTH_LONG).show();
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("thanghoang064@gmail.com", "eotzmfeyuycgblmi");
                        sender.sendMail("Xác nhận đặt phòng thành công",
                                "Cảm ơn bạn đã đặt phòng từ ngày "+ngaynhanphong +" tới ngày " +ngaytraphong +". Với giá tiền" +giatien
                                +". Khách sạn sẽ liên hệ lại với bạn chúc bạn có 1 kì nghỉ vui vẻ ở khách sạn"
                                ,
                                email,
                                email);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }
            }).start();

            String temp=    Mydef.push().getKey();
            HashMap<String, String> t = new HashMap<>();
            t.put("TotalMoney", String.valueOf(giatien));
            t.put("hotel_id", "LAmx-7ZT1PtZSoYTX0O");
            t.put("name", HoTen);
            t.put("RoomOrder", String.valueOf(Sophong));
            t.put("Phone", dienthoai);
            t.put("Email", email);
            t.put("DateStarOrder", ngaynhanphong);
            t.put("DateEndOrder", ngaytraphong);
            Mydef.child(temp).setValue(t);


        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datphong);
        getTimeInEditText();
        setUp();
        Dathang();
        setDown();
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtSophong = (EditText)findViewById(R.id.txtSoPhong);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtSDT =(EditText)findViewById(R.id.txtDienThoai);
        txtHoTen =(EditText)findViewById(R.id.txtHoTen);
        txtGiaTien =(EditText)findViewById(R.id.txtGiaTien);
        String ngaybatdau = txtTime.getText().toString();
        String ngayketthuc = txtDate.getText().toString();
        txtSophong.setText("1");
        try {
            Date datebatdau =new SimpleDateFormat("dd/MM/yyyy").parse(ngaybatdau);
            Date dateketthuc =new SimpleDateFormat("dd/MM/yyyy").parse(ngayketthuc);
            long a = (dateketthuc.getTime() - datebatdau.getTime())/86400000;
            Log.v("songay1",String.valueOf(a));
            getPrice((int) a,1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        setGia();




    }

    @Override
    public void onClick(View v)  {



        if (v == btnDatePicker) {

            // Get Current Date
            try {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            String ngaybatdau = txtDate.getText().toString();
            Log.v("FGH",ngaybatdau);
            Date day =new SimpleDateFormat("dd/MM/yyyy").parse(ngaybatdau);
            String days =new SimpleDateFormat("dd").format(day);
            String months =new SimpleDateFormat("MM").format(day);
                Log.v("FGH1",months+"");
            String years =new SimpleDateFormat("yyyy").format(day);
            int getdays = Integer.parseInt(days);
            int getMonths = Integer.parseInt(months);
            int getYears = Integer.parseInt(years);
            c.add(Calendar.DATE, 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date ch = Calendar.getInstance().getTime();
                            String formattedDate1234 = formatter.format(ch);
                                Date dateReal = formatter.parse(formattedDate1234);
                                String sDate1=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Date dateset=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                                Date datetomorrow =  addDays(dateReal, 1);
                                String formattedDatetomorrow = formatter.format(datetomorrow);
                             /*  */
                             //
                                if(datetomorrow.after(dateset)==true)
                                {

                                    txtDate.setText(formattedDatetomorrow);
                                }
                                else {
                                    txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    }, getYears, getMonths-1, getdays);
            datePickerDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }



        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                Date ch = Calendar.getInstance().getTime();
                                String formattedDate1234 = formatter.format(ch);
                                Date dateReal = formatter.parse(formattedDate1234);
                                String sDate1=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Date dateset=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                                Date datetomorrow =  addDays(dateReal, 1);
                                String formattedDatetomorrow = formatter.format(datetomorrow);
                                /*  */
                                //
                                if(datetomorrow.after(dateset)==true)
                                {

                                    txtTime.setText(formattedDatetomorrow);
                                }
                                else {
                                    txtTime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}


