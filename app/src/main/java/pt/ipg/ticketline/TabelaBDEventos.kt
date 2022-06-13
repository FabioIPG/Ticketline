package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEventos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME TEXT NOT NULL),"+
                " $CAMPO_DATA LONG NOT NULL),"+

                " $CAMPO_ARTISTA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ARTISTA_ID) REFERENCES ${TabelaBDArtistas.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT),"+
                " $CAMPO_LOCAL_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_LOCAL_ID) REFERENCES ${TabelaBDLocais.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT),"+
                " $CAMPO_PROMOTOR_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_PROMOTOR_ID) REFERENCES ${TabelaBDPromotor.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")

    }

    companion object {
        const val NOME = "evento"
        const val CAMPO_NOME = "nome_evento"
        const val CAMPO_DATA = "data"
        const val CAMPO_ARTISTA_ID = "artista_id"
        const val CAMPO_LOCAL_ID = "local_id"
        const val CAMPO_PROMOTOR_ID = "promotor_id"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DATA, CAMPO_ARTISTA_ID, CAMPO_LOCAL_ID, CAMPO_PROMOTOR_ID)
    }
}