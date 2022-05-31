package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEventos(val db: SQLiteDatabase) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME TEXT NOT NULL),"+
                " $CAMPO_DATA DATE NOT NULL),"+
                "$CAMPO_ARTISTA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ARTISTA_ID) REFERENCES ${TabelaBDArtistas.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT),"+
                "$CAMPO_LOCAL_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_LOCAL_ID) REFERENCES ${TabelaBDLocais.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "evento"
        const val CAMPO_NOME = "nome_evento"
        const val CAMPO_DATA = "data"
        const val CAMPO_ARTISTA_ID = "artistaId"
        const val CAMPO_LOCAL_ID = "localId"

    }
}