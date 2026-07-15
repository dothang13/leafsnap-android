package fxc.dev.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TikMusic(
    val id: String,
    val title: String,
    val playURL: String,
    val coverLarge: String,
    val coverThumb: String,
    val author: String,
    val duration: Long,
    val original: Boolean,
    val album: String
)
