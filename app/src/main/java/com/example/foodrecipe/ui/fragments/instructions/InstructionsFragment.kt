package com.example.foodrecipe.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.foodrecipe.R
import com.example.foodrecipe.models.Result
import com.example.foodrecipe.util.Constants

class InstructionsFragment : Fragment() {
lateinit var instructionsWebView : WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_instructions, container, false)
        instructionsWebView =view.findViewById(R.id.instructions_webView)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        instructionsWebView.webViewClient = object : WebViewClient(){}
        val websiteUrl :String = myBundle!!.sourceUrl
        instructionsWebView.loadUrl(websiteUrl)
        return view
    }

}