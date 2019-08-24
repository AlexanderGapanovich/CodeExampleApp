package com.example.domain.models.articles


data class NewsData (

	val status : String? = null,
	val totalResults : Int? = null,
	val articles : List<Articles>? = null
)