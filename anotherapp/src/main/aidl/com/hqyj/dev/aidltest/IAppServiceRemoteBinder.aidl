// IAppServiceRemoteBinder.aidl
package com.hqyj.dev.aidltest;

// Declare any non-default types here with import statements
import com.hqyj.dev.aidltest.IRemoteServiceCallback;
interface IAppServiceRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setData(String data);
    void registerCallback(IRemoteServiceCallback cb);
    void unregisterCallback(IRemoteServiceCallback cb);
}
