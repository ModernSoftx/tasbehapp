package com.yourname.tasbehapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.yourname.tasbehapp.data.repository.TasbehRepository
import com.yourname.tasbehapp.domain.model.Tasbeh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasbehListViewModel @Inject constructor(
    private val repository: TasbehRepository
) : ViewModel() {

    private val _searchQuery = MutableLiveData("")
    val allTasbehs: LiveData<List<Tasbeh>> = _searchQuery.switchMap { query ->
        if (query.isNullOrBlank()) {
            repository.allTasbehs
        } else {
            repository.searchTasbehs(query)
        }
    }

    val favouriteTasbehs: LiveData<List<Tasbeh>> = repository.favouriteTasbehs

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavourite(tasbeh: Tasbeh) {
        viewModelScope.launch {
            repository.toggleFavourite(tasbeh.id, !tasbeh.isFavourite)
        }
    }

    fun selectTasbeh(tasbehId: Long) {
        viewModelScope.launch {
            repository.setActiveTasbeh(tasbehId)
        }
    }

    fun deleteTasbeh(tasbeh: Tasbeh) {
        viewModelScope.launch {
            repository.deleteTasbeh(tasbeh)
        }
    }

    fun insertTasbeh(tasbeh: Tasbeh) {
        viewModelScope.launch {
            repository.insertTasbeh(tasbeh)
        }
    }

    fun updateTasbeh(tasbeh: Tasbeh) {
        viewModelScope.launch {
            repository.updateTasbeh(tasbeh)
        }
    }
}
