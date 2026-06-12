package com.example.hospitalregistration.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.R
import com.example.hospitalregistration.model.Appointment

class WchAppointmentAdapter(
    private val list: List<Appointment>,
    private val listener: (Appointment) -> Unit
) : RecyclerView.Adapter<WchAppointmentAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
        val content: TextView = view.findViewById(R.id.txtContent)
        val button: Button = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val doctorName = item.doctor?.name ?: ""
        val date = item.schedule?.workDate ?: ""
        val period = item.schedule?.timePeriod ?: ""
        holder.title.text = doctorName + "  " + (item.status ?: "")
        holder.content.text = "就诊人：" + (item.patientName ?: "") + "\n时间：" + date + " " + period
        holder.button.isEnabled = item.status != "已取消"
        holder.button.text = if (item.status == "已取消") "已取消" else "取消预约"
        holder.button.setOnClickListener {
            listener(item)
        }
    }
}
