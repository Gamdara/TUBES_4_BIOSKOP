package com.kel4.tubes_4_bioskop.api

class TicketApi {
    companion object{
        val BASE_URL = "https://www.panduwindito.my.id/"
        val GET_ALL_URL = BASE_URL + "tickets"
        val GET_BY_ID_URL = BASE_URL + "tickets/"
        val ADD_URL = BASE_URL + "tickets"
        val UPDATE_URL = BASE_URL + "tickets/"
        val DELETE_URL = BASE_URL + "tickets/"
    }
}