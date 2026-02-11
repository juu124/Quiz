package com.example.quiz

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.databinding.ActivityQuiz41Binding

class Quiz4_1Activity : AppCompatActivity() {

    // back 버튼 누른 현재 시간
    var initTime = 0L

    // 뒤로가기 콜백 선언
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // back 버튼을 누른지 3초 안지났음
                if (initTime > System.currentTimeMillis() - 3000) {
                    finish()
                } else {
                    // 뒤로가기 버튼 처음 누름 or 누른지 3초 지났음
                    Toast.makeText(this@Quiz4_1Activity, "종료하려면 한 번 더 누르세요.", Toast.LENGTH_SHORT)
                        .show()
                    initTime = System.currentTimeMillis()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityQuiz41Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뒤로가기 callback 추가
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.run {
            var pauseTime = chronometer.base - SystemClock.elapsedRealtime()

            btnStart.setOnClickListener {
                chronometer.base = SystemClock.elapsedRealtime() + pauseTime
                chronometer.start()
                btnStop.isEnabled = true
                btnReset.isEnabled = true
                btnStart.isEnabled = false
            }

            btnStop.setOnClickListener {
                pauseTime = chronometer.base - SystemClock.elapsedRealtime()
                chronometer.stop()
                btnStop.isEnabled = false
                btnReset.isEnabled = true
                btnStart.isEnabled = true
            }

            btnReset.setOnClickListener {
                pauseTime = 0L
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.stop()
                btnStart.isEnabled = true
                btnStop.isEnabled = false
                btnReset.isEnabled = false
            }
        }
    }
}