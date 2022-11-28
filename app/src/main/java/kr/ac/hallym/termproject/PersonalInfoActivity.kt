package kr.ac.hallym.termproject

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import kr.ac.hallym.termproject.databinding.ActivityPersonalInfoBinding

class PersonalInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalInfoBinding
    lateinit var  filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_personal_info)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, BitmapFactory.Options())
                inputStream!!.close()

                Glide.with(this)
                    .load(bitmap)
                    .override(150, 150)
                    .into(binding.userImageView)

            } catch (e: Exception) {
                Log.d("kk", "bitmap null")
            }
        }

        binding.cardProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }
    }
}