open class Laborglas(
    name: String,
    preis: Double,
    val material: String,
    val unterkategorie: String
) : Artikel
    (name, preis, "Laborgerät") {

}