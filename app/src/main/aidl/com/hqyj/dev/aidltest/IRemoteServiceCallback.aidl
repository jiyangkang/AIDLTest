// IRemoteServiceCallback.aidl
package com.hqyj.dev.aidltest;

// Declare any non-default types here with import statements

interface IRemoteServiceCallback {
    /**
     * return from server
     */
    void vlueChanged(String value);
}
