package com.example.quiz.ch8

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quiz.R
import com.example.quiz.databinding.ActivityTest8Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test8Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 네트워킹...
        val networkService = (applicationContext as MyApplication).networkService
        val call = networkService.getCatList(20,1)
        call.enqueue(object: Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>?>,




                response: Response<List<Cat>?>
            ) {
                Log.d("jay", "onResponse: ")
                val catList = response.body()
                Log.d("jay", "onResponse catList: $catList")
                binding.recyclerView.adapter = MyAdapter(this@Test8Activity, catList)
                binding.recyclerView.layoutManager = GridLayoutManager(this@Test8Activity, 2)
            }

            override fun onFailure(
                call: Call<List<Cat>?>,
                t: Throwable
            ) {
                Log.d("jay", "onFailure: ")
            }
        })
    }
}