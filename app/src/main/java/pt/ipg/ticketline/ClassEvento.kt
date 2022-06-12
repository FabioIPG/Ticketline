package pt.ipg.ticketline

import android.content.ContentValues

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
}
