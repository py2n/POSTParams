package com.hanselandpetal.catalog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class getRequest extends FragmentActivity
        implements TimePickerDialogFragment.TimePickerDialogHandler, RadialTimePickerDialogFragment.OnTimeSetListener {
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    public String t1;
    boolean flag = true;
    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

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
    }


    protected void updateDisplay(String result) {
        try {
            if (result.contains("Congratulation"))
                output.setText("Congratulation new mac address added to database" + "\n");
            else if (result.contains("been") && flag == true)
                output.setText("درخواست روشن شدن با موفقیت ثبت شد \n");
            else if (result.contains("been") && flag == false)
                output.setText("درخواست خاموش شدن با موفقیت ثبت شد \n");
            else if (result.contains("error"))
                output.setText("در حال حاضر امکان برقراری ارتباط با\n مرکز سرویس دهی موجود نمی باشد \n");
            if (result.contains("new version"))
                output.setText("لطفا نرم افزار خود را بروز رسانی کنید");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
//        updateDisplay(getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute)));
        TextView output2=(TextView)findViewById(R.id.textView2);
        t1=getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute));
        output2.setText(getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute)));
    }



    public void getTime2() {
        Button b6;
        b6 = (Button) findViewById(R.id.button6);
        b6.setText(R.string.time_picker_set);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(getRequest.this)
                        .setForced24hFormat().setThemeDark();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        String h,m;
        if (hourOfDay<10)
            h="0"+hourOfDay;
        else
            h=hourOfDay+"";
        if (minute<10)
            m="0"+minute;
        else
            m=minute+"";
        t1=h+":"+m;
        TextView output2=(TextView)findViewById(R.id.textView2);
        output2.setText(t1);
    }

    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }


    protected void requestData(final String req) {
        getTime2();
        flag = true;
        output = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar1);


        Button bOn = (Button) findViewById(R.id.button4);
        bOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TimePickerBuilder tpb = new TimePickerBuilder()
//                        .setFragmentManager(getSupportFragmentManager())
//                        .setStyleResId(R.style.MyCustomBetterPickerTheme);
//                tpb.show();
//                tpb.setReference(1);
                pb.setVisibility(View.INVISIBLE);
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.43.53:8000/poem");
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
                if (result.contains("error"))
                    updateDisplay("problem on connecting to service provider");
                updateDisplay(result);
            } catch (Exception e) {
                System.out.println("error occured in " + e.toString());
                updateDisplay("problem on sending the request");
            }
        }
    }


}



