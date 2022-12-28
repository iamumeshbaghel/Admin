package com.ubproject.theateradmin

data class MovieModel(
    var movieId: String? = null,
    var about_movie: String? = null,
    var banner_image_url: String? = null,
    var cover_image_url: String? = null,
    var languages: String? = null,
    var movie_duration: String? = null,
    var movie_name: String? = null,
//    var no_of_ratings: String? = null
    var release_date: String? = null
)