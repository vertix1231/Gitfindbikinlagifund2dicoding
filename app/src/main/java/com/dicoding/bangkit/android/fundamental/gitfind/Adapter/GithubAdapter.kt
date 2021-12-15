package com.dicoding.bangkit.android.fundamental.gitfind.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.Activity.DetailUserActivity
import com.dicoding.bangkit.android.fundamental.gitfind.Pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R

class GithubAdapter(private val listgithub : ArrayList<Github>) : RecyclerView.Adapter<GithubAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvuser : TextView = itemView.findViewById(R.id.tv_userlist)
        var cvgambar : ImageView = itemView.findViewById(R.id.cv_user)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_github,parent,false)
        return ListViewHolder(view)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val githubb = listgithub[position]
        holder.tvuser.text = githubb.username
        Glide.with(holder.itemView.context)
                .load(githubb.photoo).apply(RequestOptions().override(75,75))
                .into(holder.cvgambar)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu memilih " + listgithub[position].username, Toast.LENGTH_SHORT).show()
        }

        val dataUser = Github(
                githubb.username,
                githubb.name,
                githubb.photoo,
                githubb.location,
                githubb.repository,
                githubb.company,
                githubb.followers,
                githubb.following,

        )

        val mContextt = holder.itemView.context
        holder.itemView.setOnClickListener {
            val movedetailuseractivity = Intent(mContextt,DetailUserActivity::class.java)
            movedetailuseractivity.putExtra(DetailUserActivity.EXTRA_DATA,dataUser)
            mContextt.startActivity(movedetailuseractivity)
        }
    }

    override fun getItemCount(): Int {
        return listgithub.size
    }
}