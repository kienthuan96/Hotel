package com.example.thuan.hotel.Activity;
import com.example.thuan.hotel.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.design.widget.NavigationView;
import android.widget.Toast;

import com.example.thuan.hotel.Adapter.Adapter_Search_Hotel;
import com.example.thuan.hotel.Model.Hotel;
import com.firebase.client.Firebase;
import com.firebase.client.core.Constants;
import com.firebase.client.core.view.Event;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Locale.getDefault;

public class SearchActivity extends AppCompatActivity {
    /*   private DatabaseReference mDatabaseReference;
       private ListView mUserList;
       private EditText searchModule;
       private List<KhachSan> mKhachSanList ;
       private ArrayList<String> mUsernames = new ArrayList<>();
       private ArrayList<String> filteredUsernames = new ArrayList<>();*/
    final ArrayList<Float> events = new ArrayList<Float>();
    ListView mListView;
    private Adapter_Search_Hotel adapter;
    private List<Hotel> mKhachSanList ;
    private ArrayList<Hotel> arraylistkhachsan;
    public static DatabaseReference def;
    SearchView textSeach;
    Firebase myFB;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    SeekBar barseach;
    SeekBar barRate;
    RatingBar rbSao;
    TextView testSao;
    TextView test1;
    TextView testDiem;

    int progess = 0;
    int progessD = 0;
    float maxprice =0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.menuLogin:
                Intent intent=new Intent(SearchActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }



    public String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    private void SetSeach(final String seachstring)
    {
        Log.v("YourValue,", "seach is:" + seachstring);
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();
                    String full_hotel = covertStringToURL(t.get("city").toString());

                    Log.v("YourValue,", "chuoi nhap vao  is:" + full_hotel);

                    Log.v("YourValue,", "chuoi nhap vao  is:" + covertStringToURL(seachstring.toLowerCase()));
                    if (full_hotel.toLowerCase().contains(covertStringToURL(seachstring.toLowerCase()))) {

                        Log.v("YourValue,", "Map value is:" + t.toString());
                        mKhachSanList.add(new Hotel(t.get("name").toString(), t.get("address").toString(),
                                Float.parseFloat(t.get("price").toString()), t.get("img1").toString(),
                                Integer.parseInt(t.get("stars").toString()),
                                Integer.parseInt(t.get("rate").toString())));
                    }


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void seachPrice(final float Price)
    {
        //     Log.v("YourValue,", "seach is:" + seachstring);
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float price_hotel = Float.parseFloat(t.get("price").toString());

                    if (price_hotel<=Price) {

                        Log.v("YourValue,", "Map value is:" + t.toString());
                        mKhachSanList.add(new Hotel(t.get("name").toString(), t.get("address").toString(),
                                Float.parseFloat(t.get("price").toString()), t.get("img1").toString(),
                                Integer.parseInt(t.get("stars").toString()),
                                Integer.parseInt(t.get("rate").toString())));
                        float rate_hotel = Float.parseFloat(t.get("rate").toString());

                    }


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void seachStar(final float Star)
    {
        //     Log.v("YourValue,", "seach is:" + seachstring);
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float stars_hotel = Float.parseFloat(t.get("stars").toString());

                    if (stars_hotel>=Star) {

                        Log.v("YourValue,", "Map value is:" + t.toString());
                        mKhachSanList.add(new Hotel(t.get("name").toString(), t.get("address").toString(),
                                Float.parseFloat(t.get("price").toString()), t.get("img1").toString(),
                                Integer.parseInt(t.get("stars").toString()),
                                Integer.parseInt(t.get("rate").toString())));
                   //     float rate_hotel = Float.parseFloat(t.get("rate").toString());

                    }


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

float max,min;
    private  void TimMax(int count,float giatri)
    {
        if(count == 0)
        {
            max = giatri;
            min = giatri;

        }
        else
        {
            if(max<giatri)
            {
                max = giatri;
            }
            if(min>giatri)
            {
                min = giatri;
            }
        }

    }

    private void seachRate(final float Rate)
    {
        //     Log.v("YourValue,", "seach is:" + seachstring);
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();
                int count =0,count1=0;
              float max=0,min=0;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float rate_hotel = Float.parseFloat(t.get("rate").toString());
                    float price_hotel = Float.parseFloat(t.get("price").toString());

                    if (rate_hotel>=Rate) {


                        mKhachSanList.add(new Hotel(t.get("name").toString(), t.get("address").toString(),
                                Float.parseFloat(t.get("price").toString()), t.get("img1").toString(),
                                Integer.parseInt(t.get("stars").toString()),
                                Integer.parseInt(t.get("rate").toString())));
                       // max =  TimMax(count,price_hotel);
                       // min = TimMin(count,price_hotel);
                    }
                   /* if(rate_hotel<=Rate)
                    {

                        count++;
                        count1++;
                    }
                    */

                }
                /*Log.v("MAX1234,", "Map value is:" + max);
                Log.v("MIN1234,", "Map value is:" + min);
                barseach.setMax((int) max+(int) min);
                barseach.setMin((int)min);
                barseach.setProgress((int)min);
*/
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getMaxPrice()
    {
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();
                int count =0;
              //  float max = 0,min =0;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float price_hotel = Float.parseFloat(t.get("price").toString());
                    TimMax(count,price_hotel);
                    count++;
                    Log.v("MINCONCAC1", "Map value is:" + String.valueOf(min));
                    Log.v("MAXCONCAC1", "Map value is:" + String.valueOf(max));
                }

                barseach.setMax((int) max+(int) min);
                barseach.setMin((int)min);
                barseach.setProgress((int)min);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void getMaxRate()
    {
        def = FirebaseDatabase.getInstance().getReference("hotel");
        def.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mKhachSanList.clear();
                int count =0;
               // float max = 0,min =0;
              //  float max = 0,min =0,max1=0,min1=0;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float rate_hotel = Float.parseFloat(t.get("rate").toString());
                    float price_hotel = Float.parseFloat(t.get("price").toString());
                  TimMax(count,rate_hotel);
                    count++;
                }
                Log.v("MINCONCAC", "Map value is:" + String.valueOf(min));
                Log.v("MAXCONCAC", "Map value is:" + String.valueOf(max));
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap t = (HashMap) childSnapshot.getValue();

                    float rate_hotel = Float.parseFloat(t.get("rate").toString());
                    float price_hotel = Float.parseFloat(t.get("price").toString());

                }

                barRate.setMax((int) max+(int) min);
                barRate.setMin((int) min);
                barRate.setProgress((int)max);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        barseach = (SeekBar)findViewById(R.id.sbGia);
        barRate = (SeekBar)findViewById(R.id.sbDiem);
        testDiem = (TextView)findViewById(R.id.testDiem);
        test1 = (TextView)findViewById(R.id.test);
        rbSao = (RatingBar)findViewById(R.id.rbSao);
        testSao = (TextView)findViewById(R.id.testSao);
        rbSao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                testSao.setText("NANA"+rating);
                seachStar(rating);
            }
        });

        barRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                progessD =i;
                testDiem.setText(""+progessD);
                testDiem.setTextSize(17);
                seachRate(progessD);
                Log.v("progess,", "chuoi nhap vao  is:" + testDiem.getText());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        barseach.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                progess =i;
                test1.setText(""+progess);
                test1.setTextSize(17);
                seachPrice(progess);
                Log.v("progess,", "chuoi nhap vao  is:" + test1.getText());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dl = (DrawerLayout)findViewById(R.id.dl);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //
        nv = (NavigationView)findViewById(R.id.nv);

        mListView = (ListView) findViewById(R.id.dsKhachSan);
        textSeach = (SearchView) findViewById(R.id.txtSeach);


        mKhachSanList = new ArrayList<>();
        adapter = new Adapter_Search_Hotel(getApplicationContext(),mKhachSanList);
        mListView.setAdapter(adapter);
        Firebase.setAndroidContext(this);
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        textSeach.setQueryHint("Nhap ten khach san/dia diem");
        myFB = new Firebase("https://hotel-793b0.firebaseio.com/hotel");
        def = FirebaseDatabase.getInstance().getReference();

        getMaxPrice();
        getMaxRate();
   //     Log.v("MAXCONCAC", "Map value is:" + maxprice);
        textSeach.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchString1 = textSeach.getQuery().toString();

                //  final String seach1 = searchString1.toLowerCase(getDefault());
                if (!searchString1.toString().isEmpty()) {
                    SetSeach(searchString1);
                } else {

                    mKhachSanList.clear();
                    //     SetSeach("");
                    adapter.notifyDataSetChanged();
                    // mListView.removeAllViews();
                    //mListView.setAdapter(null);

                }
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }




}
