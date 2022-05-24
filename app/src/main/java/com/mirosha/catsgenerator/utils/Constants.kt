package com.mirosha.catsgenerator.utils

object Constants {
    const val BASE_URL = "https://cataas.com"
    const val TAGS_URL = "/api/tags"
    const val CAT_URL = "/cat?json=true"
    const val CAT_BY_TAG_URL = "/cat/{tag}?json=true"
    const val CAT_BY_TEXT_URL = "/cat/says/{text}?json=true"
    const val CAT_BY_TAG_AND_TEXT_URL = "/cat/{tag}/says/{text}?json=true"

    const val TAG_PARAMETER = "tag"
    const val TEXT_PARAMETER = "text"
}