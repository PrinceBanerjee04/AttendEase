package com.bp.attendease.main_files.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bp.attendease.db.data_class.Classroom
import com.bp.attendease.db.viewmodels.AppViewModel
import com.bp.attendease.main_files.adapters.ClassroomAdapter
import com.example.attendease.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView

class Home : Fragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var recyclerview: RecyclerView
    private lateinit var classroomAdapter: ClassroomAdapter
    private var classroomItems = arrayListOf<Classroom>()
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var whiteView: View
    private lateinit var loader: GifImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]
        refreshLayout = view.findViewById(R.id.refresh_layout_classroom_teacher)
        whiteView = view.findViewById(R.id.whiteView_classroom_teacher)
        loader = view.findViewById(R.id.loader_classroom_teacher)
        recyclerview = view.findViewById(R.id.recyclerview_classroom_teacher)

        refreshLayout.visibility = View.GONE
        whiteView.visibility = View.VISIBLE
        loader.visibility = View.VISIBLE

        classroomAdapter = ClassroomAdapter(requireContext(), classroomItems)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        recyclerview.setItemViewCacheSize(20)
        recyclerview.adapter = classroomAdapter

        observeClassrooms()

        refreshLayout.setOnRefreshListener {
            whiteView.visibility = View.VISIBLE
            loader.visibility = View.VISIBLE
            observeClassrooms()

        }

        return view
    }

    private fun observeClassrooms() {
        appViewModel.fetchClassRooms("Uid407")
        lifecycleScope.launch {
            appViewModel.classrooms.collect { classrooms ->
                fetchData(classrooms)
            }
        }

    }

    private fun fetchData(classrooms: List<Classroom>) {
        classroomItems = arrayListOf()
        if(classrooms.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "You don't have classroom", Toast.LENGTH_SHORT).show()
        } else {
            classrooms.forEach {classroom ->
                classroomItems.add(classroom)
            }
            classroomAdapter.updateClassroom(classroomItems)
        }
        refreshLayout.visibility = View.VISIBLE
        whiteView.visibility = View.GONE
        loader.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }

}