package com.hanselandpetal.catalog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftpconnect);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditText)findViewById(R.id.editText);
        final Editable userName;
        userName=editText.getText();
        editText2 = (EditText)findViewById(R.id.editText);
        final Editable password;
        password=editText2.getText();
        pb=(ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        get=(Button)findViewById(R.id.button8);
        output=(TextView)findViewById(R.id.textView3);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hftp(userName.toString(), password.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }



    protected class FtpHandler extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            String result;
            FTP ftp =new FTP();
            try {
                ftp.client(params[0],params[1]);
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
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                editText.setVisibility(View.INVISIBLE);
                get.setVisibility(View.GONE);
                editText2.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.GONE);
                if (result.contains("error"))
                    updateDisplay("problem on connecting to service provider");
                updateDisplay(result);
            } catch (Exception e) {
                System.out.println("error occured in " + e.toString());
                updateDisplay("problem on sending the request");
            }
        }

        protected void updateDisplay(String result) {
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
                    output.setText(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void hftp(String userName,String password){
        FtpHandler ftpHandler=new FtpHandler();
        ftpHandler.execute(userName, password);
    }


}