package com.capstone.tastematch.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.capstone.tastematch.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var profileImageView: ImageView
    private lateinit var emailTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser!!
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileImageView = view.findViewById(R.id.profileImageView)
        emailTextView = view.findViewById(R.id.emailTextView)
        nameTextView = view.findViewById(R.id.nameTextView)
        ageTextView = view.findViewById(R.id.ageTextView)
        heightTextView = view.findViewById(R.id.heightTextView)
        weightTextView = view.findViewById(R.id.weightTextView)

        loadProfileData()

        return view
    }

    private fun loadProfileData() {
        val email = currentUser.email
        emailTextView.text = email
        val userId = currentUser.uid
        firestore.collection("user").document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("name")
                    val age = documentSnapshot.getString("Age")
                    val height = documentSnapshot.getString("Height")
                    val weight = documentSnapshot.getString("Weight")

                    nameTextView.text = "$name "
                    ageTextView.text = "$age Th"
                    heightTextView.text = "$height Cm"
                    weightTextView.text = "$weight Kg"
                }
            }
            .addOnFailureListener { e ->
            }
    }
}
