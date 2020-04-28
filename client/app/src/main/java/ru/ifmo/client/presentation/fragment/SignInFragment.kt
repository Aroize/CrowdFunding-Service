package ru.ifmo.client.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.ifmo.client.R
import ru.ifmo.client.domain.field.validation.FieldValidationListener
import ru.ifmo.client.domain.field.validation.FieldValidator
import ru.ifmo.client.presentation.activity.AuthActivity

class SignInFragment : Fragment() {

    private lateinit var mainView: View
    private lateinit var loginField: EditText
    private lateinit var passwordField: EditText
    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = layoutInflater.inflate(R.layout.fragment_sign_in, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        loginField = mainView.findViewById(R.id.login_field)
        passwordField = mainView.findViewById(R.id.password_field)
        signInBtn = mainView.findViewById(R.id.sign_in_btn)
        signUpBtn = mainView.findViewById(R.id.sign_up_btn)
        val loginValidator = FieldValidator(Regex("[\\w]{2,}"), object : FieldValidationListener {
            override fun textIsValid(valid: Boolean) { signInBtn.isClickable = valid }
        })
        val passwordValidator = FieldValidator(Regex("[\\w]{7,15}"), object : FieldValidationListener {
            override fun textIsValid(valid: Boolean) { signInBtn.isClickable = valid }
        })
        loginField.addTextChangedListener(loginValidator)
        passwordField.addTextChangedListener(passwordValidator)
        signInBtn.setOnClickListener {
            Log.d(AuthActivity.TAG, "clicked sign in btn")
            (activity as AuthActivity).signIn(loginField.text.toString(), passwordField.text.toString())
        }
        signUpBtn.setOnClickListener {
            Log.d(AuthActivity.TAG, "clicked sign up btn")
            (activity as AuthActivity).showSignUpFragment()
        }
        signInBtn.isClickable = false
    }
}