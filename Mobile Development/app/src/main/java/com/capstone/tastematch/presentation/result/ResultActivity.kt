package com.capstone.tastematch.presentation.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tastematch.R
import com.capstone.tastematch.response.ResponseItem
import com.capstone.tastematch.databinding.ActivityResultBinding
import com.capstone.tastematch.ui.adapter.SearchAdapter
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var dataList: MutableList<ResponseItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var tv:TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var progressDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ingredientClass = intent.getStringExtra("ingredientClass")

        tv = binding.textView
        recyclerView = binding.recyclerView
        firestore = FirebaseFirestore.getInstance()
        dataList = mutableListOf()
        adapter = SearchAdapter(this, dataList)

        tv.text = ingredientClass.toString()

        setupRecyclerView()
        loadDataFromFirestore()
        ingredientClass?.let { filterDataList(it) }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun loadDataFromFirestore() {
        progressDialog = createProgressDialog()
        progressDialog.show()

        firestore.collection("menu")
            .limit(10) // Limit to 10 documents
            .get()
            .addOnSuccessListener { querySnapshot ->
                dataList.clear()
                for (document in querySnapshot) {
                    val responseItem = document.toObject(ResponseItem::class.java)
                    dataList.add(responseItem)
                }
                adapter.setDataList(dataList)
                progressDialog.dismiss()
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Failed to load data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun filterDataList(query: String) {
        val filteredList = dataList.filter { responseItem ->
            responseItem.bahan?.contains(query) == true
        }.take(10)
        adapter.setDataList(filteredList)
    }

    private fun createProgressDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setView(layoutInflater.inflate(R.layout.progress_layout, null))
        builder.setCancelable(false)
        return builder.create()
    }
}
