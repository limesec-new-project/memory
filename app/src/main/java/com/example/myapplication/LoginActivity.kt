package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.btnLogin.setOnClickListener {
            val userId = binding.etId.text.toString()
            val userPassword = binding.etPassword.text.toString()

            // 로그인 로직
            // id: test pw:test
            if(userId=="usertest" && userPassword=="usertest"){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("login_status", "Y")
                startActivity(intent)
                finish()

            }
            else{
                Toast.makeText(this, "로그인에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("navigateTo", "signupFragment")
            }
            startActivity(intent)
            finish()
        }


//        loginViewModel.loginResult.observe(this) { loginResult ->
//            if (loginResult != null) {
//                if (loginResult.code == 200) {
//                    // 로그인 성공 후 SharedPreferneces에 토큰 저장
//                    loginViewModel.cookie.observe(this){ cookie ->
//                        saveToken(cookie, this)
//                    }
//
//                    // 게시글 작성 페이지에서 왔을 경우
//                    val afterLoginAction = intent.getStringExtra("afterLoginAction")
//                    if (afterLoginAction=="addPost") {
//                        setResult(Activity.RESULT_OK)
//                    }
//                    else {
//                        // main activity로 이동
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                    }
//
//                    finish() // LoginActivity 종료
//                }
//                Toast.makeText(this, loginResult.msg, Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "문제가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//            }
//        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                기존에 남아 있는 액티비티 스택을 모두 제거하고 새로 실행.
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                finish()
            }
        })

    }

}