package dev.bondar.nbp.data.model

public data class Rate(
    val cacheId: Long = ID_NONE,
    val currency: String?,
    val code: String?,
    val mid: Double?,
) {
    public companion object {
        public const val ID_NONE: Long = 0L
    }
}