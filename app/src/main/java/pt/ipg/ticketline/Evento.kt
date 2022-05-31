package pt.ipg.ticketline

import android.content.ContentValues
import java.util.*

data class Evento(
        var id: Long,
        var nome_evento: String,
        var data: String,
        var artistaId: Long,
        var localId: Long

) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEventos.CAMPO_NOME,nome_evento )
        valores.put(TabelaBDEventos.CAMPO_DATA, data)
        valores.put(TabelaBDEventos.CAMPO_ARTISTA_ID, artistaId)
        valores.put(TabelaBDEventos.CAMPO_LOCAL_ID, localId)

        return valores
    }
}
