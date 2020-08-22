package com.hannibalprojects.sampleproject.presentation.frags

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LivePagedListBuilder
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.databinding.FragmentUsersListBinding
import com.hannibalprojects.sampleproject.presentation.adapters.UsersListAdapter
import com.hannibalprojects.sampleproject.presentation.viewmodels.ListUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ListUsersFragment : Fragment() {

    private val viewModel : ListUsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = UsersListAdapter { v, user ->
            val args = Bundle()
            args.putInt(UserDetailsFragment.ID_USER_ARG, user.id)
            val frag = UserDetailsFragment()
            frag.arguments = args
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val image = v.findViewById<ImageView>(R.id.imageView)
                val firstName = v.findViewById<TextView>(R.id.firstName)
                val lastName = v.findViewById<TextView>(R.id.LastName)
                transaction?.addSharedElement(image, image.transitionName)
                transaction?.addSharedElement(firstName, firstName.transitionName)
                transaction?.addSharedElement(lastName, lastName.transitionName)
            }

            transaction?.addToBackStack("users")
                ?.replace(R.id.fragment_container, frag)
                ?.commit()
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
 /*       val downloadWorkRequest =
            PeriodicWorkRequestBuilder<DownloadWorker>(60, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "DOWNLOAD_WORK_TAG",
            ExistingPeriodicWorkPolicy.KEEP,
            downloadWorkRequest
        )*/
    }
}