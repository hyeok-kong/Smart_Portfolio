package kr.ac.hallym.termproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import kr.ac.hallym.termproject.databinding.ActivityAddBinding
import kr.ac.hallym.termproject.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> { // 내용 추가 화면에서 업 버튼 누를시 뒤로가기
            val tt = binding.addTitle.text.toString()
            val dt = binding.addDetail.text.toString()

            if(!(tt.length == 0 && dt.length == 0)) { // 문장이 존재하는데 홈버튼 누를 시 다이얼로그 띄우기
                // 구현필요
                false
            } else {
                finish()
                true
            }
        }

        R.id.menu_add_save -> {
            val tt = binding.addTitle.text.toString()
            val dt = binding.addDetail.text.toString()
            val db = DBHelper(this).writableDatabase
            val intent = getIntent()

            if(tt.length == 0 || dt.length == 0) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                false
            } else{
                db.execSQL("insert into CAREER_TB (title, detail) values (?,?)",
                    arrayOf(tt, dt))
                db.close()
                intent.putExtra("title", tt)
                intent.putExtra("detail", dt)
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }
        }
        else -> true
    }
}
