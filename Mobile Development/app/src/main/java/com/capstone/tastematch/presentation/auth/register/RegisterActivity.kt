package com.capstone.tastematch.presentation.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tastematch.databinding.ActivityRegisterBinding
import com.capstone.tastematch.presentation.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvLoginHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            val confirmPass = binding.confirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
        playAnimation()
    }

    private fun playAnimation() {
        val registerTitle = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val registerEmail = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val registerPassword = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val confirmPassword = ObjectAnimator.ofFloat(binding.confirmPassword, View.ALPHA, 1f).setDuration(DURATION2.toLong())
        val registerButton = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(DURATION2.toLong())

        AnimatorSet().apply {
            playSequentially(registerTitle, registerEmail, registerPassword, confirmPassword, registerButton)
            startDelay = DURATION2.toLong()
            start()
        }
    }

    companion object {
        const val DURATION2 = 400
    }
}