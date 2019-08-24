package com.example.codeexampleapp.article


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.codeexampleapp.R
import com.example.codeexampleapp.app.App
import com.example.codeexampleapp.app.AppViewModelFactory
import com.example.domain.models.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article.*
import javax.inject.Inject

class ArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: AppViewModelFactory

    lateinit var model: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        App.graph.injectArt(this)

        init ()

        adjust()
    }

    private fun init () {
        model = ViewModelProviders.of(this, factory).get(ArticleViewModel::class.java)
    }

    private fun adjust () {

        var id = intent.getIntExtra(Constants.ID, 0)

        model.getArticle(id)?.observe(this, Observer { it ->
            if (it != null) {
                var content = it.content?.substring(0, it.content!!.length - 13)

                text.text = content

                Picasso.get()
                    .load(it.urlToImage)
                    .error(R.drawable.placeholder)
                    .resize(0, 250)
                    .into(image)
            }
        })
    }

}
