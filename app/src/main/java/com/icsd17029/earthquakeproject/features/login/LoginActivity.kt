package com.icsd17029.earthquakeproject.features.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.features.map.MapActivity
import com.icsd17029.earthquakeproject.features.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: AppCompatActivity(){

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private lateinit var auth: FirebaseAuth


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        viewModel.userModel.observe(this,{
            Log.d(TAG, "I am here")
            if(auth.currentUser != null && it.civilEngineer){
                startActivity(Intent(this, MapActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            } else {
                Log.d(TAG,"Hello " + it.civilEngineer.toString())
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
            Log.d(TAG, "Hello $it")
        })
        setContentView(R.layout.activity_login)
        goToSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        button_login.setOnClickListener {
            if (validateForm()){
                viewModel.login(emailLogin.editText?.text.toString(), passwordLogin.editText?.text.toString())

            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this, MapActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = emailLogin.editText?.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailLogin.error = "Required."
            valid = false
        } else {
            emailLogin.error = null
        }

        val password = passwordLogin.editText?.text.toString()
        if (TextUtils.isEmpty(password)) {
            passwordLogin.error = "Required."
            valid = false
        } else {
            passwordLogin.error = null
        }

        return valid
    }


}
