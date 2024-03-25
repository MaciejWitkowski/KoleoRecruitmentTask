package maciej.witkowski.koleorecruitmenttask.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maciej.witkowski.koleorecruitmenttask.R

@Composable
fun MainScreen(
    firstStation: String?,
    secondStation: String?,
    distance: String?,
    onFirstStationClick: () -> Unit,
    onSecondStationClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.first_station), fontSize = 30.sp)
        TextButton(onClick = onFirstStationClick) {
            Text(text = firstStation ?: stringResource(R.string.select_first_station), fontSize = 30.sp)
        }
        Text(text = stringResource(R.string.second_station), fontSize = 30.sp)
        TextButton(onClick = onSecondStationClick) {
            Text(text = secondStation ?: stringResource(R.string.select_second_station), fontSize = 30.sp)
        }
        Text(text = stringResource(R.string.distance_title), fontSize = 30.sp)
        Text(text = distance ?: stringResource(R.string.distance_placeholer), fontSize = 30.sp)
    }
}




