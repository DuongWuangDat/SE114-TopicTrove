package com.topic_trove.data.model

data class Community(
    var id: String = "",
    var owner: String = "",
    var icon : String = "",
    var description: String = "",
    var rules: String = "",
    var communityName : String = "community",
    var memberCount : Int = 0
)