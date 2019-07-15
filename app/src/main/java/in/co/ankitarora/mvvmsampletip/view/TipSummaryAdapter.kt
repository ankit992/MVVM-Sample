package `in`.co.ankitarora.mvvmsampletip.view

import `in`.co.ankitarora.mvvmsampletip.R
import `in`.co.ankitarora.mvvmsampletip.databinding.SavedTipCalculationsListItemBinding
import `in`.co.ankitarora.mvvmsampletip.viewmodel.TipCalculationSummaryItem
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class TipSummaryAdapter(val onItemSelected: (item: TipCalculationSummaryItem) -> Unit): RecyclerView.Adapter<TipSummaryAdapter.TipSummaryViewHolder>(){

    private  val tipCalculationSummaries = mutableListOf<TipCalculationSummaryItem>()

    fun updateList(updates: List<TipCalculationSummaryItem>){
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.size
    }

    override fun onBindViewHolder(holder: TipSummaryViewHolder, position: Int) {
        holder.bind(tipCalculationSummaries[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SavedTipCalculationsListItemBinding>(inflater, R.layout.saved_tip_calculations_list_item, parent,false)

        return TipSummaryViewHolder(binding)
    }


    inner class TipSummaryViewHolder(val binding: SavedTipCalculationsListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TipCalculationSummaryItem){
            binding.item = item
            binding.root.setOnClickListener {
                onItemSelected(item)
            }
            binding.executePendingBindings()
        }
    }

}