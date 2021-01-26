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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LivePagedListBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUsersListBinding
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.presentation.DownloadWorker
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.Loading
import com.hannibalprojects.sampleproject.presentation.models.Success
import com.hannibalprojects.sampleproject.presentation.viewmodels.ListUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ListUsersFragment : BaseFragment() {

    private val viewModel: ListUsersViewModel by viewModels()
    private lateinit var binding: FragmentUsersListBinding
    private val adapter by lazy { UsersListAdapter { v, user -> onItemListClicked(v, user) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_users_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
        activateWorker()
        initSwipeToRefreshListener()
    }

    private fun initAdapter() {
        binding.usersList.adapter = adapter
    }

    private fun initSwipeToRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshUsers()
        }
    }

    private fun onItemListClicked(view: View, user: User) {
        val image = view.findViewById<ImageView>(R.id.imageView)
        val firstName = view.findViewById<TextView>(R.id.firstName)
        val lastName = view.findViewById<TextView>(R.id.LastName)
        val extras = FragmentNavigatorExtras(
            image to image.transitionName,
            firstName to firstName.transitionName,
            lastName to lastName.transitionName
        )
        val bundle = bundleOf(UserDetailsFragment.ID_USER_ARG to user.id)
        findNavController().navigate(R.id.action_list_users_to_user_details, bundle, null, extras)
    }

    private fun initObservers() {
        viewModel.loadUsersLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Success -> LivePagedListBuilder(it.data, 5).build().observe(viewLifecycleOwner, Observer {
                    adapter.submitList(it)
                })
                is Failure -> displayError(it.error)
            }
        })

        viewModel.refreshUsersLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Failure -> displayError(it.error)
                is Loading ->{
                    binding.swipeRefreshLayout.isRefreshing = it.loading
                }
            }
        })
    }

    private fun activateWorker() {
        val downloadWorkRequest =
            PeriodicWorkRequestBuilder<DownloadWorker>(60, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "DOWNLOAD_WORK_TAG",
            ExistingPeriodicWorkPolicy.KEEP,
            downloadWorkRequest
        )
    }
}