package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDNacionalidade (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NACIONALIDADE TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "nacionalidade"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NACIONALIDADE = "nome_nacionalidade"


        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NACIONALIDADE)
    }
}