package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassTipoRecinto (
        var nome_tipo_recinto: String,
        var locaisId: Long,
        var id: Long = 1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO, nome_tipo_recinto)
        valores.put(TabelaBDTipoRecinto.CAMPO_LOCAL_ID, locaisId)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassTipoRecinto {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO)
            val posLocaisId = cursor.getColumnIndex(TabelaBDTipoRecinto.CAMPO_LOCAL_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val locaisId = cursor.getLong(posLocaisId)

            return ClassTipoRecinto(nome, locaisId, id)
        }
    }
}