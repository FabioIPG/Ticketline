package pt.ipg.ticketline

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDArtistas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME_DO_ARTISTA TEXT NOT NULL," +
                " $CAMPO_ENDERECO TEXT NOT NULL," +
                " $CAMPO_TELEMOVEL TEXT NOT NULL," +
                " $CAMPO_NACIONALIDADE_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_NACIONALIDADE_ID) REFERENCES ${TabelaBDNacionalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDNacionalidade.NOME} ON ${TabelaBDNacionalidade.CAMPO_ID} = $CAMPO_NACIONALIDADE_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME = "artistas"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_DO_ARTISTA = "nome_do_artista"
        const val CAMPO_ENDERECO = "endereço"
        const val CAMPO_TELEMOVEL = "telemovel"
        const val CAMPO_NACIONALIDADE_ID = "nacionalidade_id"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_DO_ARTISTA, CAMPO_ENDERECO, CAMPO_TELEMOVEL, CAMPO_NACIONALIDADE_ID)
    }
}