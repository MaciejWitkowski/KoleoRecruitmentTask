package maciej.witkowski.koleorecruitmenttask.domain

import android.location.Location
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseResultUseCase

class CalculateDistanceUseCaseImpl : CalculateDistanceUseCase, BaseResultUseCase<StationsCoords, String>() {

    override suspend fun doWork(request: StationsCoords): String = calculateDistance(request)

    private fun calculateDistance(locations: StationsCoords): String {

        val firstLocation = Location("first")
        firstLocation.longitude =  locations.firstLongitude ?: return ""
        firstLocation.latitude = locations.firstLatitude ?: return ""
        val secondLocation = Location("second")
        secondLocation.longitude = locations.secondLongitude ?: return ""
        secondLocation.latitude = locations.secondLatitude ?: return ""
        val distance = firstLocation.distanceTo(secondLocation) / 1000
        Log.d("aha33", distance.toString())
        return distance.toString()
    }
}

data class StationsCoords(
    val firstLongitude: Double?,
    val firstLatitude: Double?,
    val secondLongitude: Double?,
    val secondLatitude: Double?
)