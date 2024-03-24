package maciej.witkowski.koleorecruitmenttask.domain.model

data class Station(
    val id: Int,
    val keyword: String,
    val longitude: Double?,
    val latitude: Double?,
)