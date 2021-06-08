/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dagger.registration.enterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.android.dagger.databinding.FragmentEnterDetailsBinding
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterDetailsFragment : Fragment() {

    private var _binding: FragmentEnterDetailsBinding? = null
    private val binding get() = _binding!!

    /**
     * RegistrationViewModel is used to set the username and password information (attached to
     * Activity's lifecycle and shared between different fragments)
     * EnterDetailsViewModel is used to validate the user input (attached to this
     * Fragment's lifecycle)
     *
     * They could get combined but for the sake of the codelab, we're separating them so we have
     * different ViewModels with different lifecycles.
     *
     * @Inject annotated fields will be provided by Dagger
     */
    private val registrationViewModel: RegistrationViewModel by activityViewModels()
    private val enterDetailsViewModel: EnterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        bindViewModel(viewLifecycleOwner)
        setupViews()
        return view
    }

    private fun bindViewModel(owner: LifecycleOwner) {
        enterDetailsViewModel.enterDetailsState.observe(owner, { state ->
            when (state) {
                is EnterDetailsSuccess -> {
                    val username = binding.username.text.toString()
                    val password = binding.password.text.toString()
                    registrationViewModel.updateUserData(username, password)

                    (activity as RegistrationActivity).onDetailsEntered()
                }
                is EnterDetailsError -> {
                    binding.error.text = state.error
                    binding.error.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupViews() {
        binding.username.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }
        binding.password.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.next.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            enterDetailsViewModel.validateInput(username, password)
        }
    }
}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()
