package com.andrewkingmarshall.rxsandbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Function

@SuppressLint("CheckResult")
class FilterExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_example)

        init()
    }

    private fun init() {
        mapAndFilter()
    }
    private fun mapAndFilter() {
        Observable.just(1, 5, 10, 20)
            .map { theNumber ->
                theNumber * 3
            }
            .filter {
                it % 2 == 0
            }
            .subscribe {
                Log.i("map", it.toString())
            }
    }


}