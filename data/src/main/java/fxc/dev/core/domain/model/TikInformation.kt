package fxc.dev.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TikInformation(
    val id: String,
    val description: String,
    val createdAt: String,
    val height: Long,
    val width: Long,
    val duration: Long,
    val resolution: String,
    val shareCount: Long,
    val likesCount: Long,
    val commentCount: Long,
    val playCount: Long,
    val downloadURL: String,
    val cover: String,
    val dynamicCover: String,
    val playURL: String,
    val format: String,
    val author: String,
    val directVideoUrl: String
)