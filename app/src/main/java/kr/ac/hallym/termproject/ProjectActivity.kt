package kr.ac.hallym.termproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.ac.hallym.termproject.databinding.ActivityProjectBinding

class ProjectActivity : AppCompatActivity() {

    lateinit var binding: ActivityProjectBinding
    lateinit var adapter: ProjectAdapter
    lateinit var contents: MutableList<Project>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_project)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contents = mutableListOf<Project>()
        initData()

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerProject.layoutManager = staggeredGridLayoutManager
        adapter = ProjectAdapter(contents)
        binding.recyclerProject.adapter = adapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }
            fun initData() {
        contents.add(
            Project(
                "스마트 포트폴리오",
                "모바일 프로그래밍 기말 프로젝트로 진행한 프로젝트이다. 안드로이드 스튜디오를 이용해 " +
                        "개발 역략과 이력을 제공하는 앱을 만들었다.",
                R.drawable.project_portfolio
            )
        )
        contents.add(
            Project(
                "네트워크 이상탐지 시스템",
                "KDDCUP 데이터셋과 LSTM을 이용해 이상 트래픽 탐지기능을 구현하였고,"
                        + " pyshark로 수집한 트래픽을 평행좌표를 이용해 시각화하였다.",
                R.drawable.project_network
            )
        )
        contents.add(
            Project(
                "유튜브 댓글 감정분석",
                "네이버 영화 댓글의 내용과 별점을 수집하여 형태소 분석 및 전처리 진행," +
                        "LogisticRegression으로 모델을 학습시켜 텍스트의 긍/부정을 구분하는 분류기 제작" +
                        "Selenium을 이용해 유튜브 댓글을 수집하고, 해당 댓글들을 모델이 예측하였음" +
                        "분류는 성공적이였지만, 수집한 영상의 댓글이 대부분 긍정적이여서 부정에 대한 분류를 확인하기 힘들었음",
                R.drawable.project_textprocessing
            )
        )
        contents.add(
            Project(
                "클래식기타 소개 웹 페이지",
                "클래식 기타를 널리 알리고 싶어 제작한 웹페이지. " +
                        "웹의 기초를 다지기 위해 만들었으며 html, css, js를 이용해 제작하였다.",
                R.drawable.project_web
            )
        )
        contents.add(
            Project(
                "은행 내역 확인 프로그램",
                "책을 토대로 제작한 프로그램이며, Java 연습을 위함. " +
                        "csv파일에 저장되어 있는 내역을 가져와 처리하고 외부로 발송하는 프로그램이며," +
                        "도메인 클래스, 단일 책임 원칙, 개방/폐쇄 원칙 등에 대한 공부를 함. 또한 JUnit을 이용한 테스트와 maven을 이용해 빌드를 함.",
                R.drawable.project_bank
            )
        )
        contents.add(
            Project(
                "문서 관리 시스템",
                "여러 확장자로 된 파일들을 읽어 정리하는 프로그램이며 " +
                        "인터페이스를 이용해 프로그램의 확장성을 보장하고, 이에 따른 리스코프 치환 원칙에 대해 공부.",
                R.drawable.project_dms
            )
        )

    }
}