package com.hannibalprojects.sampleproject.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.UserCardBinding
import com.hannibalprojects.sampleproject.domain.User

class UsersListAdapter(val callback: (View, User) -> Unit) :
    PagedListAdapter<User, UsersListAdapter.UserCardViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
        const val TRANSITION_AVATAR = "avatar"
        const val TRANSITION_FirstName = "lastName"
        const val TRANSITION_LastName = "firstName"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        val binding = DataBindingUtil.inflate<UserCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.user_card,
            parent,
            false
        )
        return UserCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        holder.binding.user = getItem(position)
        val item = getItem(position)
        if (item != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.imageView.transitionName = TRANSITION_AVATAR + item.id
                holder.binding.firstName.transitionName = TRANSITION_FirstName + item.id
                holder.binding.LastName.transitionName = TRANSITION_LastName + item.id
            }
            holder.itemView.setOnClickListener {
                callback(holder.itemView, item)
            }
        }
    }

    class UserCardViewHolder(val binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root)
}