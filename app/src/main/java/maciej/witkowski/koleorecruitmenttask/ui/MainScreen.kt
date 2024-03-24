package maciej.witkowski.koleorecruitmenttask.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import maciej.witkowski.koleorecruitmenttask.R
import org.koin.androidx.compose.getViewModel

enum class KoleoScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Select(title = R.string.choose_station),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: KoleoScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KoleoApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = getViewModel<MainViewModel>()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = KoleoScreen.valueOf(
        backStackEntry?.destination?.route ?: KoleoScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = KoleoScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = KoleoScreen.Start.name) {
                MainScreen(
                    onFirstStationClick = {
                        setSelectedStationAndNavigateToSelect(1, viewModel, navController)
                    },
                    onSecondStationClick = {
                        setSelectedStationAndNavigateToSelect(2, viewModel, navController)
                    },
                )
            }
            composable(route = KoleoScreen.Select.name) {
                LiveSearchScreen(
                    onItemClick = {
                        setStationAndNavigateToStart(it, viewModel, navController)
                    },
                )
            }

        }
    }
}

private fun setStationAndNavigateToStart(
    id: Int,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.onStationSet(id)
    navController.popBackStack(KoleoScreen.Start.name, inclusive = false)

}

private fun setSelectedStationAndNavigateToSelect(
    selectedStation: Int,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.selectedStation = selectedStation
    navController.navigate(KoleoScreen.Select.name)
}

@Composable
fun MainScreen(
    onFirstStationClick: () -> Unit,
    onSecondStationClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Button(onClick = onFirstStationClick) {
            Text(text = "First")
        }
        Button(onClick = onSecondStationClick) {
            Text(text = "Second")
        }
    }
}




