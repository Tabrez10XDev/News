package com.example.news.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import kotlinx.android.synthetic.main.fragment_breaking.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Breaking.newInstance] factory method to
 * create an instance of this fragment.
 */
class Breaking : Fragment(R.layout.fragment_breaking) {
    lateinit var viewModel : NewsVM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
    }
}
