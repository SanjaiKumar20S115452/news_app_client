package com.sanjai.newsapiclientpart2.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjai.newsapiclientpart2.R
import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.databinding.FragmentNewsBinding
import com.sanjai.newsapiclientpart2.databinding.NewsListItemBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private val callBack = object: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount() = differ.currentList.size

    inner class MyViewHolder(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            binding.tvSource.text = article.source?.name
            Glide.with(binding.ivArticleImage.context).load(article.urlToImage).into(binding.ivArticleImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }

        }
    }

    private var onItemClickListener: ((Article)-> Unit)? = null

    fun setOnItemClickListener(listener: (Article)->Unit) {
        onItemClickListener = listener
    }

}