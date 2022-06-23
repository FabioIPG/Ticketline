package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEventos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$CAMPO_NOME_EVENTO TEXT NOT NULL," +
                "$CAMPO_DATA LONG NOT NULL," +
                "$CAMPO_LOCAL_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_LOCAL_ID) REFERENCES ${TabelaBDLocais.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "evento"
        const val CAMPO_NOME_EVENTO = "nome_evento"
        const val CAMPO_DATA = "data_evento"
        const val CAMPO_LOCAL_ID = "localId"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_EVENTO,  CAMPO_DATA, CAMPO_LOCAL_ID)
    }
}