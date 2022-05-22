package com.mirosha.catsgenerator.model

import com.google.gson.annotations.SerializedName

data class CatResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("tags")
    var tags: List<String>,

    @SerializedName("url")
    var url: String
)