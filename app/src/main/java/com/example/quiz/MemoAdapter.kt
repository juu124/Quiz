package com.example.quiz

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.DTO.TodoData
import com.example.quiz.databinding.ItemMemoBinding

class MemoViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

class MemoAdapter(val context: Context, val itemList: MutableList<TodoData>, val onItemClick:(TodoData) -> Unit) :
    RecyclerView.Adapter<MemoViewHolder>() {
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
        val data = itemList.get(position)

        holder.binding.run {
            itemNum.text = (position + 1).toString()
            tvInput.text = data.todo

            btnDelete.setOnClickListener {
                deleteData(data.docId)
                val currentPosition = holder.bindingAdapterPosition
                itemList.removeAt(currentPosition)     // 리스트 삭제
                notifyItemRemoved(currentPosition)     // remove된 리스트 업데이트
                notifyItemRangeChanged(currentPosition, itemList.size) // remove된 만큼 리스트 당기기
            }

            root.setOnClickListener {
                Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show()
                // todo :: MainActivitiy의 edittext 창에 해당 item의 todo.text내용을 수정할 수 있다.
                onItemClick(data)
            }
        }
    }

    override fun getItemCount() = itemList.size

    fun deleteData(documentId: String?) {
        MyApplication.db.collection("todo")
            .document(documentId ?: "")
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show()
            }
    }
}

