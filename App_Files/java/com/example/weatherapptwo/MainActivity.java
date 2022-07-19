package com.example.weatherapptwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements MainAdapter.ListItemClickListener {
    String City="Colombo";
    String key="a18b978603316d47c572d98d52a420f6";
    String link1 = "metric";
    String link2 = "imperial";
    String day_1,day_2,day_3,day_4,day_5,day_6;
    SwitchCompat switchCompat;
    RecyclerView recyclerView;



    List<String> tempArray = new ArrayList<>();
    List<String> humidityArray = new ArrayList<>();
    List<String> feel_Like_Array = new ArrayList<>();
    List<String> pressureArray = new ArrayList<>();
    List<String> speedArray = new ArrayList<>();
    List<String> rainArray = new ArrayList<>();
    List<String> sIcon = new ArrayList<>();
    List<String> urlIcon = new ArrayList<>();
    List<String> stimeArray = new ArrayList<>();
    List<String > stimeArray2= new ArrayList<>();
//    List<Bitmap> bitmapArray = new ArrayList<>();
    List<String> sunset =new ArrayList<>();
    List<String> sunrise = new ArrayList<>();
    List<String> descriptionArray = new ArrayList<>();

    public static final String EXTRA_TEXT="com.example.weatherapp.EXTRA_TEXT";
    public static final String EXTRA_TEXT_1 ="com.example.weatherapp.EXTRA_TEXT_1";
    public static final String EXTRA_TEXT_2 ="com.example.weatherapp.EXTRA_TEXT_2";
    public static final String EXTRA_TEXT_3 ="com.example.weatherapp.EXTRA_TEXT_3";
    public static final String EXTRA_TEXT_4 ="com.example.weatherapp.EXTRA_TEXT_4";
    public static final String EXTRA_TEXT_5 ="com.example.weatherapp.EXTRA_TEXT_5";
    public static final String EXTRA_TEXT_6 ="com.example.weatherapp.EXTRA_TEXT_6";
    public static final String EXTRA_TEXT_7 ="com.example.weatherapp.EXTRA_TEXT_7";
    public static final String EXTRA_TEXT_8 ="com.example.weatherapp.EXTRA_TEXT_8";
    public static final String EXTRA_TEXT_9 ="com.example.weatherapp.EXTRA_TEXT_9";

    TextView txtCity, txtTime, txtValueFeelLike,txtValueHumidity,txtrain,txtpressure,txtSpeed,txtTemp;
    ImageView imageView,mainicon;
    EditText edt;
    Button btn;
    RelativeLayout rlWeather,rlRoot;
    ScrollView sv;
    public void back(View view) {
        edt.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
        mainicon.setVisibility(View.VISIBLE);
        rlWeather.setVisibility(View.INVISIBLE);
        switchCompat.setVisibility(View.VISIBLE);
        rlRoot.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public class DownloadJSON extends AsyncTask<String, Void ,String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            String result ="";
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1){
                    result +=(char)data;
                    data = inputStreamReader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    public static class DownloadIcon extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream =  httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    public void Loading(View view){
        City =edt.getText().toString();
        String url= "https://api.openweathermap.org/data/2.5/forecast/daily?q="+City+"&cnt=7&appid="+key+"&units="+link2;
        edt.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        mainicon.setVisibility(View.INVISIBLE);
        rlWeather.setVisibility(View.VISIBLE);
        switchCompat.setVisibility(View.INVISIBLE);
        rlRoot.setBackgroundColor(Color.parseColor("#E6E6E6"));
        sv.scrollTo(0, 0);
        DownloadJSON downloadJSON = new DownloadJSON();
        try {
            txtCity.setText(City);
            String result = downloadJSON.execute(url).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            DownloadIcon downloadIcon = new DownloadIcon();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list = jsonArray.getJSONObject(i);
                tempArray.add(list.getJSONObject("temp").getString("day"));
                humidityArray.add(list.getString("humidity"));
                feel_Like_Array.add(list.getJSONObject("feels_like").getString("day"));
                pressureArray.add(list.getString("pressure"));
                rainArray.add(list.getString("rain"));
                speedArray.add(list.getString("speed"));
                sunrise.add(new SimpleDateFormat("hh:mm ", Locale.ENGLISH).format(new Date(list.getLong("sunrise")* 1000)));
                sunset.add(new SimpleDateFormat("h:mm ", Locale.ENGLISH).format(new Date(list.getLong("sunset")* 1000)));
                stimeArray.add(new SimpleDateFormat("EEEE , MMM d ", Locale.ENGLISH).format(new Date(list.getLong("dt")* 1000)));
                stimeArray2.add(new SimpleDateFormat("EEEE ", Locale.ENGLISH).format(new Date(list.getLong("dt")* 1000)));
                descriptionArray.add(list.getJSONArray("weather").getJSONObject(0).getString("description"));
                sIcon.add(list.getJSONArray("weather").getJSONObject(0).getString("icon"));
                urlIcon.add("https://openweathermap.org/img/wn/"+ sIcon.get(i) +"@2x.png");

                if(i==0){
                    txtValueFeelLike.setText(list.getJSONObject("feels_like").getString("day")+"°");
                    txtValueHumidity.setText(list.getString("humidity")+"%");
                    txtTemp.setText(list.getJSONObject("temp").getString("day")+"°");
                    txtrain.setText(list.getString("rain") + "mm");
                    txtpressure.setText(list.getString("pressure")+" mBar");
                    txtSpeed.setText(list.getString("speed")+" km/h");
                }
            }
            txtTime.setText(stimeArray.get(0));
            day_1=stimeArray2.get(1);
            day_2=stimeArray2.get(2);
            day_3=stimeArray2.get(3);
            day_4=stimeArray2.get(4);
            day_5=stimeArray2.get(5);
            day_6=stimeArray2.get(6);
            cardview(day_1,day_2,day_3,day_4,day_5,day_6);
            Bitmap bitmap= downloadIcon.execute(urlIcon.get(0)).get();
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,200,true));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCity=findViewById(R.id.txtCity);
        txtTime=findViewById(R.id.txtTime);
        txtValueFeelLike=findViewById(R.id.txtValueFeelLike);
        txtValueHumidity = findViewById(R.id.txtValueHumidity);
        txtTemp = findViewById(R.id.txtValue);
        imageView = findViewById(R.id.imgIcon);
        txtrain=findViewById(R.id.txtValuerain);
        btn = findViewById(R.id.btn);
        edt = findViewById(R.id.edt);
        mainicon=findViewById(R.id.mainicon);
        rlWeather = findViewById(R.id.rlWeather);
        rlRoot=findViewById(R.id.rlRoot);
        txtpressure=findViewById(R.id.txtValuePressure);
        txtSpeed=findViewById(R.id.txtValueWind);
        recyclerView = findViewById(R.id.recycle_view);

        sv = (ScrollView) findViewById(R.id.sv_1);
//        sv.scrollTo(0, 0);
        Button nextpage = findViewById(R.id.History);
        switchCompat = findViewById(R.id.toggle_switch);

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputcity = edt.getText().toString().trim();
                btn.setEnabled(!inputcity.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    link2 = link1;
                }
                else{
                    link2="imperial";
                }
            }
        });

    }

    public void cardview(String day_1,String day_2,String day_3,String day_4,String day_5,String day_6) {
        Integer[] langlogo = {R.drawable.snowflake,R.drawable.windsock,R.drawable.satellite,R.drawable.uvindex,R.drawable.wind,R.drawable.hightemperature};

        String[] langName = {day_1,day_2,day_3,day_4,day_5,day_6};
        mainModels =new ArrayList<>();
        for (int i =0;i<langlogo.length;i++){
            MainModel model = new MainModel(langlogo[i],langName[i]);
            mainModels.add(model);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainActivity.this,LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainAdapter =new MainAdapter(mainModels,this);
        recyclerView.setAdapter(mainAdapter);
    }

    public  void extras(int k){
        Intent mIntent;
        String text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;
        text1 = tempArray.get(k).toString();
        text2 = humidityArray.get(k).toString();
        text3 = urlIcon.get(k).toString();
        text4 = speedArray.get(k).toString();
        text5 = stimeArray.get(k).toString();
        text6 = pressureArray.get(k).toString();
        text7 = rainArray.get(k).toString();
        text8=sunrise.get(k).toString();
        text9=sunset.get(k).toString();
        text10=descriptionArray.get(k).toString();
        mIntent = new Intent(MainActivity.this, information.class);
        mIntent.putExtra(EXTRA_TEXT,text1);
        mIntent.putExtra(EXTRA_TEXT_1,text2);
        mIntent.putExtra(EXTRA_TEXT_2,text3);
        mIntent.putExtra(EXTRA_TEXT_3,text4);
        mIntent.putExtra(EXTRA_TEXT_4,text5);
        mIntent.putExtra(EXTRA_TEXT_5,text6);
        mIntent.putExtra(EXTRA_TEXT_6,text7);
        mIntent.putExtra(EXTRA_TEXT_7,text8);
        mIntent.putExtra(EXTRA_TEXT_8,text9);
        mIntent.putExtra(EXTRA_TEXT_9,text10);
        startActivity(mIntent);
    }

    @Override
    public void onphoneListClick(int clickedItemIndex) {
        switch (clickedItemIndex){
            case 0: //third item in Recycler view
                extras(1);
                break;
            case 1: //third item in Recycler view
                extras(2);
                break;
            case 2: //third item in Recycler view
                extras(3);
                break;
            case 3: //third item in Recycler view
                extras(4);
                break;
            case 4: //third item in Recycler view
                extras(5);
                break;
            case 5: //third item in Recycler view
                extras(6);
                break;
        }
    }
}