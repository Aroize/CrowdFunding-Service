package ru.ifmo.client.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ifmo.client.R

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

    companion object {
        fun createIntent(packageContext: Context) = Intent(packageContext, NavigationActivity::class.java)
    }
}
