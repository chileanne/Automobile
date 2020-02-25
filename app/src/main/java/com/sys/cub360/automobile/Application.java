package com.sys.cub360.automobile;

import co.paystack.android.PaystackSdk;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PaystackSdk.initialize(getApplicationContext());
    }
}
