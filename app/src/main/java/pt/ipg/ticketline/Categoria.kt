package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria (
        var nome_categoria: String,
        var id: Long= 1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDCategoria.CAMPO_NOME_CATEGORIA, nome_categoria)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Categoria {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDCategoria.CAMPO_NOME_CATEGORIA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Categoria(nome, id)
        }
    }
}