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

class SignUpFragment : Fragment() {

    private lateinit var mainView: View
    private lateinit var loginField: EditText
    private lateinit var passwordField: EditText
    private lateinit var passwordRepeatField: EditText
    private lateinit var signUpBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        loginField = mainView.findViewById(R.id.login_field)
        passwordField = mainView.findViewById(R.id.password_field)
        passwordRepeatField = mainView.findViewById(R.id.password_repeat_field)
        signUpBtn = mainView.findViewById(R.id.sign_up_btn)
        val loginValidator = FieldValidator(Regex("[\\w]{2,}"), object : FieldValidationListener {
            override fun textIsValid(valid: Boolean)  { signUpBtn.isClickable = valid }
        })
        loginField.addTextChangedListener(loginValidator)
        val passwordValidator = FieldValidator(Regex("[\\w]{7,15}"), object : FieldValidationListener {
            override fun textIsValid(valid: Boolean) { signUpBtn.isClickable = valid }
        })
        passwordField.addTextChangedListener(passwordValidator)
        signUpBtn.setOnClickListener {
            if (passwordField.text.toString() != passwordRepeatField.text.toString()) {
                Log.d(AuthActivity.TAG, "passwords don't match each other")
                Toast.makeText(activity, R.string.repeat_must_equals, Toast.LENGTH_LONG).show()
            } else {
                Log.d(AuthActivity.TAG, "delegating sign up task")
                (activity as AuthActivity).signUp(loginField.text.toString(), passwordField.text.toString())
            }
        }
        signUpBtn.isClickable = false
    }
}