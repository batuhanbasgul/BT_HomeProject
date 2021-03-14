package com.example.bt_homeproject.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bt_homeproject.R
import com.example.bt_homeproject.db.data_model.User
import com.example.bt_homeproject.db.service.AuthenticationUtils.Companion.login
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Toolbar
        toolbarLogin.title=resources.getString(R.string.app_name)
        toolbarLogin.subtitle=resources.getString(R.string.app_sign_in)
        setSupportActionBar(toolbarLogin)

        //Remember mail and password
        checkRememberMe()

        //Sign Up
        textViewLoginSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        //Login
        buttonLogin.setOnClickListener {
            progressBarLogin.visibility = View.VISIBLE
            if(isNetworkConnected()){
                if(checkEmptySpaces()){
                    val user = User("",
                            editTextLoginEmail.text.toString(),
                            editTextLoginPassword.text.toString())
                    login(this@LoginActivity,user)
                    setRememberMe()
                }
            }else{
                Toast.makeText(this,resources.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                progressBarLogin.visibility = View.INVISIBLE
            }
        }
    }

    private fun checkEmptySpaces():Boolean{
        return when {
            editTextLoginEmail.text.toString().isNullOrEmpty() -> {
                editTextLoginEmail.error = resources.getString(R.string.empty_email)
                false
            }
            editTextLoginPassword.text.toString().isNullOrEmpty() -> {
                editTextLoginPassword.error = resources.getString(R.string.empty_password)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun setRememberMe(){
        //define PRIVATE Shared Preferences for remember me
        val sharedPreferences:SharedPreferences = getSharedPreferences("remember",MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

        if (switchRememberMe.isChecked){
            sharedPreferencesEditor.putBoolean("isChecked", true)
            sharedPreferencesEditor.putString("email", editTextLoginEmail.text.toString())
            sharedPreferencesEditor.putString("password", editTextLoginPassword.text.toString())
            sharedPreferencesEditor.commit()
        }else{
            sharedPreferencesEditor.putBoolean("isChecked", false)
            sharedPreferencesEditor.putString("email", null)
            sharedPreferencesEditor.putString("password", null)
            sharedPreferencesEditor.commit()
        }
    }

    private fun checkRememberMe() {
        val sharedPreferences:SharedPreferences = getSharedPreferences("remember",MODE_PRIVATE)
        if(sharedPreferences.getBoolean("isChecked",false)){
            editTextLoginEmail.setText(sharedPreferences.getString("email",""))
            editTextLoginPassword.setText(sharedPreferences.getString("password",""))
        }
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

}