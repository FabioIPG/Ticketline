package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDPromotor (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_PROMOTOR TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "promotor"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_PROMOTOR = "nome_promotor"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_PROMOTOR)
    }
}