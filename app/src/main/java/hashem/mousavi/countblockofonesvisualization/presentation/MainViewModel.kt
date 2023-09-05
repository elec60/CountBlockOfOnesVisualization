package hashem.mousavi.countblockofonesvisualization.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hashem.mousavi.countblockofonesvisualization.domain.CountBlocksUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: CountBlocksUseCase = CountBlocksUseCase(), // use DI in real projects
) : ViewModel() {


    private val _uiState = mutableStateOf(
        UiState(
            sequence = "11100110001000011110" as CharSequence,
            blocks = emptyList(),
            currIndex = -1,
            finished = false
        )
    )
    val uiState: State<UiState> = _uiState

    fun start() {
        viewModelScope.launch {
            useCase(uiState.value.sequence).collect { itemInfo ->
                val blocks = uiState.value.blocks.toMutableList()

                if (itemInfo.isStartOfBlock) {
                    blocks.add(
                        IntRange(
                            start = itemInfo.currentIndex,
                            endInclusive = itemInfo.currentIndex
                        )
                    )
                } else if (itemInfo.addToRange) {
                    val last = blocks.last()
                    blocks.remove(last)
                    blocks.add(
                        IntRange(
                            start = last.start,
                            endInclusive = itemInfo.currentIndex
                        )
                    )
                }
                _uiState.value = uiState.value.copy(
                    currIndex = itemInfo.currentIndex,
                    blocks = blocks,
                    finished = itemInfo.finished
                )
            }
        }
    }
}