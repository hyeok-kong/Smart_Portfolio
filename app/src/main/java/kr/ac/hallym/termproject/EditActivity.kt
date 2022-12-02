package kr.ac.hallym.termproject

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kr.ac.hallym.termproject.databinding.ActivityEditBinding
import kr.ac.hallym.termproject.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("title")
        val detail = intent.getStringExtra("detail")

        binding.addTitle.setText(title)
        binding.addDetail.setText(detail)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> { // 내용 추가 화면에서 업 버튼 누를시 뒤로가기
            val tt = binding.addTitle.text.toString()
            val dt = binding.addDetail.text.toString()

            if (!(tt.length == 0 && dt.length == 0)) { // 내용이 존재하는데 홈버튼 누를 시 다이얼로그 띄우기
                val dialog = AlertDialog.Builder(this)

                dialog.run {
                    setTitle("모바일 이력서")
                    setMessage("내용이 변경되지 않습니다.\n취소하시겠습니까?")
                    setNegativeButton("YES", DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                    setPositiveButton("NO", null)
                    show()
                }
                true
            } else {
                finish()
                true
            }
        }
        R.id.menu_add_save -> {
            val itemId = intent.getStringExtra("id")?.toInt()
            val tt = binding.addTitle.text.toString()
            val dt = binding.addDetail.text.toString()
            val db = DBHelper(this).writableDatabase
            val intent = getIntent()

            if (tt.length == 0 || dt.length == 0) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                false
            } else {
                db.execSQL(
                    "update CAREER_TB set title=?, detail=? where _id=?",
                    arrayOf(tt, dt, itemId)
                )
                db.close()
                finish()

                val toast = Toast.makeText(this, "변경되었습니다. 새로고침을 해주세요.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 100)
                toast.show()
                true
            }
        }
        else -> true
    }

    override fun onBackPressed() {
        val tt = binding.addTitle.text.toString()
        val dt = binding.addDetail.text.toString()

        if (!(tt.length == 0 && dt.length == 0)) { // 내용이 존재하는데 홈버튼 누를 시 다이얼로그 띄우기
            val dialog = AlertDialog.Builder(this)

            dialog.run {
                setTitle("모바일 이력서")
                setMessage("내용이 변경되지 않습니다.\n취소하시겠습니까?")
                setNegativeButton("YES", DialogInterface.OnClickListener { dialog, id ->
                    finish()
                })
                setPositiveButton("NO", null)
                show()
            }
        } else {
            finish()
            super.onBackPressed()
        }
    }
}
