package com.example.rick_morti

import com.google.gson.annotations.SerializedName

data class PersonajeApiResponse(
    @SerializedName("info") val info: InfoResponse,
    @SerializedName("results") val results: List<Personaje>
)

data class InfoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class Personaje(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("image") val image: String
)
