package kr.ac.hallym.termproject

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kr.ac.hallym.termproject.databinding.ActivityIntroBinding
import java.io.File

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.introButton.setOnClickListener {
            val Intent = Intent(this, SplashActivity::class.java)
            startActivity(Intent)
            finish()
        }

        val filePath = "/data/user/0/kr.ac.hallym.termproject/files/profile.png"
        val file = File(filePath)

        if (file.exists()) {
            try {
                val bitmap = BitmapFactory.decodeFile(filePath)

                Glide.with(this)
                    .load(bitmap)
                    .override(300, 300)
                    .into(binding.profile)
            } catch (e: Exception) {
                Log.d("kk", "profile load error")
            }
        } else {
            binding.profile.setImageResource(R.drawable.profile)
        }
    }
}