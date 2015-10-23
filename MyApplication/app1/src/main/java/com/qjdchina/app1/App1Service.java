package com.qjdchina.app1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class App1Service extends Service {
    public App1Service() {
    }

    private String data = "默认数据";
    private boolean running = false;

    @Override
    public IBinder onBind(Intent intent) {
        return new IAppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {
                App1Service.this.data = data;
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service start");
        new Thread() {
            @Override
            public void run() {
                super.run();
                running = true;
                while (running) {
                    System.out.println(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        System.out.println("Service Destroy");
    }
}
