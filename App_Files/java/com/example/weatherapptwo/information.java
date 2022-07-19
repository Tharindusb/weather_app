package com.example.weatherapptwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class information extends AppCompatActivity {
    TextView txtdescription,txtValueday_1,day_1_wind,day_1_humidity,Day,txtrain,txtpressure1,txtsunset,txtsunrise;
    ImageView imageViewday1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        MainActivity.DownloadIcon downloadIcon = new MainActivity.DownloadIcon();
        txtdescription =(TextView) findViewById(R.id.txtdescription);
        txtValueday_1 =(TextView) findViewById(R.id.txtValueday_1);
        day_1_wind =(TextView) findViewById(R.id.day_1_wind);
        day_1_humidity =(TextView) findViewById(R.id.day_1_humidity);
        imageViewday1 = (ImageView) findViewById(R.id.imgday1);
        Day = (TextView) findViewById(R.id.date_1);
        txtrain = (TextView) findViewById(R.id.day_1_rain);
        txtpressure1 = (TextView) findViewById(R.id.day_1_pressure);
        txtsunrise=(TextView) findViewById(R.id.txtsunrise);
        txtsunset=(TextView) findViewById(R.id.txtsunset);
        txtdescription= (TextView) findViewById(R.id.txtdescription);


        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        String text1 = intent.getStringExtra(MainActivity.EXTRA_TEXT_1);
        String text2 = intent.getStringExtra(MainActivity.EXTRA_TEXT_2);
        String text3 = intent.getStringExtra(MainActivity.EXTRA_TEXT_3);
        String text4 = intent.getStringExtra(MainActivity.EXTRA_TEXT_4);
        String text5 = intent.getStringExtra(MainActivity.EXTRA_TEXT_5);
        String text6 = intent.getStringExtra(MainActivity.EXTRA_TEXT_6);
        String text7 = intent.getStringExtra(MainActivity.EXTRA_TEXT_7);
        String text8 = intent.getStringExtra(MainActivity.EXTRA_TEXT_8);
        String text9 = intent.getStringExtra(MainActivity.EXTRA_TEXT_9);

        Bitmap bitmap1 = null;
        try {
            bitmap1 = downloadIcon.execute(text2).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        txtValueday_1.setText(text+"°");
        day_1_humidity.setText(text1+" %");
        imageViewday1.setImageBitmap(bitmap1);
        day_1_wind.setText(text3+" km/h");
        txtrain.setText(text6+" mm");
        Day.setText(text4);
        txtpressure1.setText(text5+" mBar");
        txtsunrise.setText(text8+" AM");
        txtsunset.setText(text7+" PM");
        txtdescription.setText(text9);

    }
}