package com.andrewkingmarshall.rxsandbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Function

@SuppressLint("CheckResult")
class MapExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_example)

        init()
    }

    private fun init() {
        map()
//        mapNoSugar()
    }

    private fun mapNoSugar() {
        Observable.just(1, 5, 10, 20)
            .map(object : Function<Int, String> {
                override fun apply(number: Int): String {
                   return "Hi there: " + number * 3
                }
            })
            .subscribe {
                Log.i("map", it.toString())
            }
    }

    private fun map() {
        Observable.just(1, 5, 10, 20)
            .map { theNumber ->
                theNumber * 3
            }
            .subscribe {
                Log.i("map", it.toString())
            }
    }


}