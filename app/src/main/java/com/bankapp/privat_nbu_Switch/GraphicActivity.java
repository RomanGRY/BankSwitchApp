package com.bankapp.privat_nbu_Switch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphicActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private GraphView graphView;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        graphView=findViewById(R.id.graph);
        backBtn=findViewById(R.id.back_btn);
        series=new LineGraphSeries<>(getPoints());
        graphView.addSeries(series);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1995);
        graphView.getViewport().setMaxX(2022);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(1);
        graphView.getViewport().setMaxY(32);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return "year\n"+ super.formatLabel(value, isValueX);
                }
                else {
                    return "UAH\n"+ super.formatLabel(value, isValueX);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GraphicActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private DataPoint[] getPoints() {
        DataPoint[] dataPoints=new DataPoint[]{
                new DataPoint(1996,1.799),
                new DataPoint(1997,1.893),
                new DataPoint(1998,1.901),
                new DataPoint(1999,3.427),
                new DataPoint(2000,5.2194),
                new DataPoint(2001,5.434),
                new DataPoint(2002,5.3114),
                new DataPoint(2003,5.3326),
                new DataPoint(2004,5.3312),
                new DataPoint(2005,5.305),
                new DataPoint(2006,5.05),
                new DataPoint(2007,5.05),
                new DataPoint(2008,5.05),
                new DataPoint(2009,7.7),
                new DataPoint(2010,7.985),
                new DataPoint(2011,7.959),
                new DataPoint(2012,7.9898),
                new DataPoint(2013,7.993),
                new DataPoint(2014,7.993),
                new DataPoint(2015,12.614845),
                new DataPoint(2016,23.783691),
                new DataPoint(2017,27.020929),
                new DataPoint(2018,28.203244),
                new DataPoint(2019,28.122133),
                new DataPoint(2020,24.119),
                new DataPoint(2021,28.4028),
        };
        return dataPoints;
    }
}