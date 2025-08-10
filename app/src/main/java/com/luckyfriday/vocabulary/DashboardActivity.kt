package com.luckyfriday.vocabulary

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckyfriday.vocabulary.adapter.CategoryAdapter
import com.luckyfriday.vocabulary.databinding.ActivityDashboardBinding
import com.luckyfriday.vocabulary.db.SqlDbHandler
import com.luckyfriday.vocabulary.model.WordCategory

class DashboardActivity : AppCompatActivity() {

    private lateinit var bindingDashboard: ActivityDashboardBinding
    private lateinit var adapterCategory: CategoryAdapter
    private var selectedCategory = WordCategory.ALL_CATEGORY
    private val sqlDbHandler = SqlDbHandler(this)

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

        setCategoryList()
    }

    fun setCategoryList() {
        val categoryList = WordCategory.entries.toList()
        adapterCategory = CategoryAdapter(categoryList, selectedCategory) {
            selectedCategory = it
            refreshListCategoryAndVocab(it)
        }
        bindingDashboard.rvCategory.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterCategory
        }
    }

    private fun refreshListCategoryAndVocab(wordCategory: WordCategory) {
        val listWord = if (wordCategory == WordCategory.ALL_CATEGORY) {
            sqlDbHandler.getVocab()
        } else {
            sqlDbHandler.getVocab().filter { it.category == wordCategory }
        }
        adapterCategory.updateSelectedCategory(selectedCategory)
    }
}