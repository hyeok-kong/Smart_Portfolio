package kr.ac.hallym.termproject

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.termproject.databinding.ActivityMainBinding
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
        binding.itemId.text = contents[position].id.toString()
        binding.itemTitle.text = contents[position].title
        binding.itemSub.text = contents[position].sub
        binding.editButton.setOnClickListener {
            val popupMenu = PopupMenu(v, binding.itemTitle)
            popupMenu.menuInflater.inflate(R.menu.menu_recycler, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit_recycler -> { // 내용 수정 시 EditActivity로 이동
                        if(binding.itemId.text.toString() == "0") {
                            Toast.makeText(v, "기본 데이터는 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(holder.itemView?.context, EditActivity::class.java)
                            intent.putExtra("id", binding.itemId.text.toString())
                            intent.putExtra("title", binding.itemTitle.text.toString())
                            intent.putExtra("detail", binding.itemSub.text.toString())

                            ContextCompat.startActivity(holder.itemView.context, intent, null)
                        }
                        true
                    }
                    R.id.delete_recycler -> {
                        Log.d("kk","click ${binding.itemId.text} item delete")
                        val dbId = binding.itemId.text.toString()
                        val db = DBHelper(v).writableDatabase
                        if(dbId == "0") {
                            Toast.makeText(v, "기본 데이터는 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            val dialog = AlertDialog.Builder(v)

                            dialog.run {
                                setTitle("모바일 이력서")
                                setMessage("정말 삭제하시겠습니까?")
                                setNegativeButton("YES", DialogInterface.OnClickListener { dialog, id ->
                                    db.execSQL("delete from CAREER_TB where _id=?",
                                        arrayOf(dbId.toInt()))
                                    Toast.makeText(v, "삭제되었습니다. 새로고침을 해주세요", Toast.LENGTH_SHORT).show()
                                })
                                setPositiveButton("NO", null)
                                show()
                            }
                        }
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