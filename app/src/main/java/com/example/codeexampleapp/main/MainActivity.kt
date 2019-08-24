package com.example.codeexampleapp.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codeexampleapp.R
import com.example.codeexampleapp.app.App
import com.example.codeexampleapp.app.AppViewModelFactory
import com.example.codeexampleapp.article.ArticleActivity
import com.example.codeexampleapp.databinding.ActivityMainBinding
import com.example.codeexampleapp.supportive.ConnectionChecker
import com.example.data.db.ArticleEntity
import com.example.domain.models.Constants
import com.example.domain.models.articles.Articles
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: AppViewModelFactory

    lateinit var model: MainViewModel
    lateinit var binding: ActivityMainBinding
    var data: ArrayList<ArticleEntity> = ArrayList<ArticleEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.graph.injectMain(this)

        init()

        adjust()
    }

    private fun onItemClicked(id: Int) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(Constants.ID, id)
        startActivity(intent)
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView.adapter = MainAdapter(data, ::onItemClicked, this)
    }

    private fun adjust() {
        binding.model = model
        binding.lifecycleOwner = this

        model.getNews(Constants.SOURSE)?.observe(this, Observer { it ->
            if (it!= null) {
                    data.clear()
                    data.addAll(it)
                    recyclerView.adapter!!.notifyDataSetChanged()
            } else {
                showMessageLoadError()
            }
        })


        fab.setOnClickListener {
            if (ConnectionChecker(this).check()) {
                model.updateNews(Constants.SOURSE)
            } else {
                showMessageConnectionError()
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility === View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility !== View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    fun showMessageConnectionError() {
        Snackbar.make(coordinator, resources.getText(R.string.connection_fail), Snackbar.LENGTH_LONG).show()
    }

    fun showMessageLoadError() {
        Snackbar.make(coordinator, resources.getText(R.string.somth_went_wrong), Snackbar.LENGTH_LONG).show()
    }
}
