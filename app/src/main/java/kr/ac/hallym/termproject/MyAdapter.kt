package kr.ac.hallym.termproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import kr.ac.hallym.termproject.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val contents: MutableList<Career>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var v: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        v = parent.context
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as MyViewHolder
        val binding = holder.binding
        binding.itemTitle.text = contents[position].title
        binding.itemSub.text = contents[position].sub
        binding.editButton.setOnClickListener {
            val popupMenu = PopupMenu(v, binding.itemTitle)
            popupMenu.menuInflater.inflate(R.menu.menu_recycler, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit_recycler -> {
                        Log.d("kk","click ${binding.itemTitle.text} item edit")
                        true
                    }
                    R.id.delete_recycler -> {
                        Log.d("kk","click ${position + 1} item delete")
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return contents.size
    }
}