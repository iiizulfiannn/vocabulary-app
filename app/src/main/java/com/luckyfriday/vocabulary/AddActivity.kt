package com.luckyfriday.vocabulary

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.luckyfriday.vocabulary.databinding.ActivityAddBinding
import com.luckyfriday.vocabulary.db.SqlDbHandler
import com.luckyfriday.vocabulary.model.WordCategory
import androidx.appcompat.R

class AddActivity : AppCompatActivity() {

    private lateinit var bindingAddActivity: ActivityAddBinding
    private val sqlDbHandler = SqlDbHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAddActivity = ActivityAddBinding.inflate(layoutInflater)
        setContentView(bindingAddActivity.root)
        setSpinner()

        bindingAddActivity.btnSave.setOnClickListener {
            if (
                bindingAddActivity.etName.text.isNullOrEmpty() ||
                bindingAddActivity.etMeaning.text.isNullOrEmpty() ||
                bindingAddActivity.spinnerCategory.selectedItem.toString().isEmpty()
            ) return@setOnClickListener

            sqlDbHandler.addVocab(
                bindingAddActivity.etName.text.toString(),
                bindingAddActivity.etMeaning.text.toString(),
                bindingAddActivity.spinnerCategory.selectedItem.toString()
            )
            setResult(123, Intent())
            finish()
        }

        bindingAddActivity.btnDiscard.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            getCategoryList()
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        bindingAddActivity.spinnerCategory.adapter = adapter
    }

    private fun getCategoryList(): List<String> {
        return WordCategory.entries.map {
            if (it == WordCategory.ALL_CATEGORY) {
                ""
            } else {
                it.title
            }
        }
    }
}