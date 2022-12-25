package kr.ac.hallym.termproject

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.hallym.termproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var contents: MutableList<Career>

    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contents = mutableListOf<Career>()
        initData() // 데이터 불러오기

        binding.recyclerMain.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(contents)
        binding.recyclerMain.adapter = adapter

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        toggle.syncState()

        binding.mainDrawerView.setNavigationItemSelectedListener { // 네비게이션 메뉴 선택 시
            Log.d("kk", "navigation item is clicked: ${it.title}")
            when (it.title) {
                "Personal Details" -> { // 개인 신상 화면으로 이동
                    val intent = Intent(this, PersonalInfoActivity::class.java)
                    startActivity(intent)
                }
                "Projects" -> {
                    val intent = Intent(this, ProjectActivity::class.java)
                    startActivity(intent)
                }
                "Logout" -> { // 시작화면으로 이동 (앱 재시작)
                    finishAffinity()
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val db = DBHelper(this).readableDatabase
                val cursor = db.rawQuery("select * from CAREER_TB", null)
                cursor.moveToLast()
                contents?.add(Career(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))

                adapter.notifyDataSetChanged()
            }
        }

        binding.extFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            requestLauncher.launch(intent)
        }
    }

    override fun onBackPressed() {

        if (System.currentTimeMillis() - initTime > 3000) {
            Toast.makeText(this, "종료하려면 한 번 더 누르세요", Toast.LENGTH_SHORT).show()
            initTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.delete_all_list) {
            val dialog = AlertDialog.Builder(this)

            dialog.run {
                setTitle("모바일 이력서")
                setMessage("추가한 내용을 모두 삭제하시겠습니까?")
                setNegativeButton("YES", DialogInterface.OnClickListener { dialog, id -> // db삭제
                    deleteData()
                })
                setPositiveButton("NO", null)
                show()
            }
            return true
        } else if(item.itemId == R.id.refresh) {
            Log.d("kk","but")
            refreshActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteData() {
        val db = DBHelper(this).writableDatabase
        db.execSQL("delete from CAREER_TB")
        finish()//인텐트 종료
        val intent = getIntent(); //인텐트
        startActivity(intent); //액티비티 열기
        Toast.makeText(this, "추가내용이 삭제되었습니다", Toast.LENGTH_SHORT).show()
    }

    fun initData() {
        contents.add(Career(0,"한림대학교 입학","기간 : 2018.03~2023.12"))
        contents.add(Career(0,"해커톤 참가", "제 1회 2019 강원 학생 SW 아이디어 해커톤"))
        contents.add(Career(0,"교내 코딩테스트 캠프 수료", "2022 여름방학 코딩테스트 대비 캠프"))
        contents.add(Career(0,"2022-2학기 교과목 멘토링", "교과목 보조, 조교활동 진행"))
        contents.add(Career(0,"2022 제 5회 SW Coding Festival", "교내 코딩테스트 대회 동상"))
        contents.add(Career(0,"TOPCIT 정기평가", "260점"))

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from CAREER_TB", null) // 앱 시작 시 DB 읽어오기
        cursor.run {
            while (moveToNext()) {
                contents?.add(Career(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            }
        }
    }

    fun refreshActivity() {
        val intent = getIntent()
        finish()
        startActivity(intent)
    }
}
