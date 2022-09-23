package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.R

object MovieList {
    val ngeriNgeriSedap : Movie = Movie("Ngeri-Ngeri Sedap", 4, "sinopsis", "director", "writter", R.drawable.poster_ngeringerisedap)
    val mencuriRadenSaleh : Movie = Movie("Mencuri Raden Saleh", 4, "sinopsis", "director", "writter", R.drawable.poster_mencuriradensaleh)
    val cintaPertamaKeduaKetiga : Movie = Movie("Cinta Pertama, Kedua & Ketiga", 4, "sinopsis", "director", "writter", R.drawable.poster_cintapertamakeduaketiga)
    val pengabdiSetan : Movie = Movie("Pengabdi Setan", 4, "sinopsis", "director", "writter", R.drawable.poster_pengabdisetan)
    val filosofiKopi : Movie = Movie("Filosofi Kopi", 4, "sinopsis", "director", "writter", R.drawable.poster_filosofikopi)
    val sepertiDendam : Movie = Movie("Seperti Dendam", 4, "sinopsis", "director", "writter", R.drawable.poster_sepertidendam)
    val yuni : Movie = Movie("Yuni", 4, "sinopsis", "director", "writter", R.drawable.poster_yuni)
    val tekaTekiTika : Movie = Movie("Teka-Teki Tika", 4, "sinopsis", "director", "writter", R.drawable.poster_tekatekitika)
    val garaGaraWarisan : Movie = Movie("Gara-Gara Warisan", 4, "sinopsis", "director", "writter", R.drawable.poster_garagarawarisan)
    val kkn : Movie = Movie("KKN", 4, "sinopsis", "director", "writter", R.drawable.poster_kkn)
    val blackAdam : Movie = Movie("Black Adam", 4, "sinopsis", "director", "writter", R.drawable.poster_blackadam)
    val onePiece : Movie = Movie("One Piece Red", 4, "sinopsis", "director", "writter", R.drawable.poster_onepiece)
    val pinocchio : Movie = Movie("Pinocchio", 4, "sinopsis", "director", "writter", R.drawable.poster_pinocchio)
    val avatar : Movie = Movie("Avatar: The Way of Water", 4, "sinopsis", "director", "writter", R.drawable.poster_avatar)
    val blackPanther : Movie = Movie("Black Panther: Wakanda Forever", 4, "sinopsis", "director", "writter", R.drawable.poster_blackpanther)
    val disenchanted : Movie = Movie("Disenchanted", 4, "sinopsis", "director", "writter", R.drawable.poster_disenchanted)
    val puss : Movie = Movie("Puss in Boots: The Last Wish", 4, "sinopsis", "director", "writter", R.drawable.poster_puss)
    val flash : Movie = Movie("The Flash", 4, "sinopsis", "director", "writter", R.drawable.poster_flash)
    val medieval : Movie = Movie("Medieval", 4, "sinopsis", "director", "writter", R.drawable.poster_medieval)
    val amsterdam : Movie = Movie("Amsterdam 2022", 4, "sinopsis", "director", "writter", R.drawable.poster_amsterdam)



    var listOfNowPlaying = arrayOf<Movie>(
        ngeriNgeriSedap, mencuriRadenSaleh, cintaPertamaKeduaKetiga, pengabdiSetan, filosofiKopi,
        sepertiDendam, yuni, tekaTekiTika, garaGaraWarisan,kkn
    )


    var listOfUpComing = arrayOf<Movie>(
        avatar, blackPanther, blackAdam, onePiece, pinocchio, disenchanted, puss, flash, medieval, amsterdam
    )

    fun find(judul: String): Movie{
        var movie : Movie = ngeriNgeriSedap
        for(innerArray in MovieList.listOfNowPlaying){
            if(innerArray.judul == judul){
                movie = innerArray
            }
        }
        return movie
    }
}