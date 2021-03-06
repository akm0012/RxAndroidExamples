package com.andrewkingmarshall.rxsandbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_click_delay.*
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class ClickDelayActivity : AppCompatActivity() {

    var counter1 = 0
    var counter2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_delay)

        init()
    }

    private fun init() {
        initLayout()
    }

    private fun initLayout() {
        sugar()
//        noSugar()
    }

    private fun noSugar() {

        val emitter = PublishSubject.create<View>()
        clickMeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                emitter.onNext(v)
            }
        })

        val consumer = object: Consumer<View> {
            override fun accept(t: View?) {
                incrementCounter2()
            }
        }

//        val observer = object : Observer<View> {
//            override fun onSubscribe(d: Disposable) {
//            }
//
//            override fun onNext(t: View) {
//                incrementCounter2()
//            }
//
//            override fun onError(e: Throwable) {
//            }
//
//            override fun onComplete() {
//            }
//        }

        emitter
            .map(object: Function<View, View> {
                override fun apply(v: View): View {
                    incrementCounter1()
                    return v
                }
            })
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
//            .subscribe(observer)
            .subscribe(consumer)
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