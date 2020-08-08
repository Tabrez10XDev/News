package com.example.news.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Saved.newInstance] factory method to
 * create an instance of this fragment.
 */
class Saved : Fragment(R.layout.fragment_saved) {
    lateinit var viewModel : NewsVM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
    }
}
