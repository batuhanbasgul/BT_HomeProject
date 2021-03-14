package com.example.bt_homeproject.db.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.bt_homeproject.R
import com.example.bt_homeproject.db.data_model.User
import com.example.bt_homeproject.view.activity.LoginActivity
import com.example.bt_homeproject.view.activity.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class AuthenticationUtils {
    companion object{
        private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()

        fun createAccount(context: Context, user: User){
            firebaseAuth.createUserWithEmailAndPassword(user.userEmail, user.userPassword).addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    (context as Activity).progressBarSignUp.visibility = View.INVISIBLE
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    (context as Activity).finish()
                } else {
                    try {
                        throw it.exception!!
                    } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(context, R.string.sign_up_weak_password, Toast.LENGTH_LONG).show()
                    } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, R.string.sign_up_email_error, Toast.LENGTH_LONG).show()
                    } catch (existEmail: FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, R.string.sign_up_email_exists, Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, R.string.sign_up_error, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        fun login(context: Context, user: User){
            firebaseAuth.signInWithEmailAndPassword(user.userEmail, user.userPassword).addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    (context as Activity).progressBarLogin.visibility = View.INVISIBLE
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as Activity).finish()
                } else {
                    try {
                        throw it.exception!!
                    } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                        Toast.makeText(context, R.string.sign_in_invalid_mail, Toast.LENGTH_LONG).show()
                    } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, R.string.sign_in_invalid_password,Toast.LENGTH_LONG).show()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(context, R.string.sign_in_login_error, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        fun logOut(context: Context){
            firebaseAuth.signOut()
            context.startActivity(Intent(context,LoginActivity::class.java))
            (context as Activity).finish()
        }
    }
}