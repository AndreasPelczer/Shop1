open class Shop {
    open val user: Map<String, String> = mapOf( // Benutzername und Passwort
        "benutzer1" to "passwort1",
        "benutzer2" to "passwort2"
    )
    //val artikelListe = mutableListOf<Artikel>() Brauche ich nur in der main

    init {
        // übungsartikel
        artikelListe.add(Artikel("Artikel 1", 10.99))
        artikelListe.add(Artikel("Artikel 2", 5.99))
        artikelListe.add(Artikel("Artikel 10", 8.99))
    }

    fun login(benutzername: String, passwort: String): User? {
        if (user.containsKey(benutzername) && user[benutzername] == passwort) {
            return User(benutzername)
        }
        return null
    }

    fun anzeigenArtikel() {
        println("Verfügbare Artikel:")
        println(" 1 <- Sortiert Preis aufsteigend")
        println(" 2 <- Sortiert Preis absteigend")
        println(" 3 <- Alphabetisch sortiert")
        println("")

        val auswahl = readLine()?.toIntOrNull()

        when (auswahl) {
            1 -> if (artikelListe.isEmpty()){
                println("keine Artikel vorhanden")
            }else{
                println("Verfügbare Artikel (nach Preis aufsteigend sortiert):")
                val sortierteArtikel = artikelListe.sortedBy { it.preis }
                sortierteArtikel.forEachIndexed { index, artikel ->
                    println("${index + 1}. ${artikel.name} - ${artikel.preis}")
                }}


            2 -> if (artikelListe.isEmpty()){
                println("keine Artikel vorhanden")
            }else{
                println("Verfügbare Artikel (nach Preis absteigend sortiert):")
                val sortierteArtikel = artikelListe.sortedByDescending { it.preis }
                sortierteArtikel.forEachIndexed { index, artikel ->
                    println("${index + 1}. ${artikel.name} - ${artikel.preis}")
                }}

            3 ->if (artikelListe.isEmpty()){
                println("keine Artikel vorhanden")
            }else{
                println("Verfügbare Artikel (nach Namen sortiert):")
                val sortierteArtikel = artikelListe.sortedBy { it.name }
                sortierteArtikel.forEachIndexed { index, artikel ->
                    println("${index + 1}. ${artikel.name} - ${artikel.preis}")
                }}
            0-> {
            println("Auf Wiedersehen!")
            return
            }

            else -> println("Ungültige Auswahl.")
        }
    }
}

fun artikelHinzufuegenWarenkorb(user: User, artikelIndex: Int) {
    val artikel = artikelListe.getOrNull(artikelIndex - 1)
    if (artikel != null) {
        user.warenkorb.add(artikel)
        artikelListe.removeAt(artikelIndex - 1)
        println("Artikel '${artikel.name}' wurde zum Warenkorb hinzugefügt.")
    } else {
        println("Ungültiger Artikelindex.")
    }
}

fun artikelEntfernen(user: User, artikelIndex: Int) {
    val artikel = user.warenkorb.getOrNull(artikelIndex - 1)
    if (artikel != null) {
        user.warenkorb.removeAt(artikelIndex - 1)
        artikelListe.add(artikel)
        println("Artikel '${artikel.name}' wurde aus dem Warenkorb entfernt.")
    } else {
        println("Ungültiger Artikelindex.")
    }
}

fun anzeigenWarenkorb(user: User) {
    if (user.warenkorb.isEmpty()) {
        println("Warenkorb ist leer.")
    } else {
        println("Warenkorb von ${user.name}:")
        user.warenkorb.forEachIndexed { index, artikel ->
            println("${index + 1}. ${artikel.name} - ${artikel.preis}")
        }
    }
}

fun checkout(user: User) {
    val gesamtPreis = user.warenkorb.sumByDouble { it.preis }
    println("Gesamtpreis: $gesamtPreis")
    println("Vielen Dank für Ihren Einkauf, ${user.name}!")
    user.warenkorb.clear()
}


