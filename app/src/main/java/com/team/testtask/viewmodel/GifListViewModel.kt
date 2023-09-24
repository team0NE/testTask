package com.team.testtask.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.testtask.BaseApplication
import com.team.testtask.domain.model.GifImage
import com.team.testtask.repository.ImageRepo
import com.team.testtask.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GifListViewModel
@Inject
constructor(
    private val app: BaseApplication,
    private val repository: ImageRepo,
    @Named("api_key") private val apik: String,
): ViewModel() {
    val gifList: MutableState<List<GifImage>> = mutableStateOf(emptyList())
    val isConnected: MutableState<Boolean> = mutableStateOf(true)
    val query = mutableStateOf("car")
    val position = mutableStateOf(0)

    init {
        loadData()
    }

    fun loadData() {
        // check internet
        if (isConnected(app)) {
            isConnected.value = true
            viewModelScope.launch {
                newSearch()
            }
        } else {
            isConnected.value = true
        }
    }

    private suspend fun newSearch() {
        val  result = repository.search(
            apiKey = apik,
            key = query.value
        )

        gifList.value = result
    }

    fun increaseCount() {
        val nextPosition = position.value + 1
        val listSize = gifList.value.size

        if (nextPosition < listSize) {
            position.value = nextPosition
        } else {
            position.value = 0
        }
    }
    fun decreaseCount() {
        val nextPosition = position.value - 1
        val listSize = gifList.value.size

        if (nextPosition in 0 until listSize) {
            position.value = nextPosition
        } else {
            position.value = listSize - 1
        }
    }
}