package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.R

object MovieList {
    val ngeriNgeriSedap : Movie = Movie("Ngeri-Ngeri Sedap", 4, "Pak Domu dan Mak Domu yang tinggal bersama Sarma, ingin sekali tiga anaknya: Domu, Gabe dan Sahat yang sudah lama merantau pulang untuk menghadiri acara adat, tetapi mereka menolak pulang karena hubungan mereka tidak harmonis dengan Pak Domu.", "director", "writter", R.drawable.poster_ngeringerisedap)
    val mencuriRadenSaleh : Movie = Movie("Mencuri Raden Saleh", 4, "Pencurian terbesar abad ini tinggal menghitung hari menjelang tanggal eksekusinya. Komplotan sudah lengkap dan siap menjalankan misi untuk mencuri lukisan sang maestro, Raden Saleh, yang berjudul Penangkapan Diponegoro. Pemalsuan, peretasan, pertarungan, dan manipulasi terjadi dalam pencurian berencana yang dijalankan oleh sekelompok anak muda amatiran.", "director", "writter", R.drawable.poster_mencuriradensaleh)
    val cintaPertamaKeduaKetiga : Movie = Movie("Cinta Pertama, Kedua & Ketiga", 4, "Raja dan Asia punya kesamaan tanggung jawab, mengurus kedua orang tua tunggal mereka masing-masing. Bila Raja (Angga Yunanda) ingin hidup mandiri seperti kedua kakaknya, Asia (Putri Marino) sudah memilih untuk berbakti pada ibunya yang ia rasa telah mengorbankan segalanya untuk dirinya.", "director", "writter", R.drawable.poster_cintapertamakeduaketiga)
    val pengabdiSetan : Movie = Movie("Pengabdi Setan", 4, "Beberapa tahun setelah berhasil menyelamatkan diri dari kejadian mengerikan yang membuat mereka kehilangan ibu dan si bungsu Ian, Rini dan adik-adiknya, Toni dan Bondi, serta Bapak tinggal di rumah susun karena percaya tinggal di rumah susun aman jika terjadi sesuatu karena ada banyak orang.", "director", "writter", R.drawable.poster_pengabdisetan)
    val filosofiKopi : Movie = Movie("Filosofi Kopi", 4, "Hutang bernilai ratusan juta rupiah mengancam keberadaan kedai Filosofi Kopi yang didirikan oleh Jody dan Ben. Di tengah perjuangan mengatasi hutang dan belitan konflik di antara mereka, seorang pengusaha muncul dengan tantangan yang sanggup menyelamatkan Filosofi Kopi.", "director", "writter", R.drawable.poster_filosofikopi)
    val sepertiDendam : Movie = Movie("Seperti Dendam", 4, "Ajo Kawir, seorang jagoan yang tak takut mati. Hasratnya yang besar untuk bertarung didorong oleh sebuah rahasia — ia impoten.", "director", "writter", R.drawable.poster_sepertidendam)
    val yuni : Movie = Movie("Yuni", 4, "seorang gadis yang tergolong cerdas di sekolahnya. Yuni memiliki sebuah cita-cita untuk bisa mengenyam pendidikan setinggi-tingginya. Cita-cita untuk bisa berkuliah itu mungkin sederhana, namun keinginannya tersebut juga menjadi hal tabu bagi sebagian orang di kampungnya.", "director", "writter", R.drawable.poster_yuni)
    val tekaTekiTika : Movie = Movie("Teka-Teki Tika", 4, "keluarga bahagia yang hidupnya saling mengisi satu sama lain. Saat sedang merayakan ulang tahun pernikahan bersama keluarga, Budiman (Ferry Salim), seorang pengusaha kaya tiba-tiba dikejutkan oleh kemunculan seorang perempuan misterius yang mengaku sebagai anak kandungnya.", "director", "writter", R.drawable.poster_tekatekitika)
    val garaGaraWarisan : Movie = Movie("Gara-Gara Warisan", 4, "tiga orang saudara (Adam, Laras, dan Dicky) yang memperebutkan warisan ayah mereka, Dahlan, berupa guest house. Untuk meyakinkan hatinya, akhirnya Dahlan pun memberikan sebuah tantangan kepada tiga anaknya untuk mengurus harta warisan berupa guest house keluarga tersebut.", "director", "writter", R.drawable.poster_garagarawarisan)
    val kkn : Movie = Movie("KKN", 4, "kisah enam mahasiswa bernama Nur (Tissa Biani), Widya (Adinda Thomas), Ayu (Aghniny Haque), Bima (Achmad Megantara), Anton (Calvin Jeremy), dan Wahyu (Fajar Nugraha) yang sedang melakukan KKN di desa Penari, sebuah desa yang punya kekuatan mistis amat kuat.", "director", "writter", R.drawable.poster_kkn)
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