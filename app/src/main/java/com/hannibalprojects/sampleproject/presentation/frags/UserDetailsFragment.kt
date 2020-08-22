package com.hannibalprojects.sampleproject.presentation.frags

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUserDetailsBinding
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.viewmodels.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    companion object {
        const val ID_USER_ARG = "idUser"
    }

    private val viewModel : UserDetailsViewModel by viewModels()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val idUser = arguments?.getInt(ID_USER_ARG)
        val binding = DataBindingUtil.inflate<FragmentUserDetailsBinding>(
            inflater,
            R.layout.fragment_user_details,
            container,
            false
        )
        binding.viewModel = viewModel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.imageView2.transitionName = UsersListAdapter.TRANSITION_AVATAR + idUser
            binding.textView3.transitionName = UsersListAdapter.TRANSITION_FirstName + idUser
            binding.textView4.transitionName = UsersListAdapter.TRANSITION_Lastame + idUser
        }
        viewModel.getUserDetails(idUser!!)

        return binding.root
    }
}