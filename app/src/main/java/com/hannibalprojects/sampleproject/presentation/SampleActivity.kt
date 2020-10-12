package com.hannibalprojects.sampleproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.presentation.frags.ListUsersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
   }
}