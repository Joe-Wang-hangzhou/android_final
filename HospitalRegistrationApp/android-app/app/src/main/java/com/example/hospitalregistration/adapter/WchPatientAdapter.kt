package com.example.hospitalregistration.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.R
import com.example.hospitalregistration.util.WchPatientInfo

class WchPatientAdapter(
    private val list: List<WchPatientInfo>,
    private val editListener: (WchPatientInfo) -> Unit,
    private val deleteListener: (WchPatientInfo) -> Unit
) : RecyclerView.Adapter<WchPatientAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtPatientName)
        val phone: TextView = view.findViewById(R.id.txtPatientPhone)
        val edit: Button = view.findViewById(R.id.btnEditPatient)
        val delete: Button = view.findViewById(R.id.btnDeletePatient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.phone.text = "手机号：" + item.phone
        holder.edit.setOnClickListener { editListener(item) }
        holder.delete.setOnClickListener { deleteListener(item) }
        holder.itemView.setOnClickListener { editListener(item) }
    }
}
