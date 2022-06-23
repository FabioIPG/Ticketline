package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassEvento(

        var nome_evento: String,
        var data: Long,
        var local_id: Long,
        var id: Long = 1

) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEventos.CAMPO_NOME_EVENTO,nome_evento )
        valores.put(TabelaBDEventos.CAMPO_DATA, data)
        valores.put(TabelaBDEventos.CAMPO_LOCAL_ID, local_id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassEvento {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDEventos.CAMPO_NOME_EVENTO)
            val posData = cursor.getColumnIndex(TabelaBDEventos.CAMPO_DATA)
            val posIdLocal = cursor.getColumnIndex(TabelaBDEventos.CAMPO_LOCAL_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val data = cursor.getLong(posData)
            val idLocal = cursor.getLong(posIdLocal)

            return ClassEvento(nome,data,idLocal,id)

        }
    }
}
