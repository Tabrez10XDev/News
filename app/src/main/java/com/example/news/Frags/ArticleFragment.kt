package com.example.news.Frags

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel : NewsVM



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
d("Lj","help")
        var args : ArticleFragmentArgs = ArticleFragmentArgs.fromBundle(this.arguments!!)

        Log.d("Lj",args.toString())
        var article = args.article
        webView.apply {
            webViewClient = WebViewClient()

            loadUrl(article.url)
        }


        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Toast.makeText(activity,"Article Saved",Toast.LENGTH_SHORT).show()
        }

    }
}



