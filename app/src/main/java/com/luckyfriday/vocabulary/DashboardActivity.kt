package com.luckyfriday.vocabulary

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luckyfriday.vocabulary.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var bindingDashboard: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingDashboard = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bindingDashboard.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        if (username != null) {
            bindingDashboard.tvGreeting.text = getString(R.string.txt_greeting, username)
        }


    }
}