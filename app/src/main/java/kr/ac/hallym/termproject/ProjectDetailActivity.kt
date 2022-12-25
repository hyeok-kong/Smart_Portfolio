package kr.ac.hallym.termproject

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kr.ac.hallym.termproject.databinding.ActivityProjectBinding
import kr.ac.hallym.termproject.databinding.ActivityProjectDetailBinding

class ProjectDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProjectDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_project_detail)
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("title")
        val detail = intent.getStringExtra("detail")

        binding.title.setText(title)
        binding.detail.setText(detail)
        initData(title)
    }

    fun initData(title: String?) = when(title) {
        "스마트 포트폴리오" -> {
            binding.image.setImageResource(R.drawable.project_portfolio)
        }
        "네트워크 이상탐지 시스템" -> {
            binding.image.setImageResource(R.drawable.project_network)
        }
        "유튜브 댓글 감정분석" -> {
            binding.image.setImageResource(R.drawable.project_textprocessing)
        }
        "클래식기타 소개 웹 페이지" -> {
            binding.image.setImageResource(R.drawable.project_web)
        }
        "은행 내역 확인 프로그램" -> {
            binding.image.setImageResource(R.drawable.project_bank)
        }
        "문서 관리 시스템" -> {
            binding.image.setImageResource(R.drawable.project_dms)
        }
        else -> true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }
}