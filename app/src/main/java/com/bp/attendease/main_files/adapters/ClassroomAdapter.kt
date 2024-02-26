package com.bp.attendease.main_files.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bp.attendease.db.data_class.Classroom
import com.example.attendease.R


class ClassroomAdapter(
    private val context: Context,
    private val classroomItems: ArrayList<Classroom>
) :
    RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassroomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class_room_teacher, parent, false)
        return ClassroomViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClassroomViewHolder, position: Int) {
        val currentItem = classroomItems[position]
        holder.subjectName.text = currentItem.subjectName
        holder.semester.text = "${currentItem.semester} Semester"
        holder.classroomBody.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return classroomItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateClassroom(updateClassroomItems: ArrayList<Classroom>) {
        classroomItems.clear()
        classroomItems.addAll(updateClassroomItems)
        notifyDataSetChanged()
    }

    class ClassroomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val semester: TextView = itemView.findViewById(R.id.semester_classroom_teacher)
        val subjectName: TextView = itemView.findViewById(R.id.subject_name_classroom_teacher)
        val classroomBody: RelativeLayout = itemView.findViewById(R.id.body_classroom_teacher)
    }
}