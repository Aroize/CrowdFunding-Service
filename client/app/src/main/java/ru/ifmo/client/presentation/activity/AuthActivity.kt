package ru.ifmo.client.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.ifmo.client.R
import ru.ifmo.client.api.AccessToken
import ru.ifmo.client.api.ApiCallback
import ru.ifmo.client.api.ApiManager
import ru.ifmo.client.api.md5
import ru.ifmo.client.data.models.User
import ru.ifmo.client.presentation.fragment.SignInFragment
import ru.ifmo.client.presentation.fragment.SignUpFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ApiManager.isSignIn()) {
            startActivity(NavigationActivity.createIntent(this))
        }
        setContentView(R.layout.activity_auth)
        findViewById<View>(R.id.fragment_container).post {
            showSignInFragment()
        }
    }

    fun signIn(login: String, password: String) {
        val callback = object : ApiCallback<AccessToken> {
            override fun onError(e: Throwable) = Toast.makeText(this@AuthActivity, e.message, Toast.LENGTH_LONG).show()

            override fun onSuccess(result: AccessToken) = startActivity(NavigationActivity.createIntent(this@AuthActivity))
        }
        ApiManager.signIn(login, password.md5(), callback)
    }

    fun signUp(login: String, password: String) {
        val callback = object : ApiCallback<User> {

            override fun onError(e: Throwable) = Toast.makeText(this@AuthActivity, e.message, Toast.LENGTH_SHORT).show()

            override fun onSuccess(result: User) = showSignInFragment()
        }
        ApiManager.signUp(login, password.md5(), callback)
    }

    private fun showSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignInFragment())
            .commitNow()
    }

    fun showSignUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignUpFragment())
            .commitNow()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) is SignUpFragment)
            showSignInFragment()
        else
            super.onBackPressed()
    }

    companion object {
        const val TAG = "AuthActivity.Tag"

        fun createIntent(packageContext: Context) = Intent(packageContext, AuthActivity::class.java)
    }
}