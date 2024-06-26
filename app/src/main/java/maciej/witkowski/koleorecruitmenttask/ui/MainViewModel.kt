package maciej.witkowski.koleorecruitmenttask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import maciej.witkowski.koleorecruitmenttask.common.mutableStateIn
import maciej.witkowski.koleorecruitmenttask.domain.CalculateDistanceUseCase
import maciej.witkowski.koleorecruitmenttask.domain.CombineStationsUseCase
import maciej.witkowski.koleorecruitmenttask.domain.StationsCoords
import maciej.witkowski.koleorecruitmenttask.domain.model.StationWithKeyword

internal class MainViewModel(
    combineStationsStationsUseCase: CombineStationsUseCase,
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) : ViewModel() {

    var selectedStation = 1

    init {
        combineStationsStationsUseCase(Unit)
    }

    private val _distance = MutableStateFlow<String?>(null)
    val distance = _distance.asStateFlow()

    private val _firstStationWithKeyword = MutableStateFlow<StationWithKeyword?>(null)
    val firstStation = _firstStationWithKeyword.asStateFlow()

    private val _secondStationWithKeyword = MutableStateFlow<StationWithKeyword?>(null)
    val secondStation = _secondStationWithKeyword.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _stations = (combineStationsStationsUseCase.data).map { it ->
        if (!it.data.isNullOrEmpty())
            it.data
        else {
            _isError.value = true
            emptyList()
        }
    }.mutableStateIn(
        initialValue = listOf(),
        scope = viewModelScope
    )

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
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _stations.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onStationSet(stationWithKeyword: StationWithKeyword) {
        if (selectedStation == 1)
            _firstStationWithKeyword.value = stationWithKeyword
        else {
            _secondStationWithKeyword.value = stationWithKeyword
        }
        if (_firstStationWithKeyword.value != null && _secondStationWithKeyword.value != null) {
            viewModelScope.launch {
                _distance.emit(
                    calculateDistanceUseCase.executeSync(
                        StationsCoords(
                            firstLongitude = firstStation.value?.longitude,
                            firstLatitude = firstStation.value?.latitude,
                            secondLongitude = secondStation.value?.longitude,
                            secondLatitude = secondStation.value?.latitude
                        )
                    )
                )
            }
        }
    }
}
