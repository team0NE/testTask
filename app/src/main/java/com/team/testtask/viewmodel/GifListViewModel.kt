package com.team.testtask.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _gifList: MutableLiveData<List<GifImage>> = MutableLiveData()
    val gifList: LiveData<List<GifImage>> get() = _gifList
    private val _isConnected: MutableLiveData<Boolean> = MutableLiveData()
    val isConnected: LiveData<Boolean> get() = _isConnected
    val query = mutableStateOf("car")
    val position = mutableStateOf(0)

    fun loadData() {
        // check internet
        if (isConnected(app)) {
            setConnection(true)
            viewModelScope.launch {
                newSearch()
            }
        } else {
            setConnection(false)
        }
    }

    private suspend fun newSearch() {
        val  result = repository.search(
            apiKey = apik,
            key = query.value
        )

        _gifList.value = result
    }

    fun setNewQuery(newSearch: String) {
        // check internet
        if (isConnected(app)) {
            setConnection(true)
            query.value = newSearch
            viewModelScope.launch {
                newSearch()
            }
        } else {
            setConnection(false)
        }
    }

    fun increaseCount() {
        val nextPosition = position.value + 1
        val listSize = gifList.value?.size ?: 0

        if (nextPosition < listSize) {
            position.value = nextPosition
        } else {
            position.value = 0
        }
    }
    fun decreaseCount() {
        val nextPosition = position.value - 1
        val listSize = gifList.value?.size ?: 0

        if (nextPosition in 0 until listSize) {
            position.value = nextPosition
        } else {
            position.value = listSize - 1
        }
    }

    fun setConnection(connectValue: Boolean) {
        _isConnected.value = connectValue
    }
}