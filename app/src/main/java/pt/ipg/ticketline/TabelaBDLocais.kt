package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocais(db: SQLiteDatabase) : TabelaBD(db, NOME) {

    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_LOCAL TEXT NOT NULL, " +
                "$CAMPO_LOCALIZACAO TEXT NOT NULL, " +
                "$CAMPO_ENDRECO TEXT NOT NULL, " +
                "$CAMPO_CAPACIDADE TEXT NOT NULL)")
    }


    companion object {
        const val NOME = "locais"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_LOCAL = "nome_local"
        const val CAMPO_LOCALIZACAO = "localizacao"
        const val CAMPO_ENDRECO = "endereço"
        const val CAMPO_CAPACIDADE = "capacidade"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_LOCAL, CAMPO_LOCALIZACAO, CAMPO_ENDRECO, CAMPO_CAPACIDADE)

    }

}