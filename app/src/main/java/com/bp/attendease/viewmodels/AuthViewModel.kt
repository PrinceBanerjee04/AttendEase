package com.bp.attendease.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bp.attendease.repos.AuthRepository

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)
}