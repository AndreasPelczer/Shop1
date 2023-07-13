open class Shop {
    //val artikelListe = mutableListOf<Artikel>() Brauche ich nur in der main

    init {                                                                                      //Übungsliste vorinstalliert
        artikelListe.add(Artikel("Vase-grün", 10.99, "Kunstglas"))
        artikelListe.add(Artikel("Rohrkolben", 5.99, "Laborglas"))
        artikelListe.add(Artikel("Reagenzglas", 8.99, "Laborglas"))
        artikelListe.add(Artikel("Petrischale", 12.99, "Laborglas"))
        artikelListe.add(Artikel("Vase-gelb", 120.99, "Kunstglas"))
        artikelListe.add(Artikel("Frosch", 5.99, "Kunstglas"))
        artikelListe.add(Artikel("Reagenzglas5", 2.99, "Laborglas"))
        artikelListe.add(Artikel("Petrischale2", 1.99, "Laborglas"))
    }


    fun anzeigenArtikel() {
        println("Verfügbare Artikel:")
        println(" 1 <- Sortiert Preis aufsteigend")
        println(" 2 <- Sortiert Preis absteigend")
        println(" 3 <- Alphabetisch sortiert")
        println(" 4 <- unterkategorie Anzeigen")
        println("")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> if (artikelListe.isEmpty()) {
                println("keine Artikel vorhanden")
            } else {
                println("Verfügbare Artikel (nach Preis aufsteigend sortiert):")
                val sortierteArtikel = artikelListe.sortedBy { it.preis }                               // sortiert Liste nach Preis aufsteigen
                sortierteArtikel.forEachIndexed { index, artikel ->                                     //Indexiert die sortierte Liste
                    println("${index + 1}. ${artikel.name} - ${artikel.preis} ${artikel.unterkategorie}")// Ausgabe auf der Konsole
                }
            }


            2 -> if (artikelListe.isEmpty()) {
                println("keine Artikel vorhanden")
            } else {
                println("Verfügbare Artikel (nach Preis absteigend sortiert):")
                val sortierteArtikel = artikelListe.sortedByDescending { it.preis }                         //sortiert die Liste nch Preis absteigend
                sortierteArtikel.forEachIndexed { index, artikel ->                                         //Indexiert die Liste
                    println("${index + 1}. ${artikel.name} - ${artikel.preis} - ${artikel.unterkategorie} ")//Ausgabe Konsole
                }
            }

            3 -> if (artikelListe.isEmpty()) {                          //
                println("keine Artikel vorhanden")
            } else {
                println("Verfügbare Artikel (nach Namen sortiert):")
                val sortierteArtikel = artikelListe.sortedBy { it.name }                                    //sortiert die Liste nach Alphabet
                sortierteArtikel.forEachIndexed { index, artikel ->                                         //Indexiert die sortierte Liste
                    println("${index + 1}. ${artikel.name} - ${artikel.preis} - ${artikel.unterkategorie}")  //Ausgabe Konsole
                }
            }

            4 ->{
                println("Bitte Unterkategorie eingeben: Kunstglas oder Laborglas")   //mit Hilfe von Julian

                val unterkategorie = readln()
                if (unterkategorie.isNotBlank()) {
                    anzeigenNachUnterkategorie(unterkategorie)
                } else {
                    println("ungültige Eingabe")
                }

            }

            0 -> {
                println("Auf Wiedersehen!")
                return
            }

            else -> println("Ungültige Auswahl.")
        }
    }

    private fun anzeigenNachUnterkategorie(unterkategorie: String) {                                                    //Funktion sortiert nach Unterkategorie
        val gefilterteArtikel = artikelListe.filter { it.unterkategorie.equals(unterkategorie, ignoreCase = true) }     //filtert die Unterkategorie, vergleicht und Ignoriert groß-kleinschreibung(ignoreCase)

        if (gefilterteArtikel.isEmpty()) {
            println("Es sind keine Artikel in der Unterkategorie '$unterkategorie' vorhanden.")
        } else {
            println("Artikel in der Unterkategorie '$unterkategorie':")     //gibt die Unterkategorie aus
            gefilterteArtikel.forEachIndexed { index, artikel ->            //Indexiert jeden Artikel der Unterkategorie
                println("${index + 1}. ${artikel.name} - ${artikel.preis}") //Ausgabe in der Konsole
            }
        }
    }
    fun artikelHinzufuegenWarenkorb(user: User, artikelIndex: Int) {            //fügt Artikel dem Warenkorb hinzu und entfernt diesen Artikel aus der Liste
        val artikel = artikelListe.getOrNull(artikelIndex - 1)             // holt/liest den Artikel dessen Index angegeben wurde aus der Liste
        if (artikel != null) {                                                   // wenn es den Artikel gibt(also nicht null) gehts weiter, wenn nicht geht es mit else weiter
            user.warenkorb.add(artikel)                                          //Artikel wird zum Warenkorb hinzugefügt
            artikelListe.removeAt(artikelIndex - 1)                        //Artikel wird entfernt Artikelliste
            println("Artikel '${artikel.name}' wurde zum Warenkorb hinzugefügt.")
        } else {
            println("Ungültiger Artikelindex.")
        }
    }
    fun artikelEntfernen(user: User, artikelIndex: Int) {
        val artikel = user.warenkorb.getOrNull(artikelIndex - 1)         // holt/liest den Artikel dessen Index angegeben wurde aus dem Warenkorb
        if (artikel != null) {                                                 // wenn es den Artikel gibt(also nicht null) gehts weiter, wenn nicht geht es mit else weiter
            user.warenkorb.removeAt(artikelIndex - 1)                    // entfernt den Artikel aus dem Warenkorb
            artikelListe.add(artikel)                                          // und gibt ihn wieder zurück in die Artikelliste
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
            user.warenkorb.forEachIndexed { index, artikel ->                                            //Indexiert den Warenkorb
                println("${index + 1}. ${artikel.name} - ${artikel.preis} - ${artikel.unterkategorie}")  //Ausgabe in der Konsole
            }
        }
    }
    fun checkout(user: User) {                                                      //Bezahlen Funktion nicht implementiert
        val gesamtPreis = user.warenkorb.sumOf { it.preis }                         //Gesamtpreis wird errechnet
        println("Gesamtpreis: $gesamtPreis")                                        //und in der Konsole Angezeigt
        println("Vielen Dank für Ihren Einkauf, ${user.name}!")
        user.warenkorb.clear()                                                      //Warenkorb wird gelöscht
    }
}












