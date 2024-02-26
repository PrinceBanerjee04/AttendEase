package com.bp.attendease.main_files.fragments

import android.annotation.SuppressLint
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import com.bp.attendease.db.viewmodels.AppViewModel
import com.example.attendease.R
import io.grpc.Context
import pl.droidsonroids.gif.GifImageView

class CreateClassRoom : Fragment() {

    private lateinit var backBtn: ImageView
    private lateinit var createBtn: AppCompatButton
    private lateinit var subjectNameET: AppCompatEditText
    private lateinit var subjectCodeET: AppCompatEditText
    private lateinit var departmentET: AppCompatEditText
    private lateinit var semesterET: AppCompatEditText
    private lateinit var studentStrengthET: AppCompatEditText
    private lateinit var classId: String
    private lateinit var appViewModel: AppViewModel
    private var isAllRight = true
    private lateinit var whiteView: View
    private lateinit var loader: GifImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_class_room, container, false)

        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]
        backBtn = view.findViewById(R.id.back_btn_create_classroom_teacher)
        createBtn = view.findViewById(R.id.btn_create_classroom_teacher)
        subjectNameET = view.findViewById(R.id.subject_name_create_classroom_teacher)
        subjectCodeET = view.findViewById(R.id.subject_code_create_classroom_teacher)
        departmentET = view.findViewById(R.id.department_create_classroom_teacher)
        semesterET = view.findViewById(R.id.semester_create_classroom_teacher)
        studentStrengthET = view.findViewById(R.id.student_strength_create_classroom_teacher)
        whiteView = view.findViewById(R.id.whiteView_create_classroom_teacher)
        loader = view.findViewById(R.id.loader_create_classroom_teacher)

        subjectNameET.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (subjectNameET.text!!.isEmpty()) {
                    subjectNameET.hint = "e.g. DBMS, Economics, etc."
                }
            } else {
                subjectNameET.hint = null
            }
        }

        subjectCodeET.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (subjectCodeET.text!!.isEmpty()) {
                    subjectCodeET.hint = "e.g. PCC-CSE501, ESC-CSE552, etc."
                }
            } else {
                subjectCodeET.hint = null
            }
        }

        departmentET.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (departmentET.text!!.isEmpty()) {
                    departmentET.hint = "e.g. Math, Electrical Engineering, etc."
                }
            } else {
                departmentET.hint = null
            }
        }

        semesterET.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (semesterET.text!!.isEmpty()) {
                    semesterET.hint = "e.g. 2, 3, 6, 8, etc."
                }
            } else {
                semesterET.hint = null
            }
        }

        studentStrengthET.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (studentStrengthET.text!!.isEmpty()) {
                    studentStrengthET.hint = "No. of students in the class. e.g. 54, etc."
                }
            } else {
                studentStrengthET.hint = null
            }
        }

        createBtn.setOnClickListener {
            isAllRight = true
            if(checkDetails()) {
                whiteView.visibility = View.VISIBLE
                loader.visibility = View.VISIBLE
                if(checkForInternet()) {
                    subjectNameET.isEnabled = false
                    subjectCodeET.isEnabled = false
                    departmentET.isEnabled = false
                    semesterET.isEnabled = false
                    studentStrengthET.isEnabled = false
                    val subjectName = subjectNameET.text.toString()
                    val subjectCode = subjectCodeET.text.toString()
                    val department = departmentET.text.toString()
                    val semester = Integer.parseInt(semesterET.text.toString())
                    val students = Integer.parseInt(studentStrengthET.text.toString())
                    classId = "A${System.currentTimeMillis()}"
                    appViewModel.createClassroom("Uid407", classId, subjectName, subjectCode, department, semester, students) {
                            isSuccess, massage ->
                        if(isSuccess) {
                            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
                        }
                        whiteView.visibility = View.GONE
                        loader.visibility = View.GONE
                        subjectNameET.isEnabled = true
                        subjectCodeET.isEnabled = true
                        departmentET.isEnabled = true
                        semesterET.isEnabled = true
                        studentStrengthET.isEnabled = true
                    }
                } else {
                    whiteView.visibility = View.GONE
                    loader.visibility = View.GONE
                    subjectNameET.isEnabled = true
                    subjectCodeET.isEnabled = true
                    departmentET.isEnabled = true
                    semesterET.isEnabled = true
                    studentStrengthET.isEnabled = true
                    Toast.makeText(requireContext(), "Something went wrong!\nCheck your internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun checkDetails(): Boolean {

        if(subjectNameET.text.isNullOrEmpty()) {
            subjectNameET.error = "Enter subject name"
            isAllRight = false
        }

        if(subjectCodeET.text.isNullOrEmpty()) {
            subjectCodeET.error = "Enter subject code"
            isAllRight = false
        }

        if(departmentET.text.isNullOrEmpty()) {
            departmentET.error = "Enter your department"
            isAllRight = false
        }

        if(semesterET.text.isNullOrEmpty()) {
            semesterET.error = "Enter semester"
            isAllRight = false
        }

        if(studentStrengthET.text.isNullOrEmpty()) {
            studentStrengthET.error = "Enter the no. of student you expect"
            isAllRight = false
        }

        return isAllRight
    }

    private fun checkForInternet(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}