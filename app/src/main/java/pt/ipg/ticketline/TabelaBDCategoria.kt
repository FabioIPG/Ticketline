package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDCategoria (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_CATEGORIA TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "categoria"
        const val CAMPO_NOME_CATEGORIA = "nome_categoria"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_CATEGORIA)
    }
}