package com.icsd17029.earthquakeproject.features.signin

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.internal.models.UserModel
import kotlinx.coroutines.launch


class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

     fun createUser(email: String, password: String,
                           userName: String, userSurname: String) {
        viewModelScope.launch {
             auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val userUID = auth.currentUser?.uid
                            val user = UserModel(userUID, userName, userSurname, false)
                            Log.d(TAG, user.toString())
                            db.collection("Users").add(user)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                            //auth.signOut()
                            //TODO (start another activity)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createAccEmail:failure", it.exception)
                            Toast.makeText(getApplication<Application>().baseContext, "Signing in failed.",
                                    Toast.LENGTH_SHORT).show()

                        }

                    }
        }
    }

    private fun addUserModel(user: UserModel) {
        db.collection("User").add(user)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}