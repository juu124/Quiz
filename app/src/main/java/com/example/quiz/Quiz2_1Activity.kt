package com.example.quiz

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.databinding.ActivityQuiz21Binding

class Quiz2_1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityQuiz21Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.run {
            checkAgree.setOnCheckedChangeListener { buttonView, isChecked ->
                btnJoin.isEnabled = isChecked
            }

            btnJoin.setOnClickListener {
                if (checkAgree.isChecked)
                    if (binding.etName.text.isEmpty()) {
                        showToast("name invalid")
                        return@setOnClickListener   // break 배울때 사용했던 라벨 사용. 해당 listener 문을 나가게 해준다.
                    }

                val email = binding.etEmail.text.toString()
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showToast("email invalid")
                    return@setOnClickListener
                }

                // 출력 Log로 표시
                printLog(binding)

                // 가입하기 버튼 활성화
                btnJoin.isEnabled = true

                // 입력창 초기화
                initState(binding)

                // hof도 사용했는데 효과적이지 않아서 주석처리
                // 이름 empty check
//                checkEmptyEditText(binding.etName, "이름을 입력해주세요") {
//                    // 이메일 empty check
//                    checkEmptyEditText(binding.etEmail, "이메일을 입력해주세요") {
//                        // 이메일 입력 형식 체크
//                        checkInputFormat(
//                            binding.etEmail,
//                            Patterns.EMAIL_ADDRESS,
//                            "이메일 형식이 올바르지 않습니다."
//                        ) {
//                            // 출력 Log로 표시
//                            printLog(binding)
//
//                            // 가입하기 버튼 활성화
//                            btnJoin.isEnabled = true
//
//                            // 입력창 초기화
//                            initState(binding)
//                        }
//                    }
//                }
            }
        }
    }

    // 토스트 노출
    fun showToast(message: String) {
        Toast.makeText(this@Quiz2_1Activity, message, Toast.LENGTH_SHORT).show()
    }

    // 입력창 초기화
    fun initState(binding: ActivityQuiz21Binding) {
        binding.run {
            etName.text.clear()
            etEmail.text.clear()
            rbMale.isChecked = true
            checkAgree.isChecked = false
        }
    }

    // 출력 Log로 표시
    fun printLog(binding: ActivityQuiz21Binding) {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val gender = if (binding.rbMale.isChecked) "남성" else "여성"
        val result = "이름 : $name, 이메일 : $email, 성별 : $gender"
        Log.d("Quiz2_1Activity", result)
    }


    // hof도 사용했는데 효과적이지 않아서 주석처리
//    fun checkEmptyEditText(editText: EditText, errorMessage: String, onSuccess: () -> Unit) {
//        val result = editText.text.toString()
//        if (result.isEmpty()) {
//            showToast(errorMessage)
//        } else {
//            onSuccess()
//        }
//    }
//
//    fun checkInputFormat(
//        editText: EditText,
//        patterns: Pattern,
//        errorMessage: String,
//        onSuccess: () -> Unit
//    ) {
//        val result = editText.text.toString()
//        if (!patterns.matcher(result).matches()) {
//            showToast(errorMessage)
//        } else {
//            onSuccess()
//        }
//    }
}