package fxc.dev.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TikDownload(
    @SerialName("inforVideo")
    val information: TikInformation,
    @SerialName("music")
    val music: TikMusic
)
