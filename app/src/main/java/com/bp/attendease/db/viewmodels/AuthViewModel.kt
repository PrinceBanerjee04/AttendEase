package com.bp.attendease.db.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bp.attendease.db.repos.AuthRepository

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)
}