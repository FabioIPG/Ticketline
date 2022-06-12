package pt.ipg.ticketline

import android.content.ContentValues

data class ClassTipoRecinto (
        var nome_tipo_recinto: String,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO, nome_tipo_recinto)
        return valores
    }
}