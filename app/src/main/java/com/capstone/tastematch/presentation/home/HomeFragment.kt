package com.capstone.tastematch.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tastematch.response.ResponseItem
import com.capstone.tastematch.databinding.FragmentHomeBinding
import com.capstone.tastematch.ui.adapter.MenuAdapter
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuAdapter: MenuAdapter
    private val dataList: MutableList<ResponseItem> = mutableListOf()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = MenuAdapter(requireContext(), dataList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = menuAdapter
        }

        loadDataFromFirestore()
    }

    private fun loadDataFromFirestore() {
        firestore.collection("menu")
            .get()
            .addOnSuccessListener { querySnapshot ->
                dataList.clear()
                for (document in querySnapshot) {
                    val responseItem = document.toObject(ResponseItem::class.java)
                    dataList.add(responseItem)
                }
                menuAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }
}
