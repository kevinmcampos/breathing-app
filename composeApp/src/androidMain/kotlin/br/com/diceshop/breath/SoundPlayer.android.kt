package br.com.diceshop.breath

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import coil3.PlatformContext
import java.io.IOException


private val mediaPlayer by lazy {
    MediaPlayer().apply {
        setVolume(100f, 100f)
        isLooping = false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun playSound(context: PlatformContext, soundFile: String) {
    mediaPlayer.playAudio(context, soundFile)

}

@RequiresApi(Build.VERSION_CODES.O)
fun MediaPlayer.playAudio(context: Context, file: String) {
    val request =
        AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            // .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .build()

    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.requestAudioFocus(request)

    val afd: AssetFileDescriptor = context.assets.openFd(file)
    try {
        reset()
        setDataSource(afd.fileDescriptor, afd.startOffset, afd.getLength());
        prepareAsync()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: SecurityException) {
        e.printStackTrace()
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    setOnPreparedListener { player -> player.start() }
    setOnCompletionListener { mediaPlayer ->
        audioManager.abandonAudioFocusRequest(request)
    }
}

actual fun pauseSound() {
}