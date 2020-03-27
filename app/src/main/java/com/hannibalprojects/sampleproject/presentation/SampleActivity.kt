package com.hannibalprojects.sampleproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.presentation.frags.ListUsersFragment

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val transaction = supportFragmentManager.beginTransaction()
        val fragmentList = ListUsersFragment()
        transaction.replace(R.id.fragment_container, fragmentList, "usersList").commit()
    }
}