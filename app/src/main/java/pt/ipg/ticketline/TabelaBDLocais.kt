package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocais(db: SQLiteDatabase) : TabelaBD(db, NOME) {

    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_LOCAL TEXT NOT NULL),"+
                " $CAMPO_LOCALIZACAO TEXT NOT NULL),"+
                " $CAMPO_ENDRECO TEXT NOT NULL),"+
                /*
                " ${CAMPO_TIPO_RECINTO_ID} INTEGER NOT NULL, FOREIGN KEY (${CAMPO_TIPO_RECINTO_ID}) REFERENCES ${TabelaBDTipoRecinto.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT),"+
                 */
                " $CAMPO_CAPACIDADE INTEGER NOT NULL)")
    }

    companion object {
        const val NOME = "locais"
        const val CAMPO_NOME_LOCAL = "nome_local"
        const val CAMPO_LOCALIZACAO = "localizacao"
        const val CAMPO_ENDRECO = "endereço"
        const val CAMPO_CAPACIDADE = "capacidade"
        /*
        const val CAMPO_TIPO_RECINTO_ID = "tipo_recinto_id"
         */

    }

}