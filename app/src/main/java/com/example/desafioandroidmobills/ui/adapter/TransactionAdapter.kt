package com.example.desafioandroidmobills.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.data.Transaction
import com.example.desafioandroidmobills.databinding.ItemListBinding
import com.example.desafioandroidmobills.extension.toText

class TransactionAdapter(
    private val listTransaction: List<Transaction>,
    private val onClickDelete: (Transaction) -> Unit,
    private val onClickEdit: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(listTransaction[position])
    }

    override fun getItemCount(): Int = listTransaction.count()


    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)

        fun bindView(transaction: Transaction) {
            binding.tvDateOfMonth.text = transaction.date?.toText()
            binding.tvDescription.text = transaction.description
            binding.tvValue.text = transaction.value.toString()
            if(transaction.paid == true){
                binding.myCradView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.slowRed))
            }else{
                binding.myCradView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.slowGreen))
            }
            binding.tvDelete.setOnClickListener {
                onClickDelete.invoke(transaction)
            }
            binding.tvEdit.setOnClickListener {
                onClickEdit.invoke(transaction)
            }

        }
    }


}
