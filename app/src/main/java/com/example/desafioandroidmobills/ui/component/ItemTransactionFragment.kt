package com.example.desafioandroidmobills.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.desafioandroidmobills.data.Transaction
import com.example.desafioandroidmobills.databinding.FragmentItemTransactionBinding

class ItemTransactionFragment : Fragment() {
    lateinit var binding: FragmentItemTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelableArrayList<Transaction>(EXTRA_TRANSACTION)?.toList()?.let {

        }
    }

    companion object {
        private const val EXTRA_TRANSACTION = "EXTRA_TRANSACTION"

        fun newInstance(transaction: List<Transaction>): ItemTransactionFragment {
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_TRANSACTION, ArrayList(transaction))
            val fragment = ItemTransactionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}