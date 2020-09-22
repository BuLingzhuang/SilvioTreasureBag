package com.intsig.silviotreasurebag.hsv

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.red
import java.math.BigDecimal
import kotlin.math.roundToInt

/**
 * @author lingzhuang_bu
 * Description:
 * @date 2020/9/22
 */
class TreasureBag {

    companion object {
        private const val TAG = "TreasureBag"
        private const val hueStep = 2
        private const val saturationStep = 0.16
        private const val saturationStep2 = 0.05
        private const val brightnessStep1 = 0.05
        private const val brightnessStep2 = 0.15
        private const val lightColorCount = 5
        private const val darkColorCount = 4
    }

    fun main(color: String, index: Int): String {
        val isLight = index <= 6
        val parseColor = Color.parseColor(color)
        val hsv = getHsv(parseColor.red, parseColor.red, parseColor.red)
        val i = if (isLight) {
            lightColorCount + 1 - index
        } else {
            index - lightColorCount - 1
        }
        // i 为index与6的相对距离
        Log.d(TAG, "hsv = $hsv")
        return tinycolor(
            getHue(hsv, i, isLight),
            getSaturation(hsv, i, isLight),
            getValue(hsv, i, isLight)
        ).toHexString();
    }

    fun getHsv(r: Int, g: Int, b: Int): HSV {
        val floatArray = FloatArray(3)
        Color.RGBToHSV(r, g, b, floatArray)
        return HSV(floatArray[0], floatArray[1], floatArray[2])
    }

    fun getSaturation(hsv: HSV, i: Int, isLight: Boolean): Float {
        var saturation: Double
        saturation = when {
            isLight -> {
                hsv.s - saturationStep * i
            }
            i == darkColorCount -> {
                hsv.s + saturationStep
            }
            else -> {
                hsv.s + saturationStep2 * i
            }
        }
        if (saturation > 1.0) {
            saturation = 1.0
        }
        if (isLight && i == lightColorCount && saturation > 0.1) {
            saturation = 0.1
        }
        if (saturation < 0.06) {
            saturation = 0.06
        }

        return BigDecimal(saturation).setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    fun getValue(hsv: HSV, i: Int, isLight: Boolean): Int {

    }

    fun getHue(hsv: HSV, i: Int, isLight: Boolean): Int {
        var hue = if (hsv.h in 60.0..240.0) {
            if (isLight) hsv.h - hueStep * i else hsv.h + hueStep * i
        } else {
            if (isLight) hsv.h + hueStep * i else hsv.h - hueStep * i
        }
        if (hue < 0) {
            hue += 360
        } else if (hue >= 360) {
            hue -= 360
        }
        return hue.
    }
}