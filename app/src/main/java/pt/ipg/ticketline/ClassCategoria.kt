package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassCategoria (
        var nome_categoria: String,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDCategoria.CAMPO_NOME_CATEGORIA, nome_categoria)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassCategoria {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDCategoria.CAMPO_NOME_CATEGORIA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return ClassCategoria(nome, id)
        }
    }
}