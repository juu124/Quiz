package com.example.quiz

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.ActivityQuiz61Binding
import com.example.quiz.databinding.ItemKakaoChatBinding
import java.text.SimpleDateFormat
import java.util.Locale

class Quiz6_1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityQuiz61Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 임의의 데이터 저장 (이름, 메세지, 날짜)
        val dataList = mutableListOf<ChatInfo>()
        val myDate = SimpleDateFormat("MM월 dd일", Locale.KOREA)
        val bassDay = Calendar.getInstance()

        for (i in 1..20) {
            val calendar = Calendar.getInstance()
            calendar.time = bassDay.time
            calendar.add(Calendar.DATE, i)
            val dateStr = myDate.format(calendar.time)
            val info = ChatInfo("홍길동", "안녕하세요.", dateStr)
            dataList.add(info)
        }

        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = ChatAdapter(dataList)
        binding.rvChat.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}

data class ChatInfo(val name: String, val message: String, val day: String)

class ChatViewHolder(val binding: ItemKakaoChatBinding) : RecyclerView.ViewHolder(binding.root)

class ChatAdapter(val dataList: MutableList<ChatInfo>) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ItemKakaoChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.binding.run {
            tvUserName.text = dataList[position].name + (position + 1)
            tvMessage.text = dataList[position].message + (position + 1)
            tvDay.text = dataList[position].day
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}