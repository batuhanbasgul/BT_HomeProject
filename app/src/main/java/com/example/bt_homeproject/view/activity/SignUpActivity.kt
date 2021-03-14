package com.example.bt_homeproject.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bt_homeproject.R
import com.example.bt_homeproject.db.data_model.User
import com.example.bt_homeproject.db.service.AuthenticationUtils.Companion.createAccount
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Toolbar
        toolbarSignUp.title = resources.getString(R.string.app_title)
        toolbarSignUp.subtitle = resources.getString(R.string.app_sign_up)
        setSupportActionBar(toolbarSignUp)

        //Sign Up
        buttonSignUp.setOnClickListener {
            progressBarSignUp.visibility = View.VISIBLE
            if(isNetworkConnected()){
                if(checkEmptySpaces()){
                    val user = User("",
                            editTextSignUpEmail.text.toString(),
                            editTextSignUpPassword.text.toString())
                    if(user.userPassword != editTextSignUpPassword2.text.toString()){
                        Toast.makeText(this@SignUpActivity,resources.getString(R.string.sign_up_password_no_match),Toast.LENGTH_LONG).show()
                        editTextSignUpPassword2.error = resources.getString(R.string.sign_up_password_no_match)
                    }else{
                        createAccount(this@SignUpActivity,user)
                    }
                }
            }else{
                Toast.makeText(this,resources.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                progressBarSignUp.visibility = View.INVISIBLE
            }

        }
    }

    private fun checkEmptySpaces(): Boolean {
        return when {
            editTextSignUpEmail.text.toString().isNullOrEmpty() -> {
                editTextSignUpEmail.error = resources.getString(R.string.empty_email)
                false
            }
            editTextSignUpPassword.text.toString().isNullOrEmpty() -> {
                editTextSignUpPassword.error = resources.getString(R.string.empty_password)
                false
            }
            editTextSignUpPassword2.text.toString().isNullOrEmpty() -> {
                editTextSignUpPassword2.error = resources.getString(R.string.empty_password)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        finish()
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}