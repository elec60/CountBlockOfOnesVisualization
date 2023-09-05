package hashem.mousavi.countblockofonesvisualization.domain

data class ItemInfo(
    val currentIndex: Int,
    val countOfBlocks: Int = 0,
    val isStartOfBlock: Boolean = false,
    val addToRange: Boolean = false,
    val finished: Boolean = false,
)