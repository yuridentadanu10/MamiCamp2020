package com.yuridentadanu.mamicamp2020.LoginAndRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
    }

    override fun onClick(v: View?) {
        val item_id = v?.id
        when (item_id) {
            R.id.btn_login -> Toast.makeText(context, "recipe", Toast.LENGTH_SHORT).show()
            R.id.tv_dontHave -> Navigation.findNavController(v)
                .navigate(R.id.action_loginFragment_to_registerFragment)


        }
    }

}
