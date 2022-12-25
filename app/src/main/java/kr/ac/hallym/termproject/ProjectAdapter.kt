package kr.ac.hallym.termproject

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.termproject.databinding.CardLayoutBinding
import java.io.ByteArrayOutputStream


class ProjectViewHolder(val binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root)

class ProjectAdapter(val contents: MutableList<Project>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProjectViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ProjectViewHolder).binding
        binding.title.text = contents[position].title
        binding.detail.text = contents[position].sub
        binding.image.setImageResource(contents[position].img)

        val drawable = binding.image.drawable
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        binding.cardView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, ProjectDetailActivity::class.java)
            intent.putExtra("title", binding.title.text.toString())
            intent.putExtra("detail", binding.detail.text.toString())
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return contents?.size ?: 0
    }
}