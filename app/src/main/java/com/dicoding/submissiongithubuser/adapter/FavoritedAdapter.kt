package com.dicoding.submissiongithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.databinding.ListItemBinding
import com.dicoding.submissiongithubuser.ui.activity.DetailActivity

class FavoritedAdapter :
    androidx.recyclerview.widget.ListAdapter<FavoriteEntity, FavoritedAdapter.MyViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(favorited: FavoriteEntity) {
            binding.tvUsername.text = favorited.username
            Glide.with(binding.civProfile).load(favorited.avatarUrl).into(binding.civProfile)

            itemView.setOnClickListener {
                val usernameIntent = favorited.username
                val avatarIntent = favorited.avatarUrl
                var intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.PAKET_USERNAME, usernameIntent)
                intent.putExtra(DetailActivity.PAKET_AVATAR, avatarIntent)
                binding.root.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorited = getItem(position)
        holder.bind(favorited)

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> =
            object : DiffUtil.ItemCallback<FavoriteEntity>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteEntity,
                    newItem: FavoriteEntity
                ): Boolean {
                    return oldItem.username == newItem.username
                }

                override fun areContentsTheSame(
                    oldItem: FavoriteEntity,
                    newItem: FavoriteEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}
