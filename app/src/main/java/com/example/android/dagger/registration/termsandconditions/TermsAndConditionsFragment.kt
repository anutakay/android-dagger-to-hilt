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

package com.example.android.dagger.registration.termsandconditions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.dagger.databinding.FragmentTermsAndConditionsBinding
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionsFragment : Fragment() {

    private var _binding: FragmentTermsAndConditionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsAndConditionsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupViews(binding, viewModel)
        return view
    }

    private fun setupViews(
        binding: FragmentTermsAndConditionsBinding,
        viewModel: RegistrationViewModel
    ) = with(binding) {
        next.setOnClickListener {
            viewModel.acceptTCs()
            (activity as RegistrationActivity).onTermsAndConditionsAccepted()
        }
    }
}
