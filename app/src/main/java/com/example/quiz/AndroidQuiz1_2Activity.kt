package com.example.quiz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.DTO.TodoData
import com.example.quiz.databinding.ActivityAndroidQuiz12Binding
import java.text.SimpleDateFormat
import java.util.Date

class AndroidQuiz1_2Activity : AppCompatActivity() {
    lateinit var binding: ActivityAndroidQuiz12Binding
    val itemList = mutableListOf<TodoData>()

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
        makeRecyclerView()

        binding.btnTodoAdd.setOnClickListener {
            val content = binding.inputTodo.text.toString()
            if (!content.isEmpty()) {
                saveStore()
                binding.inputTodo.text.clear()
//                binding.inputRecyclerView.adapter?.notifyItemInserted(dataList.size - 1)
            }
        }
    }

    // add 버튼 클릭시 해당 text DB에 저장
    private fun saveStore() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val data = mapOf(
            "date" to format.format(Date()),
            "todo" to binding.inputTodo.text.toString()
        )
        Log.d("jay", "data: ${data["todo"]}")

        MyApplication.db.collection("todo")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "todo가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                binding.inputRecyclerView.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "data save error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun makeRecyclerView() {
        // read 하고자 하는 collection 먼저 선택
        MyApplication.db.collection("todo")
            .get() // 모든 데이터..
            .addOnSuccessListener {
                // 획득 데이터 List
                for (document in it) {
                    Log.d("jay", "${document.id}")
                    // 하나의 document 를 개발자가 지칭하는 객체를 생성해서 담아달다
                    val item = document.toObject(TodoData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }

                binding.inputRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.inputRecyclerView.adapter =
                    MemoAdapter(this, itemList) { modifyData ->
                        binding.inputTodo.setText(modifyData.todo)
                        binding.btnTodoAdd.text = "수정"
                        binding.btnTodoAdd.setOnClickListener {
                            val data = mapOf(
                                "todo" to binding.inputTodo.text.toString()
                            )
                            MyApplication.db.collection("todo")
                                .document(modifyData.docId ?: "")
                                .update(data)
                                .addOnSuccessListener {
                                    val index = itemList.indexOfFirst { it.docId == modifyData.docId }
                                    if (index != -1) {
                                        itemList[index].todo = modifyData.todo
                                        binding.inputRecyclerView.adapter?.notifyItemChanged(index)
                                    }
                                }
                                .addOnFailureListener {

                                }
                        }
                    }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
