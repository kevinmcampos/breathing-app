package br.com.diceshop.breath

import coil3.PlatformContext
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class SoundPlayer

actual fun playSound(context: PlatformContext, soundFile: String) {
    try {
        val resource = SoundPlayer::class.java.classLoader.getResource(soundFile.replace(".MP3", ".wav"))
        val audioInputStream = AudioSystem.getAudioInputStream(resource)
        val clip: Clip = AudioSystem.getClip()
        clip.open(audioInputStream)
        clip.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

actual fun pauseSound() {
}
