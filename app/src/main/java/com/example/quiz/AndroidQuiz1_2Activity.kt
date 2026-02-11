package com.example.quiz

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.ActivityAndroidQuiz12Binding
import com.example.quiz.databinding.ItemMemoBinding
import com.example.quiz.db.DBHelper

class AndroidQuiz1_2Activity : AppCompatActivity() {
    lateinit var binding: ActivityAndroidQuiz12Binding
    var dataList = mutableListOf<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAndroidQuiz12Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, imeInsets.bottom)
            insets
        }

        dataList = selectData(this) // 초기 데이터 로드
        val memoAdapter = MemoAdapter(dataList) // 어댑터 생성

        binding.inputRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inputRecyclerView.adapter = memoAdapter

        binding.btnTodoAdd.setOnClickListener {
            val content = binding.inputTodo.text.toString()
            if (!content.isEmpty()) {
                val values = ContentValues().apply {
                    put("content", binding.inputTodo.text.toString())
                }
                val newId = insertData(this, values)
                dataList.add(Memo(newId.toInt(), content))

                binding.inputTodo.text.clear()
                binding.inputRecyclerView.adapter?.notifyItemInserted(dataList.size - 1)
            }
        }
    }
}

class MemoViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

class MemoAdapter(val dataList: MutableList<Memo>) : RecyclerView.Adapter<MemoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemoViewHolder {
        return MemoViewHolder(
            ItemMemoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.binding.run {
            itemNum.text = (position + 1).toString()
            tvInput.text = dataList[position].content

            btnDelete.setOnClickListener {
                val currentPosition = holder.bindingAdapterPosition
                deleteData(holder.itemView.context, dataList[currentPosition].id) // DB에 반영
                dataList.removeAt(currentPosition)     // 리스트 삭제

                notifyItemRemoved(currentPosition)     // remove된 리스트 업데이트
                notifyItemRangeChanged(currentPosition, dataList.size) // remove된 만큼 리스트 당기기
            }
        }
    }

    override fun getItemCount() = dataList.size
}

fun insertData(context: Context, values: ContentValues): Long {
    val db = DBHelper(context).writableDatabase
    val newId = db.insert("tb_memo", null, values)
    db.close()
    return newId
}

fun selectData(context: Context): MutableList<Memo> {
    val dataList = mutableListOf<Memo>()
    val db = DBHelper(context).readableDatabase
    val cursor = db.rawQuery("SELECT * FROM tb_memo", null)
    while (cursor.moveToNext()) {
        val id = cursor.getInt(0)
        val content = cursor.getString(1)
        val memoData = Memo(id, content)
        Log.d("jay", "data : $memoData")
        dataList.add(memoData)
    }
    cursor.close()
    return dataList
}

fun deleteData(context: Context, id: Int) {
    val db = DBHelper(context).writableDatabase
    db.delete("tb_memo", "_id = ?", arrayOf(id.toString()))
    db.close()
}
