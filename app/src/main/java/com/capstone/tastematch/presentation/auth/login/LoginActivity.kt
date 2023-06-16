package com.capstone.tastematch.presentation.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tastematch.databinding.ActivityLoginBinding
import com.capstone.tastematch.presentation.auth.register.RegisterActivity
import com.capstone.tastematch.presentation.formInput.DataUserActivity
import com.capstone.tastematch.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        checkUserData()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
        playAnimation()
    }

    private fun checkUserData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userDocumentRef = FirebaseFirestore.getInstance().collection("user").document(userId)

            userDocumentRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // User data exists, already filled in
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        // User data doesn't exist, navigate to the data entry screen
                        val intent = Intent(this, DataUserActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to check user data!", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error checking user data: ${exception.message}")
                }
        }
    }

    private fun playAnimation() {
        val loginTitle = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val editTextLogin = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val editTextPassword = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(DURATION2.toLong())

        AnimatorSet().apply {
            playSequentially(loginTitle, editTextLogin, editTextPassword, btnLogin)
            startDelay = DURATION2.toLong()
            start()
        }
    }

    companion object {
        const val DURATION2 = 400
    }
}
