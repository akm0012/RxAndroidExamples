package com.andrewkingmarshall.rxsandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var counter1 = 0
    var counter2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initLayout()
    }

    private fun initLayout() {
//        sugar()
        noSugar()
    }

    private fun noSugar() {

        val emitter = PublishSubject.create<View>()
        clickMeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                emitter.onNext(v)
            }
        })

        emitter
            .map {
                incrementCounter1()
            }
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                incrementCounter2()
            }
    }

    private fun sugar() {
        RxView.clicks(clickMeButton)
            .map {
                incrementCounter1()
            }
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                incrementCounter2()
            }
    }

    private fun incrementCounter1() {
        counter1++
        beforeThrottleTextView.text = "Before Throttle: $counter1"
    }

    private fun incrementCounter2() {
        counter2++
        afterThrottleTextView.text = "After Throttle: $counter2"
    }


}