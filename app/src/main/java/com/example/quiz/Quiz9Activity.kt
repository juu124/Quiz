package com.example.quiz

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.databinding.ActivityQuiz9Binding

class Quiz9Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityQuiz9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission(), {
            if (it) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-129"))
                startActivity(intent)
            } else {
                Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=제주신라호텔"))
            startActivity(intent)
        }

        binding.tvPhoneNumber.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.CALL_PHONE"
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-129"))
                startActivity(intent)
            } else {
                launcher.launch("android.permission.CALL_PHONE")
            }
        }

        binding.btnReservation.setOnClickListener {
            val intent = Intent(this, QuizSub9Activity::class.java)
            startActivity(intent)
        }
    }
}