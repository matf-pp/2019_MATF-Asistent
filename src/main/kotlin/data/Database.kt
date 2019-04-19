package data

import tornadofx.runAsync
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

object Database {

    private const val DATABASE_PATH = "database.db"

    // TODO
    private const val DB_CREATION_SQL = """
        CREATE TABLE Courses(
            id VARCHAR(10) PRIMARY KEY
        );
    """

    private val databaseConnection: Connection by lazy {
        val existant = File(DATABASE_PATH).exists()
        Class.forName("org.sqlite.JDBC")
        val connection = DriverManager.getConnection("jdbc:sqlite:$DATABASE_PATH")

        // Ako je baza podataka neinicijalizovana, napraviti tabele
        if (!existant) {
            println("Pravi se baza podataka...")
            val createStatement = connection.createStatement()
            createStatement.executeUpdate(DB_CREATION_SQL)
            createStatement.close()

        }

        // Povratna vrednost lambda izraza (bez return)
        connection
    }

    fun loadFromDatabase() {
        runAsync {
            var statement = databaseConnection.createStatement()

            // TODO učitavanje iz baze podataka
        }
    }

}