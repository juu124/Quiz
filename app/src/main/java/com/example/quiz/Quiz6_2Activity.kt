package com.example.quiz

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz.databinding.ActivityQuiz62Binding

class Quiz6_2Activity : AppCompatActivity() {
    lateinit var binding : ActivityQuiz62Binding

    inner class WebAppInterface {
        @JavascriptInterface
        fun calcNum(num: Int) {
            Log.d("Quiz6_2Activity", "plusNum() num $num")
            var result = 0
            for (i in 1..num) {
                result += i
            }
            Log.d("Quiz6_2Activity", "plusNum() result $result")
            // 모든 화면 반영과 관련된 코드는 main(ui) thread에 의해 처리되어야 하는 규칙이 있다.
            // runOnUiThread를 사용하거나 post를 사용한다.
            // TextView - 문자열 교체
            // WebView - loadUrl()
            // runOnUiThread() : activity 함수 { } 부분을 ui thread 에서 실행시켜 준다.

            // 어제 Text는 왜 컴파일 에러가 나지 않았을까?
            // ==> TextView 내부에서 text 교체업무를 스레드를 체크해서 ui Thread가 아니면 ui thread로 처리해주는 부분이 준비되어 있기 때문에 문제가 되지 않았다.
            binding.plusCalcWebview.post {
                binding.plusCalcWebview.loadUrl("javascript:updateText($result)")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQuiz62Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.plusCalcWebview.settings.javaScriptEnabled = true
        binding.plusCalcWebview.addJavascriptInterface(WebAppInterface(), "CalAndroid")
        binding.plusCalcWebview.loadUrl("file:///android_asset/num_plus.html")
    }
}