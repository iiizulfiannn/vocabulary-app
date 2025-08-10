package com.luckyfriday.vocabulary

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luckyfriday.vocabulary.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var bindingAddActivity: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAddActivity = ActivityAddBinding.inflate(layoutInflater)
        setContentView(bindingAddActivity.root)
    }
}