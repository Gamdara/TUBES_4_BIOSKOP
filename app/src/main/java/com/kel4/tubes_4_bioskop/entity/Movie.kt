package com.kel4.tubes_4_bioskop.entity

class Movie(var judul: String, var rating: Int) {
    companion object{
        @JvmField
        var listOfMovie = arrayOf(
            Movie("Spidermane",5),
            Movie("Aquaman", 4),
            Movie("Cowman",5),
            Movie("Sheepman", 4),
            Movie("Catman",5),
            Movie("Ballman", 4),
            Movie("Botolman",5),
            Movie("lalalal", 4),
            Movie("liliilil",5),
            Movie("ahahhihi", 4),
        )
    }
}