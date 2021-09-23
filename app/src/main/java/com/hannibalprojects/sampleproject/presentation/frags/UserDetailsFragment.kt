package com.hannibalprojects.sampleproject.presentation.frags

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUserDetailsBinding
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.viewmodels.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : BaseFragment() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        val idUser = arguments?.getInt(ID_USER_ARG)
        idUser?.let {
            binding.userImage.transitionName = UsersListAdapter.TRANSITION_AVATAR + idUser
            binding.userFirstName.transitionName = UsersListAdapter.TRANSITION_FirstName + idUser
            binding.userLastName.transitionName = UsersListAdapter.TRANSITION_LastName + idUser
            viewModel.getUserDetails(idUser)
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.loadUsersLiveData.observe(viewLifecycleOwner, {
            if (it is Failure) displayError(it.error)
        })
    }

    companion object {
        const val ID_USER_ARG = "idUser"
    }

}