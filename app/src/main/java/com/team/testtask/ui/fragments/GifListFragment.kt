package com.team.testtask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.team.testtask.R
import com.team.testtask.domain.model.GifImage
import com.team.testtask.ui.compose.components.ImageCard
import com.team.testtask.utils.hideKeyboard
import com.team.testtask.viewmodel.GifListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifListFragment : Fragment() {
    private val vm: GifListViewModel by activityViewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val query = vm.query.value
                val gifList = vm.gifList.value

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF424242))) {
                    ConstraintLayout {
                        val (searchTitle, searchET, searchBTN, imageList) = createRefs()
                        Text(text = resources.getString(R.string.searchbar_title),
                            modifier = Modifier
                                .padding(start = 8.dp, top = 16.dp)
                                .constrainAs(searchTitle) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                })
                        TextField(
                            value = query,
                            onValueChange = { newQuery ->
                                vm.query.value = newQuery
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    vm.loadData()
                                    hideKeyboard(context)
                                }
                            ),
                            modifier = Modifier
                                .background(color = Color(0xFF424242))
                                .fillMaxWidth(.85f)
                                .padding(start = 8.dp, top = 8.dp)
                                .constrainAs(searchET) {
                                    start.linkTo(parent.start)
                                    top.linkTo(searchTitle.bottom)
                                }
                        )
                        Image(painter = painterResource(R.drawable.ic_send),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable(enabled = true, onClick = {
                                    vm.loadData()
                                    hideKeyboard(context)
                                })
                                .constrainAs(searchBTN) {
                                    top.linkTo(searchET.top)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(searchET.bottom)
                                })
                        LazyVerticalGrid(columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                                .fillMaxHeight(.85f)
                                .constrainAs(imageList) {
                                    start.linkTo(parent.start)
                                    top.linkTo(searchET.bottom)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                        ) {
                            itemsIndexed(
                                items = gifList
                            ) { index: Int, image: GifImage ->
                                ImageCard(imageData = image, onClick = {
                                    vm.position.value = index
                                    findNavController().navigate(R.id.gifFragment)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}