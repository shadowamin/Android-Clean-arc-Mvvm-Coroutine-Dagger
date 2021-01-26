package com.hannibalprojects.sampleproject.presentation.frags

import androidx.fragment.app.Fragment
import com.hannibalprojects.sampleproject.presentation.models.RequestError
import com.hannibalprojects.sampleproject.presentation.utils.ErrorManager

abstract class BaseFragment : Fragment() {

    open fun displayError(requestError: RequestError) {
        ErrorManager.displayError(context, requestError)
    }
}