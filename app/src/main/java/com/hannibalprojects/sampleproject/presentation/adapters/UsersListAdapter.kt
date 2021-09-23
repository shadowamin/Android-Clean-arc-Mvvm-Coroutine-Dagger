package com.hannibalprojects.sampleproject.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.UserCardBinding
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.presentation.frags.UserDetailsFragment

class UsersListAdapter(val callback: (Navigator.Extras, Bundle) -> Unit) :
    ListAdapter<User, UsersListAdapter.UserCardViewHolder>(DIFF_CALLBACK) {

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
        with(holder.binding) {
            val user = getItem(position)
            holder.binding.user = user
            user?.let {
                userImage.transitionName = TRANSITION_AVATAR + user.id
                userFirstName.transitionName = TRANSITION_FirstName + user.id
                userLastName.transitionName = TRANSITION_LastName + user.id
            }
            holder.itemView.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    userImage to userImage.transitionName,
                    userFirstName to userFirstName.transitionName,
                    userLastName to userLastName.transitionName
                )
                val bundle = bundleOf(UserDetailsFragment.ID_USER_ARG to user.id)
                callback(extras, bundle)
            }
        }
    }

    class UserCardViewHolder(val binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root)
}