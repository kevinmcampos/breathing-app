package br.com.diceshop.breath

import coil3.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.UIKit.NSDataAsset

var audioPlayer: AVAudioPlayer? = null

@OptIn(ExperimentalForeignApi::class)
actual fun playSound(context: PlatformContext, soundFile: String) {
    val data = NSDataAsset(soundFile).data
    val player = AVAudioPlayer(data = data, error = null)
    player.volume = 1f
    player.play()
    audioPlayer = player
}

actual fun pauseSound() {
}
