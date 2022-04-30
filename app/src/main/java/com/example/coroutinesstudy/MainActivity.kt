package com.example.coroutinesstudy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.coroutineScope.launch {
            // CALL A TEST FUN TO VERIFY ITS BEHAVIOR HERE
        }
    }

    private suspend fun testCoroutinesSuspension() {
        Log.i(TAG, "testCoroutinesSuspension: DOING SOMETHING LONG")
        longFunction(10_000)
        Log.i(TAG, "testCoroutinesSuspension: SOMETHING LONG DONE")
    }

    private suspend fun longFunction(time: Long) {
        Log.i(TAG, "longFunction: ON longFunction")
        delay(time)
        Log.i(TAG, "longFunction: EXITING longFunction")
    }

    private suspend fun testCoroutinesCancellation() {
        setUpButton()

        val job = lifecycle.coroutineScope.launch {
            var counter = 1

            while (true) {
                delay(1_000)
                Log.i(TAG, "testCoroutinesCancellation: spent time $counter")
                counter++
            }
        }

        lifecycle.coroutineScope.launch {
            Log.e(TAG, "testCoroutinesCancellation: WAITING 11sec TO CANCEL JOB")
            delay(11_000)
            job.cancel()
            Log.e(TAG, "testCoroutinesCancellation: JOB CANCELLED")
        }
    }

    private fun setUpButton() =
        findViewById<Button>(R.id.button).apply {
            visibility = View.VISIBLE

            setOnClickListener {
                val intent = Intent(this@MainActivity, AnyActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
}