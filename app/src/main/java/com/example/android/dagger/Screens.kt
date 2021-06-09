package com.example.android.dagger

import android.content.Intent
import com.example.android.dagger.login.LoginActivity
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.registration.enterdetails.EnterDetailsFragment
import com.example.android.dagger.registration.termsandconditions.TermsAndConditionsFragment
import com.example.android.dagger.settings.SettingsActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun main() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun registration() = ActivityScreen { Intent(it, RegistrationActivity::class.java) }

    fun details() = FragmentScreen { EnterDetailsFragment() }

    fun termsAndConditions() = FragmentScreen { TermsAndConditionsFragment() }

    fun login() = ActivityScreen { Intent(it, LoginActivity::class.java) }

    fun settings() = ActivityScreen { Intent(it, SettingsActivity::class.java) }
}
