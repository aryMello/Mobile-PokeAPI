package com.example.pokev2.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.pokev2.R

object TypeUtils {

    fun getGradientForType(type: String, view: View, context: Context) {
        val baseColor: Int

        // Determine the base color based on Pokémon type
        when (type.lowercase()) {
            "fire" -> baseColor = ContextCompat.getColor(context, R.color.fire)
            "water" -> baseColor = ContextCompat.getColor(context, R.color.water)
            "grass" -> baseColor = ContextCompat.getColor(context, R.color.grass)
            "electric" -> baseColor = ContextCompat.getColor(context, R.color.electric)
            "normal" -> baseColor = ContextCompat.getColor(context, R.color.normal)
            "poison" -> baseColor = ContextCompat.getColor(context, R.color.poison)
            "psychic" -> baseColor = ContextCompat.getColor(context, R.color.psychic)
            "ghost" -> baseColor = ContextCompat.getColor(context, R.color.ghost)
            "fighting" -> baseColor = ContextCompat.getColor(context, R.color.fighting)
            "fairy" -> baseColor = ContextCompat.getColor(context, R.color.fairy)
            "ground" -> baseColor = ContextCompat.getColor(context, R.color.ground)
            "rock" -> baseColor = ContextCompat.getColor(context, R.color.rock)
            "bug" -> baseColor = ContextCompat.getColor(context, R.color.bug)
            else -> baseColor = ContextCompat.getColor(context, R.color.normal) // Default to normal
        }

        // Create a lighter or darker version of the base color
        val gradientColor = manipulateColor(baseColor, 0.8f) // Darker color (adjust ratio as needed)

        // Create a gradient drawable
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR, // Top-left to bottom-right gradient
            intArrayOf(baseColor, gradientColor)
        )
        gradientDrawable.cornerRadius = 0f

        // Apply the gradient as the background
        view.background = gradientDrawable
    }

    private fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).coerceIn(0f, 255f).toInt()
        val g = (Color.green(color) * factor).coerceIn(0f, 255f).toInt()
        val b = (Color.blue(color) * factor).coerceIn(0f, 255f).toInt()
        return Color.argb(a, r, g, b)
    }
}
