package com.luckyfriday.vocabulary

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luckyfriday.vocabulary.adapter.CategoryAdapter
import com.luckyfriday.vocabulary.adapter.VocabAdapter
import com.luckyfriday.vocabulary.databinding.ActivityDashboardBinding
import com.luckyfriday.vocabulary.db.SqlDbHandler
import com.luckyfriday.vocabulary.model.ListWordState
import com.luckyfriday.vocabulary.model.WordCategory

class DashboardActivity : AppCompatActivity() {

    private lateinit var bindingDashboard: ActivityDashboardBinding
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapterVocab: VocabAdapter
    private var selectedListState = ListWordState.NORMAL
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

        buttonDeleteAdd()
        setCategoryList()
        setVocabList()

        bindingDashboard.ivAdd.setOnClickListener {
            navigateToNewVocab()
        }
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
        adapterVocab.refreshList(listWord)
        adapterCategory.updateSelectedCategory(selectedCategory)
    }

    private fun navigateToNewVocab() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, 123)
    }

    private fun setVocabList() {
        adapterVocab = VocabAdapter(sqlDbHandler.getVocab(), selectedListState) {
            positionTobeRemoved ->
            removedAndRefreshed(positionTobeRemoved)

        }
        bindingDashboard.rvVocab.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = adapterVocab
        }
    }

    private fun removedAndRefreshed(position: Int) {
        sqlDbHandler.deleteVocab(position)
        adapterVocab.refreshList(sqlDbHandler.getVocab())

        if (sqlDbHandler.getVocab().isNotEmpty()) {
            buttonCancel()
        } else {
            buttonDeleteAdd()
        }

    }

    private fun buttonCancel() {
        bindingDashboard.btnCancel.isVisible = true
        bindingDashboard.ivDelete.isVisible = false
        bindingDashboard.ivAdd.isVisible = true
    }

    private fun buttonDeleteAdd() {
        bindingDashboard.btnCancel.isVisible = false
        bindingDashboard.ivAdd.isVisible = true
        bindingDashboard.ivDelete.isVisible = sqlDbHandler.getVocab().isNotEmpty()
    }
}