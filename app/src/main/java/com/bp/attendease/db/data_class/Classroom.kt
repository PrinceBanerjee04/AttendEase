package com.bp.attendease.db.data_class

data class Classroom(
    val classId: String? = null,
    val department: String? = null,
    val semester: Int? = null,
    val students: Int? = null,
    val subjectCode: String? = null,
    val subjectName: String? = null,
    val teacherUid: String? = null
)
