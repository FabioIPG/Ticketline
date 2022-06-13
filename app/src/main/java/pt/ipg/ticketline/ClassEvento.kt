package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassEvento(

        var nome_evento: String,
        var data: String,

        var artista_id: Long,
        var local_id: Long,
        var promotor_id: Long,

        var id: Long= -1

) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEventos.CAMPO_NOME,nome_evento )
        valores.put(TabelaBDEventos.CAMPO_DATA, data)

        valores.put(TabelaBDEventos.CAMPO_ARTISTA_ID, artista_id)
        valores.put(TabelaBDEventos.CAMPO_LOCAL_ID, local_id)
        valores.put(TabelaBDEventos.CAMPO_PROMOTOR_ID, promotor_id)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassEvento {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDEventos.CAMPO_NOME)
            val posData = cursor.getColumnIndex(TabelaBDEventos.CAMPO_DATA)
            val posIdArtista = cursor.getColumnIndex(TabelaBDEventos.CAMPO_ARTISTA_ID)
            val posIdLocal = cursor.getColumnIndex(TabelaBDEventos.CAMPO_LOCAL_ID)
            val posIdPromotor = cursor.getColumnIndex(TabelaBDEventos.CAMPO_PROMOTOR_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val data = cursor.getString(posData)
            val idArtista = cursor.getLong(posIdArtista)
            val idLocal = cursor.getLong(posIdLocal)
            val idPromotor = cursor.getLong(posIdPromotor)

            return ClassEvento(nome,data,idArtista,idLocal,idPromotor,id)
        }
    }
}
