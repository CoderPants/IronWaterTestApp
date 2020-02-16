package com.ironwater.testapp

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        /*val inputStream = resources.openRawResource(R.raw.products)

        CoroutineScope(Dispatchers.IO).launch {
            val test = JSONHelper.getDataFromFile(inputStream)

            Log.d(Constatnts.LOG_TAG, "Data from json: $test")
        }*/
    }
}