package hashem.mousavi.countblockofonesvisualization.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MainScreen(uiState: UiState) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(uiState.currIndex) {
        if (!isItemVisible(uiState.currIndex, scrollState)) {
            scrollState.animateScrollToItem(uiState.currIndex)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Count Blocks of Ones")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = scrollState
        ) {
            items(count = uiState.sequence.length) { index ->
                UiItem(index, uiState)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (!uiState.finished) {
                countingText()
            } else {
                "Count: ${uiState.blocks.size}"
            },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun countingText(): String {
    var dotCount by remember { mutableStateOf(1) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(200)
            dotCount = (++dotCount) % 4
        }
    }
    return StringBuilder("Counting").apply {
        repeat(dotCount) {
            append(".")
        }
        repeat(3 - dotCount) {
            append(" ")
        }
    }.toString()
}

@Composable
private fun UiItem(
    index: Int,
    uiState: UiState,
) {
    Column(modifier = Modifier.requiredWidth(16.dp)) {
        Indicator((index == uiState.currIndex) && !uiState.finished)

        val color =
            if (uiState.blocks.isNotEmpty() && uiState.blocks.any { it.first <= index && it.last > index }) {
                Color.Green
            } else {
                Color.Transparent
            }
        Text(
            text = uiState.sequence[index].toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color)
        )
    }
}

private fun isItemVisible(index: Int, lazyListState: LazyListState): Boolean {
    val layoutInfo = lazyListState.layoutInfo
    val firstVisibleIndex = layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
    val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    return index in firstVisibleIndex..lastVisibleIndex
}

@Composable
fun Indicator(visible: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alphaAnim = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 500)),
        label = ""
    )
    Icon(
        modifier = Modifier.alpha(alpha = if (!visible) 0f else alphaAnim.value),
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = ""
    )
}

@Preview(showSystemUi = true)
@Composable
fun UiItemPreview() {
    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            UiItem(
                1,
                UiState(
                    sequence = "111001100001000001111110",
                    blocks = listOf(IntRange(start = 0, endInclusive = 2)),
                    currIndex = 1,
                    finished = false
                )
            )
        }
    }

}