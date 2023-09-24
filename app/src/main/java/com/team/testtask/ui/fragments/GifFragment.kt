package com.team.testtask.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.team.testtask.R
import com.team.testtask.viewmodel.GifListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFragment: Fragment() {
    private val vm: GifListViewModel by activityViewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF424242))) {
                    ConstraintLayout {
                        val (imageHolder, prevBtn, nextBtn, backBtn) = createRefs()

                        val imageLoader = ImageLoader.Builder(LocalContext.current)
                            .components {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }
                            .build()

                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = vm.gifList.value[vm.position.value].originalUrl)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        size(Size.ORIGINAL)
                                    }).build(),
                                imageLoader = imageLoader
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(imageHolder) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                        )

                        Image(painter = painterResource(R.drawable.ic_prev),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .requiredHeight(60.dp)
                                .requiredWidth(60.dp)
                                .clickable(enabled = true, onClick = {
                                    vm.decreaseCount()
                                })
                                .constrainAs(prevBtn) {
                                    start.linkTo(parent.start)
                                    top.linkTo(imageHolder.top)
                                    bottom.linkTo(imageHolder.bottom)
                                })

                        Image(painter = painterResource(R.drawable.ic_next),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .requiredHeight(60.dp)
                                .requiredWidth(60.dp)
                                .clickable(enabled = true, onClick = {
                                    vm.increaseCount()
                                })
                                .constrainAs(nextBtn) {
                                    top.linkTo(imageHolder.top)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(imageHolder.bottom)
                                })

                        Image(painter = painterResource(R.drawable.ic_back_arrow_24),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp)
                                .clickable(enabled = true, onClick = {
                                    findNavController().popBackStack()
                                })
                                .constrainAs(backBtn) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                })
                    }
                }
            }
        }
    }
}