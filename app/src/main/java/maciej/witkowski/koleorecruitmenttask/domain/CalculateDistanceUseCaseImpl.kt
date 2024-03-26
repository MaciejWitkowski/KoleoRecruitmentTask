package maciej.witkowski.koleorecruitmenttask.domain

import android.content.Context
import android.location.Location
import maciej.witkowski.koleorecruitmenttask.R
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseResultUseCase

class CalculateDistanceUseCaseImpl(
    private val context: Context
) : CalculateDistanceUseCase, BaseResultUseCase<StationsCoords, String>() {

    override suspend fun doWork(request: StationsCoords): String = calculateDistance(request)

    private fun calculateDistance(locations: StationsCoords): String {
        val firstLocation = Location("first")
        firstLocation.longitude = locations.firstLongitude ?: return context.getString(R.string.distance_unavailable)
        firstLocation.latitude = locations.firstLatitude ?: return context.getString(R.string.distance_unavailable)
        val secondLocation = Location("second")
        secondLocation.longitude = locations.secondLongitude ?: return context.getString(R.string.distance_unavailable)
        secondLocation.latitude = locations.secondLatitude ?: return context.getString(R.string.distance_unavailable)
        val distance = (firstLocation.distanceTo(secondLocation) / 1000).toInt()
        return context.getString(R.string.distance, distance)
    }
}

data class StationsCoords(
    val firstLongitude: Double?,
    val firstLatitude: Double?,
    val secondLongitude: Double?,
    val secondLatitude: Double?
)