package maciej.witkowski.koleorecruitmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import maciej.witkowski.koleorecruitmenttask.ui.KoleoApp
import maciej.witkowski.koleorecruitmenttask.ui.theme.KoleoRecruitmentTaskTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoleoRecruitmentTaskTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    KoleoApp()
                }
            }
        }
    }
}