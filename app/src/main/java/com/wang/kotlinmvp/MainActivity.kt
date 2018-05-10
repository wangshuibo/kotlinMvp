package com.wang.kotlinmvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private var ints: MutableList<Int>? = Arrays.asList(1, 2, 3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (int in this.ints!!) {

        }
    }
}
