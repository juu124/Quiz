package com.example.quiz

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)

        binding.run {
            if (sharedPreferences.getBoolean("saveId", false)) {
                loginEtId.setText(sharedPreferences.getString("userId", ""))
                checkLoginRemem.isChecked = true
            }

            checkLoginRemem.setOnCheckedChangeListener { _, isChecked ->
                binding.loginEtPw.isEnabled = isChecked
            }

            loginBtn.setOnClickListener {
                if (binding.checkLoginRemem.isChecked) {
                    sharedPreferences.edit {
                        putString("userId", binding.loginEtId.text.toString())
                        putBoolean("saveId", binding.checkLoginRemem.isChecked)
                    }
                    Toast.makeText(this@LoginActivity, "아이디 저장", Toast.LENGTH_SHORT).show()
                } else {
                    sharedPreferences.edit {
                        remove("userId")
                        remove("saveId")
                    }
                    Toast.makeText(this@LoginActivity, "저장된 아이디 삭제", Toast.LENGTH_SHORT).show()
                    loginEtId.text.clear()
                    loginEtPw.text.clear()
                }
            }
        }
    }
}