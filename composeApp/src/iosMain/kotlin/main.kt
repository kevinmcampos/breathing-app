import androidx.compose.ui.window.ComposeUIViewController
import br.com.diceshop.breath.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
