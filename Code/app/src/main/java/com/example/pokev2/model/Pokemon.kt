package com.example.pokev2.model

data class Pokemon(
    val game_index: Int = 0,
    var name: String = "",
    val imageUrl: String = "",
    val types: List<String> = emptyList(),
    val height: String = "",
    val weight: String = "",
    val base_experience: Int = 0
) {

    constructor() : this(
        0, "", "", emptyList(), "", "", 0
    )
}
