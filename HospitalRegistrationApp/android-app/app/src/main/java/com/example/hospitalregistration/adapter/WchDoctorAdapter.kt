package com.example.hospitalregistration.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.R
import com.example.hospitalregistration.model.Doctor

class WchDoctorAdapter(
    private val list: List<Doctor>,
    private val listener: (Doctor) -> Unit
) : RecyclerView.Adapter<WchDoctorAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.imgDoctorAvatar)
        val title: TextView = view.findViewById(R.id.txtDoctorName)
        val content: TextView = view.findViewById(R.id.txtDoctorInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.name
        var info = item.title ?: ""
        if (!item.specialty.isNullOrEmpty()) {
            info = info + "\n擅长：" + item.specialty
        }
        val hospitalName = item.department?.hospitalName
        if (!hospitalName.isNullOrEmpty()) {
            info = info + "\n医院：" + hospitalName
        }
        holder.content.text = info
        holder.avatar.setImageResource(R.drawable.ic_doctor_avatar)
        holder.itemView.setOnClickListener { listener(item) }
    }
}
