package com.hanselandpetal.catalog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import picker.ugurtekbas.com.Picker.Picker;
import picker.ugurtekbas.com.Picker.TimeChangedListener;


public class getRequest extends FragmentActivity
        implements TimeChangedListener {
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    public String t1;
    boolean flag = true;
    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    Context context;
    Picker picker;
    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_request);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        tasks = new ArrayList<>();
        Bundle data=getIntent().getExtras();
        String request;
        request = data.getString("request");
        requestData(request);
        pb.setVisibility(View.INVISIBLE);

        picker = (Picker) findViewById(R.id.picker);
        picker.setCanvasColor(Color.TRANSPARENT);
        picker.setDialColor(Color.parseColor("#f4b400"));
        picker.setClockColor(Color.parseColor("#9933cc"));
        picker.setTextColor(Color.parseColor("#f4b400"));
        picker.setHourFormat(true);
        picker.getCurrentHour();
        picker.getCurrentMin();
        picker.setTimeChangedListener(this);
    }

    public void crouton(String a){

        Configuration croutonConfiguration = new Configuration.Builder()
                .setDuration(2500).build();
        Style InfoStyle = new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#0099cc"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();



        Style AlertStyle = new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#cc0000"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();


        Style ConfirmStyle = new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#FF00FF0D"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();



        if (a.contains("Congratulation")) {
            output.setText("Congratulation new mac address added to database" + "\n");
        }
        else if (a.contains("NullPointer"))
            Crouton.makeText(this,"لطفا زمان را تنظیم کنید",AlertStyle).show();
        else if (a.contains("been") && flag == true)
        Crouton.makeText(this,"درخواست روشن شدن با موفقیت ثبت شد ",ConfirmStyle).show();
        else if (a.contains("been") && flag == false)
            Crouton.makeText(this,"درخواست خاموش شدن با موفقیت ثبت شد ",ConfirmStyle).show();
        else if (a.contains("error"))
            Crouton.makeText(this,"در حال حاضر امکان برقراری ارتباط با\n مرکز سرویس دهی موجود نمی باشد \n",AlertStyle).show();

//            Crouton.clearCroutonsForActivity(this);
//        if (a.contains("error"))
//            Crouton.makeText(this,a, AlertStyle).show();
//        else if (a.contains("been"))
//            Crouton.makeText(this,a,ConfirmStyle).show();
//        else if (a.contains("خطا"))
//            Crouton.makeText(this,a,AlertStyle).show();
//        else
//            Crouton.makeText(this,a,InfoStyle).show();

    }


    protected void updateDisplay(String result) {
        try {
            if (result.contains("Congratulation")) {
                output.setText("Congratulation new mac address added to database" + "\n");
            }
            else if (result.contains("been") && flag == true)
                output.setText("درخواست روشن شدن با موفقیت ثبت شد \n");
            else if (result.contains("been") && flag == false)
                output.setText("درخواست خاموش شدن با موفقیت ثبت شد \n");
            else if (result.contains("error")) {
                crouton("خطا در ارسال اطلاعات");
                output.setText("در حال حاضر امکان برقراری ارتباط با\n مرکز سرویس دهی موجود نمی باشد \n");
//                output.setText(result);
            }
            if (result.contains("new version")){
                output.setText("لطفا نرم افزار خود را بروز رسانی کنید");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        // Workaround until there's a way to detach the Activity from Crouton while
        // there are still some in the Queue.
        Crouton.clearCroutonsForActivity(this);
        super.onDestroy();
    }

//    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
//        updateDisplay(getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute)));
//        TextView output2=(TextView)findViewById(R.id.textView2);
//        t1=getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute));
//        output2.setText(getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute)));
//    }


    protected void requestData(final String req) {
        flag = true;
        output = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar1);


        Button bOn = (Button) findViewById(R.id.button4);
        bOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.INVISIBLE);
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://136.243.163.16:8000/poem");
                p.setParam("noun1", "00:00:00:00:00:45");
                p.setParam("noun2", req);
                p.setParam("noun3", "on");
                p.setParam("noun4", t1);
                p.setParam("noun5", "2.0.0");
                p.setParam("noun6", "00:00:00:54:56:45");
                flag = true;
                MyTask task = new MyTask();
                task.execute(p);
            }
        });
        Button bOff = (Button) findViewById(R.id.button5);
        bOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.INVISIBLE);
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://136.243.163.16:8000/poem");
                p.setParam("noun1", "00:00:00:00:00:45");
                p.setParam("noun2", req);
                p.setParam("noun3", "off");
                p.setParam("noun4", t1);
                p.setParam("noun5", "2.0.0");
                p.setParam("noun6", "00:00:00:54:56:45");
                flag = false;
                MyTask task = new MyTask();
                task.execute(p);
            }
        });
        Crouton.clearCroutonsForActivity(this);
    }

    @Override
    public void timeChanged(Date date) {
        String h, m;
        int hourOfDay = date.getHours();
        int minute = date.getMinutes();
        if (date.getHours() < 10)
            h = "0" + hourOfDay;
        else
            h = hourOfDay + "";
        if (date.getMinutes() < 10)
            m = "0" + minute;
        else
            m = minute + "";
        t1 = h + ":" + m;
    }


    protected class MyTask extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                tasks.remove(this);
                if (tasks.size() == 0) {
                    pb.setVisibility(View.INVISIBLE);
                }
                if (result.contains("error")) {
//                    updateDisplay("problem on connecting to service provider");
//                        Crouton.makeText(this,"error in :",Style.ALERT);
                }
//                updateDisplay(result);
                crouton(result);
            } catch (Exception e) {
                System.out.println("error occured in " + e.toString());
                updateDisplay("problem on sending the request");
            }
        }
    }

}