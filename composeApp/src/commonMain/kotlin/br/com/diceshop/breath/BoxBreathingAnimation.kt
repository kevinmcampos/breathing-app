package br.com.diceshop.breath

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class BreathState {
    BreathIn, Hold1, BreathOut, Hold2;

    fun text() = when (this) {
        BreathIn -> "Breathe in"
        Hold1 -> "Hold"
        BreathOut -> "Breathe out"
        Hold2 -> "Hold"
    }
}

@Composable
fun BoxBreathingAnimation() {
    val squareSize = 400f
    val animationDuration = 6000 // 4 seconds

    val squareFillProgress = remember { Animatable(0f) }

    // Ball position
    // 0 - left bottom
    // 1 - left top
    // 2 - right top
    // 3 - right bottom
    // 4 - left bottom (again)
    val ballPosition = remember { Animatable(0f) }

    var breathState by remember { mutableStateOf(BreathState.BreathIn) }
    val context = LocalPlatformContext.current
    LaunchedEffect(breathState) {
        launch {
            when (breathState) {
                BreathState.BreathIn -> {
                    playSound(context, "breath_in1.MP3")
                    squareFillProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(animationDuration, easing = LinearEasing)
                    )
                }

                BreathState.Hold1 -> {
                    playSound(context, "hold1_1.MP3")
                    delay(animationDuration.toLong())
                }

                BreathState.BreathOut -> {
                    playSound(context, "breath_out1.MP3")
                    squareFillProgress.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(animationDuration, easing = LinearEasing)
                    )
                }

                BreathState.Hold2 -> {
                    playSound(context, "hold2_1.MP3")
                    delay(animationDuration.toLong())
                }
            }

            // Transition to next state
            breathState = when (breathState) {
                BreathState.BreathIn -> BreathState.Hold1
                BreathState.Hold1 -> BreathState.BreathOut
                BreathState.BreathOut -> BreathState.Hold2
                BreathState.Hold2 -> BreathState.BreathIn
            }
        }

        launch {
            val targetToAnimateTo = when (breathState) {
                BreathState.BreathIn -> 1
                BreathState.Hold1 -> 2
                BreathState.BreathOut -> 3
                BreathState.Hold2 -> 4
            }

            if (breathState == BreathState.BreathIn) {
                ballPosition.snapTo(0f)
            }

            ballPosition.animateTo(
                targetValue = targetToAnimateTo.toFloat(),
                animationSpec = tween(animationDuration, easing = LinearEasing)
            )
        }
    }

    Canvas(
        modifier = Modifier
            .size(450.dp)
            .padding(16.dp)
    ) {
        // Calculate the square bounds
        val left = (size.width - squareSize) / 2
        val top = (size.height - squareSize) / 2
        val right = left + squareSize
        val bottom = top + squareSize

        // Draw the square outline
        drawRect(
            color = Color.Gray,
            topLeft = Offset(left, top),
            size = Size(squareSize, squareSize),
            style = Stroke(width = 4f)
        )

        // Draw the filling rectangle
        drawRect(
            color = Color.Blue.copy(alpha = 0.3f),
            topLeft = Offset(left, bottom),
            size = Size(
                width = squareSize,
                height = -(squareSize * squareFillProgress.value) // Negative height to fill from bottom to top
            )
        )

        // Calculate ball position
        val edge = minOf(ballPosition.value, 3.999999f).toInt() // 0: Left, 1: Top, 2: Right, 3: Bottom
        val fraction = ballPosition.value - edge
        val ballRadius = 20f
        val ballX = when (edge) {
            0 -> left // Left edge
            1 -> left + fraction * squareSize // Top edge
            2 -> right // Right edge
            else -> right - fraction * squareSize // Bottom edge
        }
        val ballY = when (edge) {
            0 -> bottom - fraction * squareSize // Left edge
            1 -> top // Top edge
            2 -> top + fraction * squareSize // Right edge
            else -> bottom // Bottom edge
        }

        // Draw the ball
        drawCircle(
            color = Color.Red,
            radius = ballRadius,
            center = Offset(ballX, ballY)
        )
    }

    Text(
        text = breathState.text(),
        modifier = Modifier.padding(16.dp)
    )
}
