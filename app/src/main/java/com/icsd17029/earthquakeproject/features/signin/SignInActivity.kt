package com.icsd17029.earthquakeproject.features.signin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.R
import kotlinx.android.synthetic.main.activity_signin.*



import android.content.ContentValues.TAG
import android.content.Intent
import android.text.Editable
import com.icsd17029.earthquakeproject.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*


class SignInActivity: AppCompatActivity() {
    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContentView(R.layout.activity_signin)
        goToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
        signin_submit.setOnClickListener {
            if(validateForm()){
                val userEmail = emailSignin.editText?.text.toString()
                val userPassword = passwordSignin.editText?.text.toString()

                val userName = nameSignin.editText?.text.toString()
                val userSurname = surnameSignin.editText?.text.toString()
                clearForm()
                viewModel.createUser(userEmail, userPassword, userName, userSurname)
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = emailSignin.editText?.text.toString()
        Log.d(TAG, email)
        if (TextUtils.isEmpty(email) || email=="") {
            emailSignin.error = "Required."
            valid = false
        } else {
            emailSignin.error = null
        }

        val password = passwordSignin.editText?.text.toString()


        if(TextUtils.isEmpty(password) || password=="") {
            passwordSignin.error = "Required."
            valid = false
        }
        else if(password.length < 6) {
            passwordSignin.error = "Password must be bigger than 6 characters."
            valid = false
        }
        else {
            passwordSignin.error = null
        }


        val name = nameSignin.editText?.text.toString()
        if (TextUtils.isEmpty(name) || name=="") {
            nameSignin.error = "Required."
            valid = false
        } else {
            nameSignin.error = null
        }

        val surname = surnameSignin.editText?.text.toString()
        if (TextUtils.isEmpty(surname) || surname=="") {
            surnameSignin.error = "Required."
            valid = false
        } else {
            surnameSignin.error = null
        }
        Log.d(TAG, valid.toString())
        return valid
    }

    private fun clearForm(){
        emailSignin.editText?.setText("")
        passwordSignin.editText?.setText("")
        nameSignin.editText?.setText("")
        surnameSignin.editText?.setText("")
    }

}