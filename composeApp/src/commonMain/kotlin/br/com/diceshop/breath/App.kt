package br.com.diceshop.breath

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dice_breath.composeapp.generated.resources.*
import br.com.diceshop.breath.theme.AppTheme
import br.com.diceshop.breath.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun App() = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Box Breathing",
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.displayLarge
        )

        var isDark by LocalThemeIsDark.current
        val icon = remember(isDark) {
            if (isDark) Res.drawable.ic_dark_mode
            else Res.drawable.ic_light_mode
        }

        ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { isDark = !isDark },
            content = {
                Icon(vectorResource(icon), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(Res.string.theme))
            }
        )

        BoxBreathingAnimation()
    }
}
