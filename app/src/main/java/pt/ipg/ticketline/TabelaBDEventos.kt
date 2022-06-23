package pt.ipg.ticketline

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDEventos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$CAMPO_NOME_EVENTO TEXT NOT NULL," +
                "$CAMPO_DATA LONG NOT NULL," +
                "$CAMPO_LOCAL_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_LOCAL_ID) REFERENCES ${TabelaBDLocais.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDLocais.NOME} ON ${TabelaBDLocais.CAMPO_ID} = $CAMPO_LOCAL_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME = "evento"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_EVENTO = "nome_evento"
        const val CAMPO_DATA = "data_evento"
        const val CAMPO_LOCAL_ID = "localId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_EVENTO,  CAMPO_DATA, CAMPO_LOCAL_ID)
    }
}