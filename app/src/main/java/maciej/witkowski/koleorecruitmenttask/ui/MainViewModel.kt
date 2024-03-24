package maciej.witkowski.koleorecruitmenttask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import maciej.witkowski.koleorecruitmenttask.common.mutableStateIn
import maciej.witkowski.koleorecruitmenttask.domain.CombineStationsUseCase

internal class MainViewModel(
    combineStationsStationsUseCase: CombineStationsUseCase,
) : ViewModel() {

    var selectedStation = 1

    init {
        combineStationsStationsUseCase(Unit)
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _stations = (combineStationsStationsUseCase.data).mutableStateIn(initialValue = listOf(), scope = viewModelScope)

    @OptIn(FlowPreview::class)
    val stations = searchText
        .debounce(100L)
        .onEach { _isSearching.update { true } }
        .combine(_stations) { text, stations ->
            if (text.isBlank()) {
                stations
            } else {
                delay(100L)
                stations.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _stations.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onStationSet(id: Int) {
        Log.d("aha23", id.toString())
    }
}