package it.saggioland.kastle.model

import kotlinx.datetime.LocalDate

data class GameMetadata(
    val author: String?,
    val version: String?,
    val published: LocalDate?,
    val kastleVersions: List<String>?
)