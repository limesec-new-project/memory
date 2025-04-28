package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var loginStatus = "N"
        var username = ""

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginStatus = intent.getStringExtra("login_status").toString()

        if(loginStatus=="Y"){
            // 추가로 유저 정보를 받아온다
            val userJson = intent.getStringExtra("user_info")
            if (userJson != null) {
                try {
                    val userObject = JSONObject(userJson)
                    username = userObject.getString("username")
                    binding.tvLoginStatus.text = "$username 님 환영합니다!"
                } catch (e: Exception) {
                    e.printStackTrace()
                    binding.tvLoginStatus.text = "로그인 되었습니다."
                }
            } else {
                binding.tvLoginStatus.text = "로그인 되었습니다."
            }
            binding.btnLogout.visibility= View.VISIBLE
            binding.btnAppLogin.visibility= View.GONE
            binding.btnWebLogin.visibility= View.GONE


        }
        else{
            binding.tvLoginStatus.text = "로그인되지 않았습니다."
            binding.btnLogout.visibility= View.GONE
            binding.btnAppLogin.visibility= View.VISIBLE
            binding.btnWebLogin.visibility= View.VISIBLE
        }


        // 앱 로그인 버튼을 눌렀을 때
        binding.btnAppLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 웹 로그인 버튼을 눌렀을 때
        binding.btnWebLogin.setOnClickListener {
            val intent = Intent(this, WebLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 로그아웃 버튼을 눌렀을 때
        binding.btnLogout.setOnClickListener {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            loginStatus = "N"

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}