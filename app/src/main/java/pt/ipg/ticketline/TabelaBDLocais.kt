package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocais(val db: SQLiteDatabase) {

    fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME TEXT NOT NULL),"+
                " $CAMPO_LOCALIZACAO TEXT NOT NULL),"+
                " $CAMPO_ENDRECO TEXT NOT NULL),"+
                " $CAMPO_PROPRIATARIO TEXT NOT NULL),"+
                " $CAMPO_ANO_INAUGURACAO INTEGER NOT NULL),"+
                " $CAMPO_TIPO TEXT NOT NULL),"+
                " $CAMPO_CAPACIDADE INTEGER NOT NULL),")
    }

    companion object {
        const val NOME = "locais"
        const val CAMPO_NOME = "nome_local"
        const val CAMPO_LOCALIZACAO = "localizacao"
        const val CAMPO_ENDRECO = "endereco"
        const val CAMPO_PROPRIATARIO = "propriatario"
        const val CAMPO_ANO_INAUGURACAO = "ano_inauguracao"
        const val CAMPO_TIPO = "tipo"
        const val CAMPO_CAPACIDADE = "capacidade"

    }

}