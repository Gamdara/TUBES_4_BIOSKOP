package com.kel4.tubes_4_bioskop.api

class MovieApi {
    companion object{
        val BASE_URL = "https://atma-cinema.herokuapp.com/"
        val GET_ALL_URL = BASE_URL + "movies/"
        val GET_BY_ID_URL = BASE_URL + "movies/"
        val ADD_URL = BASE_URL + "movies/"
        val UPDATE_URL = BASE_URL + "movies/"
        val DELETE_URL = BASE_URL + "movies/"
    }
}