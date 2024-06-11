package com.kishan.wallypic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishan.wallypic.R
import com.kishan.wallypic.databinding.CategoryItemRowBinding
import com.kishan.wallypic.model.Category
import com.kishan.wallypic.utils.CategoryWallInteractionListener

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val listener: CategoryWallInteractionListener,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
   inner class CategoryViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val binding = CategoryItemRowBinding.bind(view)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_row,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.binding.apply {
            categoryName.text = currentCategory.categoryName

            Glide.with(holder.itemView.context)
                .load(currentCategory.imageUrl)
                .centerCrop()
                .error(androidx.appcompat.R.color.material_deep_teal_200)
                .into(categoryImageView)
        }

        holder.itemView.setOnClickListener {
            listener.onCategoryItemClick(currentCategory,it)
        }
    }


}