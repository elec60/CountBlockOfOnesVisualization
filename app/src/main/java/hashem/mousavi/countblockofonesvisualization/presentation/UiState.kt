package hashem.mousavi.countblockofonesvisualization.presentation


data class UiState(
    val sequence: CharSequence,
    val blocks: List<IntRange>,
    val currIndex: Int,
    val finished: Boolean
)
