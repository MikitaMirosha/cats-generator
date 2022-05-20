package com.mirosha.catsgenerator.model

import com.google.gson.annotations.SerializedName

data class CatResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("tags")
    val tags: List<String>,

    @SerializedName("url")
    val url: String,
)