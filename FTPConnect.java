package com.hanselandpetal.catalog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

public class FTPConnect extends Activity {
    ProgressBar pb;
    TextView output;
    boolean flag;
    EditText editText2;
    EditText editText;
    Button get;
    List<MyTask> tasks;
    String [] result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftpconnect);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditText)findViewById(R.id.editText);
        final Editable userName;
        userName=editText.getText();
        editText2 = (EditText)findViewById(R.id.editText2);
        final Editable password;
        password=editText2.getText();
        pb=(ProgressBar)findViewById(R.id.progressBar2);
        pb.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        get=(Button)findViewById(R.id.button8);
        output=(TextView)findViewById(R.id.textView3);
        output.setMovementMethod(new ScrollingMovementMethod());
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAddresses();
                    for(int i=0;i<result.length;i++)
                        hftp(userName.toString(), password.toString(),result[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getAddresses(){
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://192.168.43.53:8000/rcp");
        p.setParam("username", "mohammad");
        MyTask task=new MyTask();
        task.execute(p);
    }

    public void updateDisplay(String result) {
        try {
            if (result.contains("congratulation"))
                output.setText("Congratulation new mac address added to database" + "\n");
            else if (result.contains("been") && flag == true)
                output.setText("درخواست روشن شدن با موفقیت ثبت شد \n");
            else if (result.contains("been") && flag == false)
                output.setText("درخواست خاموش شدن با موفقیت ثبت شد \n");
            else if (result.contains("error"))
                output.setText("در حال حاضر امکان برقراری ارتباط با\n مرکز سرویس دهی موجود نمی باشد \n");
            if (result.contains("new version"))
                output.setText("لطفا نرم افزار خود را بروز رسانی کنید");
            else
                output.append(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hftp(String userName, String password, String address){
        FtpHandler ftpHandler=new FtpHandler();
        ftpHandler.execute(userName, password,address);
    }

    protected class FtpHandler extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            String result = null;
            FTP ftp =new FTP();
            try {
                ftp.client(params[0], params[1],params[2]);
                result="congratulation";
            } catch (FTPException e) {
                result=e.toString();
            } catch (IOException e) {
                result=e.toString();
            } catch (FTPIllegalReplyException e) {
                result=e.toString();
            } catch (FTPAbortedException e) {
                result=e.toString();
            } catch (FTPDataTransferException e) {
                result=e.toString();
            } catch (FTPListParseException e) {
                result=e.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                editText.setVisibility(View.INVISIBLE);
                get.setVisibility(View.INVISIBLE);
                editText2.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.INVISIBLE);
                if (result.contains("error"))
                    updateDisplay("problem on connecting to service provider");
                updateDisplay(result);
            } catch (Exception e) {
                System.out.println("error occured in " + e.toString());
                updateDisplay("problem on sending the request");
            }
        }


    }


    protected class MyTask extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected void onPreExecute() {
//            if (tasks.size() == 0) {
//                pb.setVisibility(View.VISIBLE);
//            }
//            tasks.add(this);
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            int begining=content.indexOf("body")+5;
            int end=content.lastIndexOf("body")-2;
            content=content.substring(begining, end);
            content=content.replace("[", "");
            content=content.replace("]","");
            content=content.replace("&#39;","");
            content=content.replace("'","");
            result=content.split(",");
            return result[0];
        }

        @Override
        protected void onPostExecute(String result) {

            try {
//                tasks.remove(this);
//                if (tasks.size() == 0) {
////                    pb.setVisibility(View.INVISIBLE);
//                }
//                for (int i=0;i<result.length();i++)
//                    updateDisplay(result+"");
                if (result.contains("error"))
                    updateDisplay("problem on connecting to service provider");
//                updateDisplay(result);
            } catch (Exception e) {
                System.out.println("error occured in " + e.toString());
                updateDisplay("problem on sending the request");
            }
        }
    }



}
