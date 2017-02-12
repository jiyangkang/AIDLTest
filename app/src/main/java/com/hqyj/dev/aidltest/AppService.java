package com.hqyj.dev.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AppService extends Service {
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new IAppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(
                    int anInt, long aLong,
                    boolean aBoolean, float aFloat,
                    double aDouble, String aString)
                    throws RemoteException {
            }

            @Override
            public void setData(String data)
                    throws RemoteException {
                setRealData(data);

            }

            @Override
            public void registerCallback(IRemoteServiceCallback cb)
                    throws RemoteException {
                AppService.this.callback = cb;
            }

            @Override
            public void unregisterCallback(IRemoteServiceCallback cb)
                    throws RemoteException {
                AppService.this.callback = null;
            }
        };
    }

    private IRemoteServiceCallback callback;

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("Service started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service stop");
    }

    public void setRealData(String data) {
        this.data = data;
        System.out.println("data = " + data);
        try {
            Thread.sleep(1000);
            if (callback != null) {
                callback.vlueChanged(data);
            }
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    private String data = "default date";
}
