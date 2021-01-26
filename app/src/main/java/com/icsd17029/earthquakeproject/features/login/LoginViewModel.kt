package com.icsd17029.earthquakeproject.features.login


import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.internal.models.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    var user:  FirebaseUser? = null
    var userModel =  MutableLiveData<UserModel>()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            auth = Firebase.auth

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            user = auth.currentUser
                            Log.d(TAG, "signInWithEmail:success ${auth.currentUser?.uid}")
                            db.collection("Users").whereEqualTo("uid", auth.currentUser?.uid).get()
                                    .addOnSuccessListener { documents ->
                                        Log.d(TAG, "Documents is $documents with size ${documents.toObjects<UserModel>()}")
                                        for (document in documents) {
                                            userModel.value = document.toObject()
                                            Log.d(TAG, "${document.id} => ${document.data}")
                                            if (userModel.value!!.civilEngineer) {
                                                Log.d(TAG, "Login successful")
                                            } else {
                                                Log.d(TAG, "Login fail - User Not Authorized")
                                                auth.signOut()
                                            }
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.d(TAG, "get failed with ", exception)
                                    }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(getApplication<Application>().baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }

                    }
        }
    }

    private fun getUserInfo(){
        db.collection("User").whereEqualTo("UID", auth.currentUser?.uid).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        userModel = document.toObject()
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

}