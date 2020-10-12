package com.hannibalprojects.sampleproject.presentation.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LivePagedListBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUsersListBinding
import com.hannibalprojects.sampleproject.presentation.DownloadWorker
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.viewmodels.ListUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ListUsersFragment : Fragment() {

    private val viewModel : ListUsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = UsersListAdapter { v, user ->
            val image = v.findViewById<ImageView>(R.id.imageView)
            val firstName = v.findViewById<TextView>(R.id.firstName)
            val lastName = v.findViewById<TextView>(R.id.LastName)
            val extras = FragmentNavigatorExtras(
                image to image.transitionName, firstName to firstName.transitionName, lastName to lastName.transitionName
            )
            val bundle = bundleOf(UserDetailsFragment.ID_USER_ARG to user.id)
            findNavController().navigate(R.id.action_list_users_to_user_details, bundle, null, extras)
        }

        val binding = DataBindingUtil.inflate<FragmentUsersListBinding>(
            LayoutInflater.from(context),
            R.layout.fragment_users_list, container, false
        )
        binding.usersList.adapter = adapter
        viewModel.refreshUsers()

        viewModel.loadUsers {
                LivePagedListBuilder(it, 5).build().observeForever {
                    adapter.submitList(it)
                }
        }

        activateWorker()

        return binding.root
    }

    private fun activateWorker(){
        val downloadWorkRequest =
            PeriodicWorkRequestBuilder<DownloadWorker>(60, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "DOWNLOAD_WORK_TAG",
            ExistingPeriodicWorkPolicy.KEEP,
            downloadWorkRequest
        )
    }
}