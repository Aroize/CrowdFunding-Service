package ru.ifmo.client.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.ifmo.client.R
import ru.ifmo.client.presentation.fragment.AccountFragment
import ru.ifmo.client.presentation.fragment.FundsFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var accountBtn: View
    private lateinit var fundsBtn: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        initViews()
    }

    private fun initViews() {
        accountBtn = findViewById(R.id.account_btn)
        fundsBtn = findViewById(R.id.funds_btn)
        accountBtn.setOnClickListener { showAccountFragment() }
        fundsBtn.setOnClickListener { showFundsFragment() }
    }

    private fun showFundsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FundsFragment())
            .commitNow()
    }

    private fun showAccountFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AccountFragment())
            .commitNow()
    }

    companion object {
        fun createIntent(packageContext: Context) = Intent(packageContext, NavigationActivity::class.java)
    }
}
