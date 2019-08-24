package com.example.codeexampleapp.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codeexampleapp.R
import com.example.data.db.ArticleEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_card.view.*
import java.lang.Exception

class MainAdapter(
    private val items: List<ArticleEntity>,
    private val listener: (Int) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_card, parent, false)

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val item = items[position]
        holder.text.text = if (item.description != null) item.description else context.getText(R.string.text_fail)

        val url = item.urlToImage

        url.let {
            Picasso.get()
                .load(item.urlToImage)
                .error(R.drawable.placeholder)
                .resize(0, 250)
                .into(holder.image, object : Callback{
                    override fun onError(e: Exception?) {
                        holder.progressBar.visibility = GONE
                        holder.image.visibility = VISIBLE
                    }

                    override fun onSuccess() {
                        holder.progressBar.visibility = GONE
                        holder.image.visibility = VISIBLE
                    }

                })
        }

        holder.view.setOnClickListener { listener(item.id) }
    }

    override fun getItemCount(): Int = items.size

    class MainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val text = view.text
        val image = view.image
        val progressBar = view.progressBar
    }
}



