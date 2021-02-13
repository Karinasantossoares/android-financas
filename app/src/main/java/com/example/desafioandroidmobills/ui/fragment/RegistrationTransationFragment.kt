package com.example.desafioandroidmobills.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.desafioandroidmobills.data.Transaction
import com.example.desafioandroidmobills.databinding.FragmentResgistrationTransactionBinding
import com.example.desafioandroidmobills.extension.addMask
import com.example.desafioandroidmobills.extension.setOnClickLeft
import com.example.desafioandroidmobills.extension.toDate
import com.example.desafioandroidmobills.extension.toText
import com.example.desafioandroidmobills.ui.component.DateDialog
import com.example.desafioandroidmobills.viewModel.TransactionViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

class RegistrationTransationFragment : Fragment() {
    private lateinit var binding: FragmentResgistrationTransactionBinding
    private val viewModel: TransactionViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResgistrationTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onDestroy()

        binding.etDate.addMask("NN/NN/NNNN")
        binding.etDate.setText(Date().toText())
        val dateDialog = DateDialog(requireContext())

        dateDialog.bindCalendar {
            binding.etDate.setText(it.toText())
        }

        binding.etDate.setOnClickLeft {
            dateDialog.showCalendar()
        }


        binding.btnRegister.setOnClickListener {
            val value = binding.etValue.text.toString()
            val description = binding.etDescription.text.toString()
            val date = binding.etDate.text.toString().toDate()
            val paid = binding.switchChangePaid.isChecked
            val transaction = Transaction(description = description, date = date, paid = paid)
            transaction.setValueNotEmpty(value)
            viewModel.saveOrUpdateTransaction(transaction)
        }

        viewModel.selectedTransactionsLiveData.observe(viewLifecycleOwner, Observer {
            binding.etValue.setText(it.value.toString())
            binding.etDate.setText(it.date?.toText())
            binding.etDescription.setText(it.description)
            binding.switchChangePaid.isChecked = it.paid == true
        })


        viewModel.successSaveLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        })

        viewModel.errorRegisterLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSelected()
    }
}