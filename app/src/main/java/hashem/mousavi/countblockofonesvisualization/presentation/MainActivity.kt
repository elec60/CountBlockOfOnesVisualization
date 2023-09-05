package hashem.mousavi.countblockofonesvisualization.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import hashem.mousavi.countblockofonesvisualization.presentation.ui.theme.CountBlockOfOnesVisualizationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountBlockOfOnesVisualizationTheme {
                MainScreen(
                    uiState = viewModel.uiState.value
                )
            }
        }

        viewModel.start()
    }

}
