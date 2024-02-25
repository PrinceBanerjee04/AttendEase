package com.bp.attendease.db.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bp.attendease.db.repos.AppRepository
import com.bp.attendease.db.repos.Response
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(application)

    fun createClassroom(
        teacherUid: String,
        classId: String,
        subjectName: String,
        subjectCode: String,
        department: String,
        semester: Int,
        students: Int,
        callback: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            val result = appRepository.createClassroom(
                teacherUid,
                classId,
                subjectName,
                subjectCode,
                department,
                semester,
                students
            )
            when (result) {
                is Response.Success -> {
                    callback(true, "Classroom created successfully")
                }
                is Response.Failure -> {
                    callback(false, "Failed to create classroom: ${result.errorMassage}")
                }
            }
        }
    }


}