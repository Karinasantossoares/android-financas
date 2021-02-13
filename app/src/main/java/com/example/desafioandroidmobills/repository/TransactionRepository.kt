package com.example.desafioandroidmobills.repository

import android.content.Context
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.desafioandroidmobills.data.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Exception


class TransactionRepository(
    context: Context,
    reference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) {

    private val database by lazy {
        reference.child(TRANSACTION)
    }

    fun saveTransaction(
        transaction: Transaction,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        val nextId = database.push().key
        transaction.id = nextId
        database.child(firebaseAuth.uid.toString()).child(nextId.toString()).setValue(transaction)
            .addOnSuccessListener {
                success.invoke()
            }.addOnFailureListener {
            error.invoke(it)
        }
    }

    fun updateTransaction(
        transaction: Transaction,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        transaction.id?.let { id ->
            database.child(firebaseAuth.uid.toString()).child(id).setValue(transaction).addOnSuccessListener {
                success.invoke()
            }.addOnFailureListener {
                error.invoke(it)
            }
        }
    }

    fun deleteTransaction(
        idTransaction: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        database
            .child(firebaseAuth.uid.toString())
            .child(idTransaction)
            .removeValue()
            .addOnSuccessListener { success.invoke() }
            .addOnFailureListener { error.invoke(it) }
    }

    fun findAllTransaction(
        success: (List<Transaction>) -> Unit,
        error: (DatabaseException) -> Unit
    ) {
        database.child(firebaseAuth.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull {
                    it.getValue(Transaction::class.java)
                }
                success.invoke(list)
            }

            override fun onCancelled(errorFirebase: DatabaseError) {
                error.invoke(errorFirebase.toException())
            }
        })
    }


    companion object {
        private const val TRANSACTION = "transaction"
    }
}