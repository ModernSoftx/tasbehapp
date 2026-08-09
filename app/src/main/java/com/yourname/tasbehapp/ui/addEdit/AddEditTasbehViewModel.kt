package com.yourname.tasbehapp.ui.addEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.tasbehapp.data.repository.TasbehRepository
import com.yourname.tasbehapp.domain.model.Tasbeh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTasbehViewModel @Inject constructor(
    private val repository: TasbehRepository
) : ViewModel() {

    private val _tasbeh = MutableLiveData<Tasbeh?>()
    val tasbeh: LiveData<Tasbeh?> = _tasbeh

    sealed class AddEditEvent {
        object Success : AddEditEvent()
        data class Error(val message: String) : AddEditEvent()
    }

    private val eventChannel = Channel<AddEditEvent>()
    val event = eventChannel.receiveAsFlow()

    fun loadTasbeh(id: Long) {
        if (id == -1L) {
            _tasbeh.value = null
            return
        }
        viewModelScope.launch {
            _tasbeh.value = repository.getTasbehById(id)
        }
    }

    fun saveTasbeh(
        name: String,
        arabicText: String?,
        targetCount: Int,
        category: String,
        description: String?,
        isFavourite: Boolean
    ) {
        if (name.isBlank()) {
            viewModelScope.launch {
                eventChannel.send(AddEditEvent.Error("Name cannot be empty"))
            }
            return
        }

        viewModelScope.launch {
            val currentTasbeh = _tasbeh.value
            if (currentTasbeh != null) {
                // Update
                repository.updateTasbeh(
                    currentTasbeh.copy(
                        name = name,
                        arabicText = arabicText,
                        targetCount = targetCount,
                        category = category,
                        description = description,
                        isFavourite = isFavourite
                    )
                )
            } else {
                // Create
                repository.insertTasbeh(
                    Tasbeh(
                        name = name,
                        arabicText = arabicText,
                        targetCount = targetCount,
                        category = category,
                        description = description,
                        isFavourite = isFavourite
                    )
                )
            }
            eventChannel.send(AddEditEvent.Success)
        }
    }
}
