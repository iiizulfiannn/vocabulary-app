package com.luckyfriday.vocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyfriday.vocabulary.R
import com.luckyfriday.vocabulary.databinding.ItemCategoryBinding
import com.luckyfriday.vocabulary.model.WordCategory

class CategoryAdapter(
    private val mList: List<WordCategory>,
    selectedCategory: WordCategory,
    private val onSelectedCategory: (WordCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var currentCategory = selectedCategory

    class CategoryViewHolder(private val itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun bind(
            item: WordCategory,
            selectedCategory: WordCategory,
            onSelectedCategory: (WordCategory) -> Unit
        ) {
            itemCategoryBinding.tvCategory.text = item.title
            if (item.ordinal == selectedCategory.ordinal) {
                itemCategoryBinding.border.setCardBackgroundColor(
                    itemCategoryBinding.root.context.getColor(
                        R.color.colorBackgroundStartButton
                    )
                )
            } else {
                itemCategoryBinding.border.setCardBackgroundColor(
                    itemCategoryBinding.root.context.getColor(
                        R.color.colorCardBackground
                    )
                )
            }
            itemCategoryBinding.root.setOnClickListener {
                onSelectedCategory(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(mList[position], currentCategory, onSelectedCategory)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    internal fun updateSelectedCategory(selectedCategory: WordCategory) {
        notifyItemChanged(currentCategory.ordinal)
        currentCategory = selectedCategory
        notifyItemChanged(selectedCategory.ordinal)
    }


}