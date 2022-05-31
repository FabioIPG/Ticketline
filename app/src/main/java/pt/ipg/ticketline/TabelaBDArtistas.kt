package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDArtistas(val db: SQLiteDatabase) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME TEXT NOT NULL),"+
                " $CAMPO_GENERO_MUSICAL TEXT NOT NULL),"+
                " $CAMPO_NACIONALIDADE TEXT NOT NULL),")
    }

    companion object {
        const val NOME = "artistas"
        const val CAMPO_NOME = "nome_artista"
        const val CAMPO_GENERO_MUSICAL = "genero_musical"
        const val CAMPO_NACIONALIDADE = "nacionalidade"

    }
}