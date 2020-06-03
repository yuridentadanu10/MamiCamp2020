package com.yuridentadanu.mamicamp2020.FragmentAndActivity.LoginAndRegister

import android.content.Intent
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuridentadanu.mamicamp2020.Const.DB_USERS
import com.yuridentadanu.mamicamp2020.Const.getUidUser
import com.yuridentadanu.mamicamp2020.FragmentAndActivity.Game.GameActivity
import com.yuridentadanu.mamicamp2020.MainActivity
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.User

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment(), View.OnClickListener {


    internal lateinit var btnRegister: Button
    internal lateinit var editTextName: EditText
    internal lateinit var editTextEmail: EditText
    internal lateinit var editTextPassword: EditText
    internal lateinit var editTextConfirm: EditText
    internal lateinit var progressBar: ProgressBar
    internal lateinit var btnHaveAccount: TextView


    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextName = view.findViewById(R.id.et_fullName)
        editTextEmail = view.findViewById(R.id.et_email)
        editTextPassword = view.findViewById(R.id.et_password)
        editTextConfirm = view.findViewById(R.id.et_passwordConfirm)
        btnRegister = view.findViewById(R.id.btn_register)
        btnHaveAccount = view.findViewById(R.id.tv_havaAcc)
        progressBar = view.findViewById(R.id.progressbar)
        progressBar.setVisibility(View.GONE)
        btnRegister.setOnClickListener(this)
        btnHaveAccount.setOnClickListener(this)

        mAuth = Firebase.auth
         db = Firebase.firestore
    }

    private fun registerUser() {
        progressBar.setVisibility(View.VISIBLE)
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirm = editTextConfirm.text.toString().trim()
        if (name.isEmpty()) {
            editTextName.error = getString(R.string.input_error_name)
            editTextName.requestFocus()
            return
        }
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
        if (confirm.isEmpty()) {
            editTextPassword.error = getString(R.string.input_error_password)
            editTextPassword.requestFocus()
            return
        }
        if (confirm != password) {
            editTextConfirm.error = getString(R.string.input_confirm_false)
            editTextConfirm.requestFocus()
            return
        }



        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = getUidUser()
                    startActivity(Intent(context, GameActivity::class.java))
                    val user = User(name, email)
                    db.collection(DB_USERS).document(uid).set(user)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    progressBar.setVisibility(View.GONE)
                } else {
                    Toast.makeText(context, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                    progressBar.setVisibility(View.GONE)
                }
            }
    }

    override fun onClick(v: View?) {
        val item_id = v?.id
        when (item_id) {
            R.id.btn_register -> registerUser()
            R.id.tv_havaAcc -> Navigation.findNavController(v)
                .navigate(R.id.action_registerFragment_to_loginFragment)


        }
    }

}
