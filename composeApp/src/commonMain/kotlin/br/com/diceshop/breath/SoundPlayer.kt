package br.com.diceshop.breath

import coil3.PlatformContext

expect fun playSound(context: PlatformContext, soundFile: String)

expect fun pauseSound()