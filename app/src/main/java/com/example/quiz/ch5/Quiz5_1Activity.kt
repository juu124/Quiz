package com.example.quiz.ch5

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.R
import com.example.quiz.databinding.ActivityQuiz51Binding

class Quiz5_1Activity : AppCompatActivity() {

    var counterService: CounterService? = null
    var num: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityQuiz51Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, CounterService::class.java)

        val connection = object : ServiceConnection {
            override fun onServiceConnected(
                name: ComponentName?,
                service: IBinder?
            ) {
                Log.d("jay", "onServiceConnected: ")
                val binder = service as CounterService.CounterBinder
                counterService = binder.getService()
                counterService?.startCounter()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }

        binding.run {
            btnStartService.setOnClickListener {
                bindService(intent, connection, BIND_AUTO_CREATE)
            }

            btnStopService.setOnClickListener {
                Log.d("jay", "btnStopService: ")
                counterService?.stopCounter()
                unbindService(connection)
            }

            btnGetCount.setOnClickListener {
                val sb = StringBuilder()
                num = counterService?.getCount() ?: 0
                sb.append("Counter: $num")
                txtCount.text = sb.toString()
            }
        }
    }
}