package com.capstone.tastematch.presentation.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tastematch.databinding.ActivitySplashscreenBinding
import com.capstone.tastematch.presentation.auth.login.LoginActivity
import com.capstone.tastematch.presentation.formInput.DataUserActivity
import com.capstone.tastematch.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "SplashscreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        playAnimation()

        Handler().postDelayed({
            navigateToNextScreen()
        }, 2500)
    }

    private fun playAnimation() {
        val title1 = ObjectAnimator.ofFloat(binding.tvNameApp, View.ALPHA, 1f).setDuration(1000)
        val title2 = ObjectAnimator.ofFloat(binding.tvDescApp, View.ALPHA, 1f).setDuration(1500)

        AnimatorSet().apply {
            playSequentially(title1, title2)
            start()
        }
    }

    private fun navigateToNextScreen() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val userDocumentRef = FirebaseFirestore.getInstance().collection("user").document(userId)

            userDocumentRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, DataUserActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to check user data!", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error checking user data: ${exception.message}")
                }
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
