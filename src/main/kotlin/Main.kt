import java.util.regex.Pattern

var isLoggedIn = false
val eMailListe: MutableMap<String, String> = mutableMapOf()
val artikelListe = mutableListOf<Artikel>()
fun main() {
    while (true) {
        println("Bitte wählen Sie eine Option:")
        println("1. Einloggen")
        println("2. Registrieren")
        println("3. Beenden")

        val userInput = readLine()

        when (userInput) {
            "1" -> einloggen()
            "2" -> registrieren()
            "3" -> {
                println("Auf Wiedersehen!")
                return
            }

            else -> println("Ungültige Auswahl. Bitte versuchen Sie es erneut.")
        }
    }


}


fun shop() {

    val shop = Shop()
    val user = User("User")

    while (true) {
        println("Willkommen im Shop! ${user.name}")
        println("Bitte wählen Sie eine Aktion:")
        println("1. Artikel anzeigen")
        println("2. Artikel zum Warenkorb hinzufügen")
        println("3. Artikel aus dem Warenkorb entfernen")
        println("4. Warenkorb anzeigen")
        println("5. zur Kasse")
        println("0. Beenden")
        print("Auswahl: ")
        val auswahl = readLine()?.toIntOrNull() ?: continue

        when (auswahl) {
            1 -> shop.anzeigenArtikel()
            2 -> {
                println("Bitte geben Sie den Index des Artikels ein, den Sie zum Warenkorb hinzufügen möchten:")
                val artikelIndex = readLine()?.toIntOrNull()
                if (artikelIndex != null) {
                    artikelHinzufuegenWarenkorb(user, artikelIndex)
                } else {
                    println("Ungültige Eingabe.")
                }
            }
            3 -> {
                println("Bitte geben Sie den Index des Artikels ein, den Sie aus dem Warenkorb entfernen möchten:")
                val artikelIndex = readLine()?.toIntOrNull()
                if (artikelIndex != null) {
                    artikelEntfernen(user, artikelIndex)
                } else {
                    println("Ungültige Eingabe.")
                }
            }
            4 -> anzeigenWarenkorb(user)
            5 -> checkout(user)
            0 -> {
                println("Auf Wiedersehen!")
                return
            }
            else -> println("Ungültige Auswahl.")
        }

        println()
    }
}


fun einloggen() {
    println("E-Mail Adresse eingeben:")
    val email = readLine()
    println("Passwort eingeben:")
    val password = readLine()


    if (email != null && password != null && isValidEmail(email) && isCorrectPassword(email, password)) {
        isLoggedIn = true
        println("Erfolgreich eingeloggt!")
        shop()
    } else if (email == "Admin"){
        admin()
    } else{
        println("Ungültige E-Mail Adresse oder falsches Passwort. Bitte versuchen Sie es erneut.")
    }
}
fun artikelHinzufuegenLager() {
    println("Bitte geben Sie den Namen des Artikels ein: ")
    val artikelName = readLine()
    println("Bitte geben Sie den Preis des Artikels ein: ")
    val artikelPreis = readLine()?.toDoubleOrNull()
    var neuerArtikel = artikelName + artikelPreis
    if (artikelName != null && artikelPreis != null) {
        val neuerArtikel = Artikel(artikelName, artikelPreis)
        artikelListe.add(neuerArtikel)
        admin()
    } else {
        println("Ungültige Eingabe.")
    }

}
fun artikelEntfernen(){
    println("Bitte geben Sie den Namen des Artikels ein der gelöscht werden soll: ")
    val artikelName = readLine()
    println("Bitte geben Sie den Preis des Artikels ein: ")
    val artikelPreis = readLine()?.toDoubleOrNull()
    var artikel = artikelName + artikelPreis
    if (artikelName != null && artikelPreis != null) {
        val artikel = Artikel(artikelName, artikelPreis)
        artikelListe.remove(artikel)
    } else {
        println("Ungültige Eingabe.")
    }
    admin()

}
fun registrieren() {
    println("E-Mail Adresse eingeben:")
    val email = readLine()
    println("Passwort eingeben:")
    val password = readLine()

    if (email != null && password != null && isValidEmail(email)) {
        if (eMailListe.containsKey(email)) {
            println("Diese E-Mail Adresse ist bereits registriert. Bitte verwenden Sie eine andere.")
        } else {
            eMailListe[email] = password
            println("Registrierung erfolgreich!")
            shop()
        }
    } else {
        println("Ungültige E-Mail Adresse oder Passwort. Bitte versuchen Sie es erneut.")
    }
}

fun anzeigenArtikel() {
    println("Verfügbare Artikel:")
    artikelListe.forEachIndexed { index, artikel ->
        println("${index + 1}. ${artikel.name} - ${artikel.preis}")
    }
    admin()
}

fun admin(){
    println("Willkommen, Admin!")
    println("Bitte wählen Sie eine Aktion:")
    println("1. Artikel hinzufügen")
    println("2. Artikel anzeigen")
    println("3. Artikel aus Lager entfernen")
    println("4. Logout")
    print("Auswahl: ")
    val auswahl = readLine()?.toIntOrNull()

    when (auswahl) {
        1 -> artikelHinzufuegenLager()
        2 -> anzeigenArtikel()
        3 -> artikelEntfernen()
        4 -> {
            isLoggedIn = false
            println("Erfolgreich ausgeloggt!")
        }
        else -> println("Ungültige Auswahl.")
    }
}

fun isValidEmail(email: String): Boolean {
    val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

fun isCorrectPassword(email: String, password: String): Boolean {
    val storedPassword = eMailListe[email]
    return storedPassword == password
}

