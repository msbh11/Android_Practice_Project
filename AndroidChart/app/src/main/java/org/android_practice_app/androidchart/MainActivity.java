package org.android_practice_app.androidchart;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.w3c.dom.Entity;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    YAxis left;
    XAxis right;
    private LineChart mChart;
    EditText valueEt;
    Button btnShow;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChart = (LineChart) findViewById(R.id.mChart);

        btnShow = findViewById(R.id.show);
        valueEt = findViewById(R.id.valueEt);

    }





    public void setData(int count ) {


        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) Math.sin((double) 180 * i / 3.1416)+2;
            yVals.add(new Entry(i, val));

        }
        LineDataSet set1;
        set1 = new LineDataSet(yVals, "Dataset 1");
        set1.setCubicIntensity(.1f);
        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set1.setDrawFilled(true);
        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setDrawCircles(false);
        set1.setCircleColor(Color.RED);
        set1.setDrawValues(false);

        LineData data = new LineData(set1);

        mChart.setData(data);
        mChart.invalidate();

        left = mChart.getAxisLeft();
        left.setDrawLabels(false);
        left.setDrawLabels(true);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true);
        left.setTextSize(20f);
        left.setTextColor(Color.BLACK);

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1


    }

    public void showChart(View view) {
        count = Integer.parseInt(""+valueEt.getText().toString().trim());
        if (count!=0){

            setData(count);
        }
    }


}
