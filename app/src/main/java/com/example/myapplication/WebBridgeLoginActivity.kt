package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginWebBinding
import org.json.JSONObject

class WebBridgeLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWebBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webview
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.addJavascriptInterface(JSBridge(), "JSBridge")

        webView.webViewClient = object : WebViewClient() {}

        webView.loadUrl("https://7fdad76f-0bff-4920-9ce9-5936fe73ebea-00-4fnc66eafuyg.pike.replit.dev/login_bridge")
    }

    inner class JSBridge {
        @JavascriptInterface
        fun loginSuccess(userJson: String) {
            try {
                val userObject = JSONObject(userJson)
                val username = userObject.getString("username")

                val intent = Intent(this@WebBridgeLoginActivity, MainActivity::class.java)
                intent.putExtra("login_status", "Y")
                intent.putExtra("user_info", userJson)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@WebBridgeLoginActivity, "로그인 정보 파싱 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
