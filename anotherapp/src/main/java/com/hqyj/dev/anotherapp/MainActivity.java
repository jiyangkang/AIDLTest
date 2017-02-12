package com.hqyj.dev.anotherapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hqyj.dev.aidltest.IAppServiceRemoteBinder;
import com.hqyj.dev.aidltest.IRemoteServiceCallback;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ServiceConnection {

    private final String TAG = MainActivity.class.getSimpleName();
    private Intent intent;
    private IAppServiceRemoteBinder binder;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_set).setOnClickListener(this);

        intent = new Intent();
        intent.setComponent(new
                ComponentName("com.hqyj.dev.aidltest",
                "com.hqyj.dev.aidltest.AppService"));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                bindService(intent, this,
                        Context.BIND_AUTO_CREATE);

                break;
            case R.id.btn_set:
                if (binder != null) {
                    try {
                        binder.setData(
                                String.format("the %d times",
                                        ++count));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_stop:
                try {
                    binder.unregisterCallback(callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                unbindService(this);
                break;
        }
    }

    @Override
    public void onServiceConnected(
            ComponentName name, IBinder service) {
        binder =
                IAppServiceRemoteBinder.Stub.asInterface(service);
        Log.d(TAG, "onServiceConnected: " + 1);
        try {
            binder.registerCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private IRemoteServiceCallback.Stub callback =
            new IRemoteServiceCallback.Stub() {
                @Override
                public void
                vlueChanged(String value) throws RemoteException {
                    Log.e(TAG, "vlueChanged: " + value);
                }
            };
}
