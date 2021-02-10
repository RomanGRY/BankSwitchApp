package com.bankapp.privat_nbu_Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PrivatAdapter.OnItemClickListener {

    private RecyclerView privatRecycler,nbuRecycler;
    private List<PrivatBankItem> privatBankItems=new ArrayList<>();
    private List<NBUBankItem> nbuBankItems=new ArrayList<>();
    private TextView privateDate, nbuDate;
    private ImageView privateDatePicker,nbuDatePicker;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private PrivatAdapter privatAdapter;
    private NBUAdapter nbuAdapter;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue=Volley.newRequestQueue(this);
        setDateAndInitFields();

        privateDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOne(true);
                setDate();
            }
        });
        nbuDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOne(false);
                setDate();
            }
        });
    }

    private void getCurrency (String url) {
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading currency data. Please wait...", true);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray curArray = response.getJSONArray("exchangeRate");
                            for (int i = 1; i < curArray.length(); i++) {
                                JSONObject curObject = curArray.getJSONObject(i);
                                String currency = curObject.get("currency").toString();
                                String saleRate = curObject.get("saleRateNB").toString();
                                String purchaseRate = curObject.get("purchaseRateNB").toString();
                                privatBankItems.add(new PrivatBankItem(currency, Double.valueOf(saleRate), Double.valueOf(purchaseRate)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        privatRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        privatRecycler.setHasFixedSize(true);
                        privatAdapter = new PrivatAdapter(MainActivity.this, privatBankItems);
                        privatRecycler.setAdapter(privatAdapter);
                        privatAdapter.setOnItemsClickListener(MainActivity.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","ResponseErrorMessage : "+error.getMessage());
            }
        });
              queue.add(jsonObjectRequest);
    }
    private void getNbuCurrency (String url) {
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading currency data. Please wait...", true);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject curObject = response.getJSONObject(i);
                            String txt = curObject.get("txt").toString();
                            String rate = curObject.get("rate").toString();
                            String cc = curObject.get("cc").toString();
                            nbuBankItems.add(new NBUBankItem(txt,Double.valueOf(rate),cc));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                dialog.dismiss();
                nbuRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                nbuRecycler.setHasFixedSize(true);
                nbuAdapter = new NBUAdapter(MainActivity.this, nbuBankItems);
                nbuRecycler.setAdapter(nbuAdapter);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","ResponseErrorMessage : "+error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void setOne(final boolean i){
        mOnDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar=Calendar.getInstance();
                int checkYear=calendar.get(Calendar.YEAR);
                int checkMonth=calendar.get(Calendar.MONTH);
                int checkDay=calendar.get(Calendar.DAY_OF_MONTH);
                if (year>checkYear||year<2014){
                    Toast.makeText(MainActivity.this, "Some of Banks can not reach this year ! ", Toast.LENGTH_SHORT).show();
                }
                else
                    if(year==checkYear&&month>checkMonth){
                        Toast.makeText(MainActivity.this, "We haven't reach this day yet ! ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(year==checkYear&&month==checkMonth&&day>checkDay){
                        Toast.makeText(MainActivity.this, "We haven't reach this day yet ! ", Toast.LENGTH_SHORT).show();
                    }
                else {
                    if (i&&privatBankItems.size()!=0){
                        privatBankItems.clear();
                    }
                    if (!i&&nbuBankItems.size()!=0){
                        nbuBankItems.clear();
                    }
                    month=month+1;
                    String date= day+"."+month+"."+year;
                    SpannableString content = new SpannableString(date);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    String sMonth,sDay;
                    if (month<10){
                        sMonth="0"+month;
                    }
                    else {
                        sMonth=String.valueOf(month);
                    }
                    if (day<10){
                        sDay="0"+day;
                    }
                    else {
                        sDay=String.valueOf(day);
                    }
                    String revDate=year+""+sMonth+""+sDay;
                    if (i){
                        privateDate.setText(content);
                        getCurrency("https://api.privatbank.ua/p24api/exchange_rates?json&date="+date);
                    }
                    else {
                        nbuDate.setText(content);
                        getNbuCurrency("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date="+revDate+"&json");
                    }
                }
            }
        };
    }

    private void setDate() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog,
                mOnDateSetListener, year,month,day);
        dialog.show();
    }

    private void setDateAndInitFields() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String date=day+"."+month+"."+year;
        String sMonth,sDay;
        if (month<10){
            sMonth="0"+month;
        }
        else {
            sMonth=String.valueOf(month);
        }
        if (day<10){
            sDay="0"+day;
        }
        else {
            sDay=String.valueOf(day);
        }
        String revDate=year+""+sMonth+""+sDay;
        privatRecycler=findViewById(R.id.private_recycler);
        nbuRecycler=findViewById(R.id.nbu_recycler);
        privateDate=findViewById(R.id.private_date);
        nbuDate=findViewById(R.id.nbu_date);
        privateDatePicker=findViewById(R.id.private_date_picker);
        nbuDatePicker=findViewById(R.id.nbu_date_picker);
        SpannableString content = new SpannableString(date);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        privateDate.setText(content);
        nbuDate.setText(content);
        getCurrency("https://api.privatbank.ua/p24api/exchange_rates?json&date="+date);
        getNbuCurrency("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date="+revDate+"&json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_graphic){
            Intent intent=new Intent(MainActivity.this,GraphicActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        PrivatBankItem clickItem=privatBankItems.get(position);
        Toast.makeText(this, clickItem.getCurrency(), Toast.LENGTH_SHORT).show();
        for (NBUBankItem nb :nbuBankItems){
            if (nb.getCc().equals(clickItem.getCurrency())){
                nbuRecycler.smoothScrollToPosition(position);
            }
        }
    }
}