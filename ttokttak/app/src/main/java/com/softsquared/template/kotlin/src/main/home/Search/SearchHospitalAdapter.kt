package com.softsquared.template.kotlin.src.main.home.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.SearchHospitalListBinding
import com.softsquared.template.kotlin.src.ItemClickListener
import com.softsquared.template.kotlin.src.main.home.models.Row

class SearchHospitalAdapter(var hospitalList : List<Row>, private val clickListener : ItemClickListener<Row>)
    :RecyclerView.Adapter<SearchHospitalAdapter.HospitalListViewHolder>(){

    private lateinit var binding : SearchHospitalListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalListViewHolder {
        binding = SearchHospitalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HospitalListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalListViewHolder, position: Int) {
        holder.bind(hospitalList[position])
        if(position % 2 == 1){
            binding.tvSearchHospitalReservation.text = "바로 진료 가능"
        }
        binding.clSearchHospitalResult.setOnClickListener {
            clickListener.onItemClick(position,hospitalList[position])
        }
    }

    override fun getItemCount(): Int {
        return hospitalList.size
    }

    inner class HospitalListViewHolder(binding : SearchHospitalListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(row: Row){
            binding.tvSearchHospitalName.text = row.hospitalName
            binding.tvSearchHospitalOpen.text = row.hospitalOpen
            binding.tvSearchHospitalSector.text = row.hospitalSector
            binding.tvSearchHospitalAddress.text = row.hospitalAddress
        }
    }
}