package maciej.witkowski.koleorecruitmenttask.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import maciej.witkowski.koleorecruitmenttask.common.mutableStateIn
import maciej.witkowski.koleorecruitmenttask.domain.CombineStationsUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

internal class MainViewModel(
    combineStationsStationsUseCase: CombineStationsUseCase,
) : ViewModel() {

    init {
        combineStationsStationsUseCase(Unit)
    }

    internal val state: StateFlow<MainState> = combineStationsStationsUseCase.data
        .map { data ->
            MainState(
                stations = data
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainState()
        )

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
}

@Immutable
internal data class MainState(
    val stations: List<Station> = arrayListOf()
)