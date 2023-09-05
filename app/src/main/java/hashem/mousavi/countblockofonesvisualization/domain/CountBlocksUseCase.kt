package hashem.mousavi.countblockofonesvisualization.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountBlocksUseCase {

    private var itemInfo = ItemInfo(0)

    operator fun invoke(sequence: CharSequence): Flow<ItemInfo> = flow {
        sequence.forEachIndexed { index, _ ->
            itemInfo = itemInfo.copy(currentIndex = index)
            if (isStartingOfBlock(index, sequence)) {
                itemInfo = itemInfo.copy(
                    isStartOfBlock = true,
                    countOfBlocks = itemInfo.countOfBlocks + 1,
                    addToRange = true
                )
            } else {
                itemInfo = itemInfo.copy(isStartOfBlock = false)
                if (isZero(index, sequence)) {
                    itemInfo = itemInfo.copy(addToRange = false)
                }
            }
            emit(itemInfo)
            delay(800)
        }
        itemInfo = itemInfo.copy(finished = true)
        emit(itemInfo)
    }

    private fun isStartingOfBlock(index: Int, seq: CharSequence): Boolean {
        if (index == 0) return seq[index] == '1'
        return seq[index - 1] == '0' && seq[index] == '1'
    }

    private fun isZero(index: Int, seq: CharSequence): Boolean {
        return seq[index - 1] == '0'
    }

}