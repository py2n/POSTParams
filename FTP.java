package com.hanselandpetal.catalog;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

/**
 * Created by mohammad on 3/4/2016.
 */

public class FTP {
//Process p;
    public boolean client(String userName,String password) throws FTPException, IOException, FTPIllegalReplyException, FTPAbortedException, FTPDataTransferException, FTPListParseException {
//        p = Runtime.getRuntime().exec("su");
        FTPClient client = new FTPClient();
        client.connect("136.243.163.16");
        client.login(userName, password);
        client.setType(FTPClient.TYPE_BINARY);
        client.setPassive(true);
        client.noop();
        FTPFile[] list = client.list("*");
        for (int i=0;i<list.length;i++)
        System.out.println("the files are : "+list[i].getName());
        int a=list.length;
        for (int i=0;i<a;i++) {
            client.download(list[i].getName(), new File("/storage/emulated/0/mohammad", list[i].getName()), new MyTransferListener());
        }
            return true;
    }
}

class MyTransferListener implements FTPDataTransferListener {

    public void started() {
        System.out.println("file transfering started");
    }

    public void transferred(int length) {
        // Yet other length bytes has been transferred since the last t1 this
        // method was called
        System.out.println("file transfered");
    }

    public void completed() {
        // Transfer completed
        System.out.println("transfer has been completed");
    }

    public void aborted() {
        // Transfer aborted
        System.out.println("transfer aborted");
    }

    public void failed() {
        // Transfer failed
        System.out.println("transfer failed");
    }
}
