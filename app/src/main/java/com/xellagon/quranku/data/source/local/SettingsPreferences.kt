package com.xellagon.quranku.data.source.local

import com.chibatching.kotpref.KotprefModel

object SettingsPreferences : KotprefModel() {

     const val INDONESIA = 0
     const val ENGLISH = 1

    var currentQori by intPref(0)
    var currentLanguage by intPref(INDONESIA)
    var isOnBoarding by booleanPref(true)

    data class Qari(
        val qoriName: String,
        val qoriId: String,
        val qoriImgUrl: String
    )

    val listQori = listOf(
        Qari(
            "Abdul Basit Mujawwad",
            "Abdul_Basit_Mujawwad_128kbps",
            "https://i1.sndcdn.com/artworks-9qXHfAUmu9UxvLL5-QPvnyQ-t500x500.jpg"
        ),
        Qari(
            "Ahmed Neana",
            "Ahmed_Neana_128kbps",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrrbbSsOFr668hdI5gT1XDR7zNZVoO9necDCUvnuOcrvVgznX5nwUv6NAzMwy-PMWTCnY&usqp=CAU"
        ),
        Qari(
            "Alafasy",
            "Alafasy_128kbps",
            "https://play-lh.googleusercontent.com/rRziKjkIzJqihgCof-cA_H3v6iHUdohUyN5J0iJ1hBA_gnbsuI-A_1i_syM5D9pc0w"
        ),
        Qari(
            "Ali Jaber",
            "Ali_Jaber_128kbps",
            "https://img.inews.co.id/media/822/files/inews_new/2020/09/14/Ali_Jabber4.jpg"
        ),
        Qari(
            "Ghamadi",
            "Ghamadi_128kbps",
            "https://i.ytimg.com/vi/QlPgDAmcSOA/sddefault.jpg?sqp=-oaymwEmCIAFEOAD8quKqQMa8AEB-AHeA4AC4AOKAgwIABABGGUgRihGMA8=&rs=AOn4CLDQa6SiYfKWO1rpLJTndHfGXPsIwQ"
        ),

        )
}


