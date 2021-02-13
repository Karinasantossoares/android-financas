package com.example.desafioandroidmobills.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.databinding.FragmentTransactionBinding
import com.example.desafioandroidmobills.ui.adapter.TransactionAdapter
import com.example.desafioandroidmobills.viewModel.TransactionViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TransactionsFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding

    private val viewModel: TransactionViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onDestroy()
        viewModel.findAllTransactions()

        binding.pieChart.setCenterColor(R.color.white)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_transactionsFragment_to_authenticationActivity)
            requireActivity().finish()

        }

        binding.srTransaction.setOnRefreshListener {
            viewModel.findAllTransactions()
        }

        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment_to_registrationTransationFragment2)
        }

        viewModel.pieChartOptions.observe(viewLifecycleOwner, Observer {
            binding.pieChart.setSliceColor(intArrayOf(R.color.slowGreen, R.color.slowRed))
            binding.pieChart.setDataPoints(it)
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.srTransaction.isRefreshing = it
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.container.isVisible = false
            binding.rvMyRecycler.isVisible = false
            binding.tvNotTransation.isVisible = true
            binding.tvNotTransation.text = it
        })

        viewModel.successListTransactionsLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNotTransation.isVisible = false
            binding.rvMyRecycler.isVisible = true
            binding.container.isVisible = true
            val adapter = TransactionAdapter(it,
                onClickDelete = { transactionDelete ->
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.message_delete)
                        .setPositiveButton(R.string.messag_ok) { _, _ ->
                            viewModel.deleteTransaction(transactionDelete)
                        }.create().show()
                },
                onClickEdit = { transaction ->
                    viewModel.selectTransaction(transaction)
                    findNavController().navigate(R.id.action_transactionsFragment_to_registrationTransationFragment2)
                })
            binding.rvMyRecycler.adapter = adapter
        })


        viewModel.deleteTransactionSuccessLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }
}