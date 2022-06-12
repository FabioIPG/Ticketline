package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDArtistas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_DO_ARTISTA TEXT NOT NULL," +
                " $CAMPO_ENDERECO TEXT NOT NULL," +
                " $CAMPO_NACIONALIDADE TEXT NOT NULL," +

                " $CAMPO_TELEMOVEL TEXT NOT NULL," +
                " $CAMPO_CATEGORIA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_CATEGORIA_ID) REFERENCES ${TabelaBDCategoria.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "artistas"
        const val CAMPO_NOME_DO_ARTISTA = "nome_do_artista"
        const val CAMPO_ENDERECO = "endereço"
        const val CAMPO_NACIONALIDADE = "nacionalidade"

        const val CAMPO_CATEGORIA_ID = "categoria_id"

        const val CAMPO_TELEMOVEL = "telemovel"

    }
}