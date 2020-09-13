package wpa.wp.myapplication.util

import timber.log.Timber

fun split(url: String): String{
    val separated: List<String> = url.split("://")
    separated[0] // this will contain "Fruit"
    separated[1] // this will contain " they taste good"
    val urlCorrectBeginning = "https://i.wpimg.pl/500x/"

//    Timber.tag("NOPE").d("urlki: ${separated[1]} : ${urlCorrectBeginning.plus(separated[1])}")

    return urlCorrectBeginning.plus(separated[1])
}