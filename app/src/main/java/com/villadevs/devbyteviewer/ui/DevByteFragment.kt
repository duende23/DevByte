package com.villadevs.devbyteviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.villadevs.devbyteviewer.DevByteApplication
import com.villadevs.devbyteviewer.adapter.DevByteAdapter
import com.villadevs.devbyteviewer.databinding.FragmentDevByteBinding
import com.villadevs.devbyteviewer.domain.DevByteVideo
import com.villadevs.devbyteviewer.viewmodels.DevByteViewModel
import com.villadevs.devbyteviewer.viewmodels.DevByteViewmodelFactory
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"


class DevByteFragment : Fragment() {

    /* private val viewModel: DevByteViewModel by activityViewModels {
         DevByteViewmodelFactory((activity?.application as DevByteApplication).database.videoDao())
     }*/

  /*  private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            DevByteViewmodelFactory(activity.application)
        )[DevByteViewModel::class.java]
    }*/

    private val viewModel: DevByteViewModel by activityViewModels {
        DevByteViewmodelFactory((activity?.application as DevByteApplication))
    }

    private var _binding: FragmentDevByteBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDevByteBinding.inflate(inflater, container, false)
        val view = binding.root


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError){
                onNetworkError()
                binding.loadingSpinner.visibility = View.GONE
            }
        }


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* viewModel.playlist.observe(viewLifecycleOwner) { videos ->
             videos?.apply {
                 devByteAdapter?.videos = videos
             }
         }*/




        val adapter = DevByteAdapter { VideoClick ->
            //viewModel.updateCurrentAmphibian(VideoClick)

            // When a video is clicked this block or lambda will be called by DevByteAdapter
            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            val packageManager = context?.packageManager ?: return@DevByteAdapter
            // Try to generate a direct intent to the YouTube app
            val queryUrl: Uri = Uri.parse("vnd.youtube:" + Uri.parse(VideoClick.url).getQueryParameter("v"))
            //var intent = Intent(Intent.ACTION_VIEW, VideoClick.launchUri)
            var intent = Intent(Intent.ACTION_VIEW, queryUrl)
            if (intent.resolveActivity(packageManager) == null) {
                // YouTube app isn't found, use the web url
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(VideoClick.url))
            }
            startActivity(intent)
        }

        binding.rvRecyclerView.adapter = adapter

        viewModel.playlist.observe(viewLifecycleOwner) { videos ->
            videos.let {
                adapter.submitList(it)
            }
            binding.loadingSpinner.visibility = View.GONE
        }





        binding.rvRecyclerView.layoutManager = LinearLayoutManager(this.context)


    }


    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

   /* *//**
     * Helper method to generate YouTube app links
     *//*
    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}