package com.bp.attendease.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bp.attendease.repos.AppRepository

class AppViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(application)
}