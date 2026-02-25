package com.example.quiz.ch5

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class CounterService : Service() {

    private var counter = 0
    private var isStop = false
    private var thread: Thread? = null

    // 액티비티에서 서비스의 메서드를 호출할 수 있도록 통로 역할만 수행
    class CounterBinder : Binder() {
        fun getService(): CounterService {
            return CounterService()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return CounterBinder()
    }

    // 실제 카운트 시작 로직 (서비스의 메서드)
    fun startCounter() {
        if (thread != null && thread!!.isAlive) return // 이미 실행 중이면 중복 실행 방지

        isStop = false
        thread = Thread {
            while (!isStop) {
                try {
                    Thread.sleep(1000)
                    counter++
                    Log.d("jay", "Counter: $counter")
                } catch (e: InterruptedException) {
                    break
                }
            }
        }
        thread?.start()
    }

    fun stopCounter() {
        Log.d("jay", "stopCounter===")
        isStop = true
        thread?.interrupt()
        thread = null
    }

    fun getCount() = counter

    override fun onDestroy() {
        stopCounter()
        super.onDestroy()
    }
}