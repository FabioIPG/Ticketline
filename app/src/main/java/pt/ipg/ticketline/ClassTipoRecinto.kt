package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassTipoRecinto (
        var nome_tipo_recinto: String,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO, nome_tipo_recinto)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassTipoRecinto {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return ClassTipoRecinto(nome, id)
        }
    }
}