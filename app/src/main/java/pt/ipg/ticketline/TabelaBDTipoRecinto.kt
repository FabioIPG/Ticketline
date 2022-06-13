package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDTipoRecinto (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_TIPO_RECINTO TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "tipo_recinto"
        const val CAMPO_NOME_TIPO_RECINTO = "nome_tipo_recinto"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_TIPO_RECINTO)
    }
}