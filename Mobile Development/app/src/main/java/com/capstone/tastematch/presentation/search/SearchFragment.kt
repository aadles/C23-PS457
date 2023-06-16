package com.capstone.tastematch.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tastematch.R
import com.capstone.tastematch.response.ResponseItem
import com.capstone.tastematch.ui.adapter.SearchAdapter
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    private lateinit var adapter: SearchAdapter
    private lateinit var dataList: MutableList<ResponseItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.search_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        dataList = mutableListOf()
        adapter = SearchAdapter(requireContext(), dataList)

        setupRecyclerView()
        setupSearchView()

        progressDialog = createProgressDialog(requireContext())

        loadDataFromFirestore()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterDataList(newText)
                return true
            }
        })
    }

    private fun loadDataFromFirestore() {
        progressDialog.show()

        firestore.collection("menu")
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
                    requireContext(),
                    "Failed to load data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun filterDataList(query: String) {
        val filteredList = dataList.filter { responseItem ->
            responseItem.menu?.contains(query, ignoreCase = true) == true
        }
        adapter.setDataList(filteredList)
    }

    private fun createProgressDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.progress_layout)
        builder.setCancelable(false)
        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
    }
}
