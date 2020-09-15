package wpa.wp.myapplication.util

fun split(url: String): String{
    val separated: List<String> = url.split("://")
    val urlCorrectBeginning = "https://i.wpimg.pl/500x/"


    return if(separated.size> 1)
        urlCorrectBeginning.plus(separated[1])
    else url
}