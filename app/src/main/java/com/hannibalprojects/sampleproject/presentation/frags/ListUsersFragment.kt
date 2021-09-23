package com.hannibalprojects.sampleproject.presentation.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUsersListBinding
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.Loading
import com.hannibalprojects.sampleproject.presentation.models.Success
import com.hannibalprojects.sampleproject.presentation.viewmodels.ListUsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUsersFragment : BaseFragment() {

    private val viewModel: ListUsersViewModel by viewModels()
    private lateinit var binding: FragmentUsersListBinding
    private var rootView: View? = null
    private val adapter by lazy {
        UsersListAdapter { extras, bundle ->
            findNavController().navigate(R.id.action_list_users_to_user_details, bundle, null, extras)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) return rootView
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_users_list, container, false)
        rootView = binding.root
        initAdapter()
        viewModel.refreshUsers(false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initSwipeToRefreshListener()
        viewModel.loadUsers()
    }

    private fun initAdapter() {
        binding.usersList.adapter = adapter
    }

    private fun initSwipeToRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshUsers(true)
        }
    }

    private fun initObservers() {
        viewModel.loadUsersLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Success -> adapter.submitList(it.data)
                is Failure -> displayError(it.error)
            }
        })

        viewModel.refreshUsersLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Failure -> displayError(it.error)
                is Loading -> binding.swipeRefreshLayout.isRefreshing = it.loading
            }
        })
    }
}