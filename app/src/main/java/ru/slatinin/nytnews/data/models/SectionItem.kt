package ru.slatinin.nytnews.data.models

import androidx.annotation.DrawableRes
import ru.slatinin.nytnews.R

data class SectionItem(val sectionName: String){
    @DrawableRes val sectionIcon: Int = when (sectionName){
        BUSINESS -> R.drawable.ic_business
        RUSSIA_EUROPE_USA -> R.drawable.ic_compare
        WORLD -> R.drawable.ic_world
        SPORT -> R.drawable.ic_sports
        OP_ED -> R.drawable.ic_op_ed
        GAMES -> R.drawable.ic_games
        else -> {
            R.drawable.ic_newspaper
        }
    }

    companion object{
        const val BUSINESS = "Business"
        const val RUSSIA_EUROPE_USA = "Russia/Europe/USA"
        const val WORLD = "World News"
        const val SPORT = "Sport"
        const val OP_ED = "Op-ed"
        const val GAMES = "Games"
    }
}
