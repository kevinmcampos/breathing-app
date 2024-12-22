package br.com.diceshop.breath

import coil3.PlatformContext
import org.w3c.dom.Audio

actual fun playSound(context: PlatformContext, soundFile: String) {
    Audio(soundFile).play()
}

actual fun pauseSound() {
}
