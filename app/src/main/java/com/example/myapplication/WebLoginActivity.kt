package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginWebBinding

class WebLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webview

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("myapp://loginSuccess")) {
                    val uri = Uri.parse(url)
                    val userJson = uri.getQueryParameter("user")

                    if (userJson != null) {
                        val intent = Intent(this@WebLoginActivity, MainActivity::class.java)
                        intent.putExtra("login_status", "Y")
                        intent.putExtra("user_info", userJson) // ✅ user_info를 추가로 넘긴다!
                        startActivity(intent)
                        finish()
                    }
                    return true
                } else if (url.startsWith("myapp://loginFail")) {
                    runOnUiThread {
                        Toast.makeText(this@WebLoginActivity, "로그인 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    view.loadUrl("https://7fdad76f-0bff-4920-9ce9-5936fe73ebea-00-4fnc66eafuyg.pike.replit.dev/login")
                    return true
                }
                return false
            }
        }

        // 서버에 배포된 웹 로그인 주소를 여기에 적어야 함!
        webView.loadUrl("https://7fdad76f-0bff-4920-9ce9-5936fe73ebea-00-4fnc66eafuyg.pike.replit.dev/login")
    }
}
