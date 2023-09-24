package com.team.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.team.testtask.R
import com.team.testtask.utils.isConnected
import com.team.testtask.viewmodel.GifListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFragment: Fragment() {
    private lateinit var img: ImageView
    private lateinit var backArrow: ImageView
    private lateinit var prevImg: ImageView
    private lateinit var nextImg: ImageView
    private lateinit var errorText: TextView
    private val vm: GifListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backArrow = view.findViewById(R.id.back_arrow)
        prevImg = view.findViewById(R.id.prev_img)
        img = view.findViewById(R.id.img_holder)
        nextImg = view.findViewById(R.id.next_img)
        errorText = view.findViewById(R.id.error_text)

        backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        nextImg.setOnClickListener {
            vm.increaseCount()
            setImage()
        }
        prevImg.setOnClickListener {
            vm.decreaseCount()
            setImage()
        }

        vm.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                img.visibility = VISIBLE
                prevImg.visibility = VISIBLE
                nextImg.visibility = VISIBLE
                errorText.visibility = GONE
            } else {
                Glide.with(requireContext()).asGif().load(R.drawable.ic_no_image).into(img)
                img.visibility = GONE
                prevImg.visibility = GONE
                nextImg.visibility = GONE
                errorText.visibility = VISIBLE
            }
        }

        setImage()
    }

    private fun setImage() {
        if(isConnected(requireContext())) {
            vm.setConnection(true)
            val url = vm.gifList.value?.get(vm.position.value)?.originalUrl
            Glide.with(requireContext()).asGif().load(url).into(img)
        } else {
            vm.setConnection(false)
        }
    }
}