package kr.ac.hallym.termproject

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kr.ac.hallym.termproject.databinding.ActivityPersonalInfoBinding
import kr.ac.hallym.termproject.databinding.DialogChangeProfileBinding
import kr.ac.hallym.termproject.databinding.DialogChooseWebviewBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Base64.getEncoder
import java.io.OutputStream as OutputStream

class PersonalInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalInfoBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_personal_info)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        filePath = "/data/user/0/kr.ac.hallym.termproject/files/profile.png"
        val file = File(filePath)

        if (file.exists()) {
            try {
                val bitmap = BitmapFactory.decodeFile(filePath)

                Glide.with(this)
                    .load(bitmap)
                    .override(150, 150)
                    .into(binding.userImageView)
            } catch (e: Exception) {
                Log.d("kk", "profile load error")
            }
        } else {
            binding.userImageView.setImageResource(R.drawable.profile)
        }

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

            try {
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, BitmapFactory.Options())
                inputStream!!.close()

                val f = File("profile.png")
                val os: FileOutputStream = openFileOutput("profile.png", 0)
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, os)
                os.flush()
                os.close()

                Glide.with(this)
                    .load(bitmap)
                    .override(150, 150)
                    .into(binding.userImageView)

            } catch (e: Exception) {
                Log.d("kk", "bitmap null")
            }
        }

        binding.cardProfile.setOnClickListener {
            val dialogBinding = DialogChangeProfileBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(this).apply {
                setView(dialogBinding.root)
            }.create()

            dialog.show()

            dialogBinding.buttonGallery.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                requestGalleryLauncher.launch(intent)
            }
            dialogBinding.buttonBasic.setOnClickListener {
                dialog.dismiss()
                binding.userImageView.setImageResource(R.drawable.profile)
                file.delete()
            }
        }

        binding.addressGithub.setOnClickListener {
            val dialogBinding = DialogChooseWebviewBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(this).apply {
                setView(dialogBinding.root)
            }.create()
            dialog.show()

            dialogBinding.webView.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this, WebActivity::class.java)
                intent.putExtra("address", binding.addressGithub.text.toString())
                startActivity(intent)
            }
            dialogBinding.otherBrowsers.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.addressGithub.text.toString()))
                intent.setPackage("com.android.chrome") // 브라우저를 크롬으로 고정
                startActivity(intent)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> true
    }
}