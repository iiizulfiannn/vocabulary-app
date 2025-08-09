package com.luckyfriday.vocabulary

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.luckyfriday.vocabulary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        if (savedUsername != null) {
            // direct to main app
            navigateToDashboard(savedUsername)
        } else {
            showOnboard()
        }
    }

    private fun showOnboard() {
        bindingMain.etNameOnboarding.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                bindingMain.tvTitle.text = getString(R.string.tell_me_your_name)
                bindingMain.btnStart.isVisible = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                bindingMain.tvTitle.text = getString(R.string.introduction_your_name)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bindingMain.tvTitle.text = getString(R.string.tell_me_your_name)
            }
        })
        bindingMain.btnStart.setOnClickListener {
            val username = bindingMain.etNameOnboarding.text.toString()
            saveName(username)
            navigateToDashboard(username)
        }
    }

    private fun saveName(username: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("USERNAME", username)
//        editor.apply()
        sharedPreferences.edit {
            putString("USERNAME", username)
        }
    }

    private fun navigateToDashboard(username: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }
}