package com.yuridentadanu.mamicamp2020.LoginAndRegister

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuridentadanu.mamicamp2020.GameActivity
import com.yuridentadanu.mamicamp2020.MainActivity
import com.yuridentadanu.mamicamp2020.R

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    internal lateinit var btnLogin: Button
    internal lateinit var editTextEmail: EditText
    internal lateinit var editTextPassword: EditText
    internal lateinit var progressBar: ProgressBar
    internal lateinit var tv_register: TextView
    private lateinit var mAuth: FirebaseAuth
    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextEmail = view.findViewById(R.id.et_email);
        editTextPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        btnLogin.setOnClickListener(this);
        tv_register = view.findViewById(R.id.tv_dontHave)
        tv_register.setOnClickListener(this)

        mAuth = Firebase.auth
    }

    private fun LoginUser() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        if (email.isEmpty()) {
            editTextEmail.error = getString(R.string.input_error_email)
            editTextEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = getString(R.string.input_error_email_invalid)
            editTextEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword.error = getString(R.string.input_error_password)
            editTextPassword.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword.error = getString(R.string.input_error_password_length)
            editTextPassword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = mAuth.uid
                progressBar.visibility = View.GONE
                Log.d(TAG, "onComplete: $uid")
            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(context, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }



    }

    override fun onClick(v: View?) {
        val item_id = v?.id
        when (item_id) {
            R.id.btn_login -> LoginUser()
            R.id.tv_dontHave -> Navigation.findNavController(v)
                .navigate(R.id.action_loginFragment_to_registerFragment)


        }
    }



}
