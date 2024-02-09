package utils

object UrlHelper {

    fun getImageUrl(url: String?): String {
        val splits = url?.split("/") ?: listOf()
       return if (splits.isNotEmpty()) {
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${splits[splits.lastIndex - 1]}.png"
        } else {
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
        }
    }
}