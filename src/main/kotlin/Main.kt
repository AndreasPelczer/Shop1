import java.util.regex.Pattern                                      //für die isValidEmail Funktion (nicht selbst gemacht Youtube beschreibung in der Funktion)

var isLoggedIn = false
val eMailListe: MutableMap<String, String> = mutableMapOf()         //E-Mail und Passwortspeicher die Funktionen: isCorrectPasswort und isValidEmail greifen darauf zu
val artikelListe = mutableListOf<Artikel>()                         // Liste der Artikel zum Speichern für Admin, als Lager für User

fun main() {
    while (true) {                                                  //solange User eingeloggt ist, läuft das Programm
        println("Bitte wählen Sie eine Option:")
        println("1. Einloggen")
        println("2. Registrieren")
        println("3. Beenden")

        when (readlnOrNull()) {
            "1" -> einloggen()                                      //ruft einloggen Funktion auf
            "2" -> registrieren()                                   //ruft registrieren Funktion auf
            "3" -> {
                println("Auf Wiedersehen!")
                return
            }

            else -> println("Ungültige Auswahl. Bitte versuchen Sie es erneut.")
        }
        shop()                                                      //ruft Shop auf
    }


}


fun shop() {

    val shop = Shop()                                                   //greift auf die Klasse Shop zu und dadurch funktionieren die Funktionen die in der Klasse Shop stehen
    val user = User("User")                                        //greift auf User zu und verbindet den Warenkorb

    while (true) {                                                    // so lange user im Shop bereich ist, läuft die Shop funktion
        println("Willkommen im Shop!")
        println("Bitte wählen Sie eine Aktion:")
        println("1. Artikel anzeigen")
        println("2. Artikel zum Warenkorb hinzufügen")
        println("3. Artikel aus dem Warenkorb entfernen")
        println("4. Warenkorb anzeigen")
        println("5. zur Kasse")
        println("0. Beenden")
        print("Auswahl: ")
        val auswahl = readlnOrNull()?.toIntOrNull() ?: continue        // User eingabe Shopauswahl

        when (auswahl) {
            1 -> shop.anzeigenArtikel()                                                                                 // Artikel anzeigen
            2 -> {
                println("Bitte geben Sie den Index des Artikels ein, den Sie zum Warenkorb hinzufügen möchten:")        //Warenkorb Artikel hinzufügen per Index
                val artikelIndex = readlnOrNull()?.toIntOrNull()
                if (artikelIndex != null) {
                    shop.artikelHinzufuegenWarenkorb(user, artikelIndex)
                } else {
                    println("Ungültige Eingabe.")
                }
            }

            3 -> {
                println("Bitte geben Sie den Index des Artikels ein, den Sie aus dem Warenkorb entfernen möchten:")     //Warenkorb Artikel entfernen
                val artikelIndex = readlnOrNull()?.toIntOrNull()
                if (artikelIndex != null) {
                    shop.artikelEntfernen(user, artikelIndex)
                } else {
                    println("Ungültige Eingabe.")
                }
            }

            4 -> shop.anzeigenWarenkorb(user)                                                                           //greift auf funktion im Shop zu
            5 -> shop.checkout(user)                                                                                    // Zur Kasse gehen

            0 -> {
                println("Auf Wiedersehen!")
                main()
            }

            else -> println("Ungültige Auswahl.")
        }

        println()
    }
}


fun einloggen() {
    println("E-Mail Adresse eingeben:")
    val email = readlnOrNull()
    println("Passwort eingeben:")
    val password = readlnOrNull()
    if (email != null && password != null && isValidEmail(email) && isCorrectPassword(email, password)) {               //Logged User ein, wenn passwort und User übereinstimmen
        isLoggedIn = true
        println("Erfolgreich eingeloggt!")
        //shop()
    } else if (email == "Admin") {                                                                                      //Logged Admin ein, wenn im EMAil abfrage Admin eingegeben wird
        admin()
    } else {
        println("Ungültige E-Mail Adresse oder falsches Passwort. Bitte versuchen Sie es erneut.")
        einloggen()
    }
}

fun artikelHinzufuegenLager() {                                                                  // Admin kann Artikel in die Artikelliste aufnehmen, mit Namen, Preis und Kategorie
    println("Bitte geben Sie den Namen des Artikels ein: ")
    val artikelName = readlnOrNull()
    println("Bitte geben Sie den Preis des Artikels ein: ")
    val artikelPreis = readlnOrNull()?.toDoubleOrNull()
    println("Bitte geben Sie die Kategorie des Artikels ein: ")
    val artikelKategorie = readlnOrNull()?.toDoubleOrNull()
    if (artikelName != null && artikelPreis != null) {                                          // wenn die Eingaben nichtnull sind
        val neuerArtikel = Artikel(artikelName, artikelPreis, artikelKategorie.toString())      //neuer Artikel zum Speichern wird gebaut
        artikelListe.add(neuerArtikel)                                                          //neuer Artikel wird der Liste hinzugefüt
        admin()                                                                                 // weiter im Auswahlmenü des Admins
    } else {
        println("Ungültige Eingabe.")
    }

}

fun artikelEntfernen() {                                                                            //Admin kann Artikel aus der Artikelliste entfernen
    println("Bitte geben Sie den Namen des Artikels ein der gelöscht werden soll: ")
    val artikelName = readlnOrNull()
    println("Bitte geben Sie den Preis des Artikels ein: ")
    val artikelPreis = readlnOrNull()?.toDoubleOrNull()
    if (artikelName != null && artikelPreis != null) {                                              //Überprüft eingabe, ob nicht null und macht dann weiter und
        val artikel = Artikel(artikelName, artikelPreis, unterkategorie = String())
        artikelListe.remove(artikel)                                                                //entfernt Artikel
    } else {
        println("Ungültige Eingabe.")
    }
    admin()                                                                                         // nach dem Entfernen des Artikels geht es ins Admin-Menü zurück

}

fun registrieren() {                                                                                // Speichert E-Mail-Adresse als Schlüssel und Passwort als Wert in der EMailList
    println("E-Mail Adresse eingeben:")
    val email = readlnOrNull()
    println("Passwort eingeben:")
    val password = readlnOrNull()

    if (email != null && password != null && isValidEmail(email)) {                                                     //wenn E-Mail nicht null und Passwort nicht null
        if (eMailListe.containsKey(email)) {                                                                            //wird geprüft, ob die E-Mail in der Liste ist.
            println("Diese E-Mail Adresse ist bereits registriert. Sie werden eingeloggt.")
            shop()
        } else {                                                                                                        //wenn nicht, wird die E-Mail in der Liste gespeichert
            eMailListe[email] = password
            println("Registrierung erfolgreich!")
            shop()
        }
    } else {
        println("Ungültige E-Mail Adresse oder Passwort. Bitte versuchen Sie es erneut.")
    }
}

fun anzeigenArtikel() {                                                                                                 // Einfache Schleife um Artikel anzuzeigen (Gordon)
    println("Verfügbare Artikel:")
    artikelListe.forEachIndexed { index, artikel ->                                                                     //weist jedem Artikel in der Liste einen Index zu
        println("${index + 1}. ${artikel.name} - ${artikel.preis} - ${artikel.unterkategorie}")
    }
    admin()
}

fun admin() {                                                                                                           // Admin Menü von Bankomat abgeschaut (Gordon)
    println("Willkommen, Admin!")
    println("Bitte wählen Sie eine Aktion:")
    println("1. Artikel hinzufügen")
    println("2. Artikel anzeigen")
    println("3. Artikel aus Lager entfernen")
    println("4. Logout")
    print("Auswahl: ")

    when (readlnOrNull()?.toIntOrNull()) {
        1 -> artikelHinzufuegenLager()                                                                                  //eingabe ruft entsprechende Funktion auf
        2 -> anzeigenArtikel()
        3 -> artikelEntfernen()
        4 -> {
            isLoggedIn = false                                                                                          // wird auf false gesetzt und Admin beendet die Funktion und kommt zurück zum Hauptmenü
            println("Erfolgreich ausgeloggt!")
        }

        else -> println("Ungültige Auswahl.")
    }
}


fun isValidEmail(email: String): Boolean {                                      //Youtube Tutorial Max Didley
                                                                                //E-Mail wird eingelesen: ob es passt wird zurückgegeben
    val pattern =
        Pattern.compile(                                                        //prüfung, ob folgende Zeichen in den richtigen Blöcken vorhanden sind
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,6}\$"          // Codezeile die ich nicht verstehe
        )
                                                                                //    Vergleichen   , wenn der patter und der matcher sind gleich
    val matcher = pattern.matcher(email)
    if (email in eMailListe) {
        shop()                                                                   // überprüft ob eingabe dem Muster entspricht_____überprüft nicht die mail, nur ob es dem Muster einer E-Mail entspricht
    } else {
        println("Anmelden")
    }

    return matcher.matches()                                                    // gibt true zurück, wenn's passt
}                   //Youtube

fun isCorrectPassword(email: String, password: String): Boolean {               //überprüft, ob gespeichertes Passwort und E-Mail übereinstimmen
    val storedPassword = eMailListe[email]
    return storedPassword == password
}           // Habe ich von einem meiner Kollegen erklärt bekommen und übernommen, Alex, Alex oder Viktor, oder?

