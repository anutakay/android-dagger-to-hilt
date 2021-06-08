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

package com.example.android.dagger.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import com.example.android.dagger.databinding.ActivityLoginBinding
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViewModel(viewModel, this, binding)
        setupViews(binding, viewModel)
    }

    private fun bindViewModel(
        viewModel: LoginViewModel,
        owner: LifecycleOwner,
        binding: ActivityLoginBinding
    ) = with(viewModel) {
        loginState.observe(owner, { state ->
            when (state) {
                is LoginSuccess -> goToMainActivity()
                is LoginError -> binding.error.visibility = View.VISIBLE
            }
        })
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupViews(
        binding: ActivityLoginBinding,
        viewModel: LoginViewModel
    ) = with(binding) {
        username.isEnabled = false
        username.setText(viewModel.getUsername())

        password.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        login.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }
        unregister.setOnClickListener {
            viewModel.unregister()
            goToRegistrationActivity()
        }
    }

    private fun goToRegistrationActivity() {
        val intent = Intent(this, RegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()
