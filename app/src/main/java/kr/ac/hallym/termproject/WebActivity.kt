package kr.ac.hallym.termproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import kr.ac.hallym.termproject.databinding.ActivityAddBinding
import kr.ac.hallym.termproject.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_web)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent: Intent = getIntent()
        val address = intent.getStringExtra("address").toString()

        val webView = binding.wView
        webView.webViewClient = WebViewClient()
        webView.loadUrl(address)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }
}