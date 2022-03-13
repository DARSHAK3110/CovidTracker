package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.api.ApiUtilities;
import com.example.covidtracker.api.Countries;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private TextView totalConfirmed,totalDeath,totalRecovered,active,test;
    private TextView todayDeath, update, todayRecovered, todayConfirmed;
    private PieChart pieChart;
   private List<Countries> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ApiUtilities.getApiInterface().getCountries().enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                list.addAll(response.body());
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getCountry().equals("India")){
                        int confirmed = Integer.parseInt(list.get(i).getCases());
                        int todayConfirm = Integer.parseInt(list.get(i).getTodayCases());
                        int death = Integer.parseInt(list.get(i).getDeaths());
                        int todayDeaths = Integer.parseInt(list.get(i).getTodayDeaths());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int todayRecover = Integer.parseInt(list.get(i).getTodayRecovered());
                        int tests = Integer.parseInt(list.get(i).getTests());
                        int Active = Integer.parseInt(list.get(i).getActive());
                        setText(list.get(i).getUpdated());
                        totalConfirmed.setText(NumberFormat.getInstance().format(confirmed));
                        totalDeath.setText(NumberFormat.getInstance().format(death));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        todayConfirmed.setText(NumberFormat.getInstance().format(todayConfirm));
                        todayRecovered.setText(NumberFormat.getInstance().format(todayRecover));
                        todayDeath.setText(NumberFormat.getInstance().format(todayDeaths));
                        test.setText(NumberFormat.getInstance().format(tests));
                        pieChart.addPieSlice(new PieModel("Confirm",confirmed, getResources().getColor(R.color.yellow)));
                        active.setText(NumberFormat.getInstance().format(Active));
                        pieChart.addPieSlice(new PieModel("Recover",recovered,getResources().getColor(R.color.blue)));
                        pieChart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red)));
                        pieChart.addPieSlice(new PieModel("Tests",tests,getResources().getColor(R.color.purple)));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText(String updated) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
        long millisec = Long.parseLong(updated);

        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(millisec);
        update.setText("Update At: " + format.format(calender.getTime()));
    }

    private void init(){
            totalConfirmed = findViewById(R.id.tv_confirmed);
            pieChart = findViewById(R.id.piechart);
            totalDeath = findViewById(R.id.tv_death);
            totalRecovered = findViewById(R.id.tv_recovered);
            active = findViewById(R.id.tv_active);
            test = findViewById(R.id.tv_test);
            todayConfirmed = findViewById(R.id.tv_today_confirmed);
            todayRecovered = findViewById(R.id.tv_today_recovered);
            todayDeath = findViewById(R.id.tv_today_death);
            update = findViewById(R.id.update);
        }
    } 
    