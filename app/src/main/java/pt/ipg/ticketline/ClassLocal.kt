package pt.ipg.ticketline

import android.content.ContentValues

data class ClassLocal(
        var nome_local: String,
        var localizacao: String,
        var endereço: String,
        var capacidade: String,

        var tipo_recinto_id: Long,

        var id: Long = -1,
        ) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDLocais.CAMPO_NOME_LOCAL, nome_local)
        valores.put(TabelaBDLocais.CAMPO_LOCALIZACAO, localizacao)
        valores.put(TabelaBDLocais.CAMPO_ENDRECO, endereço)
        valores.put(TabelaBDLocais.CAMPO_CAPACIDADE, capacidade)

        valores.put(TabelaBDLocais.CAMPO_TIPO_RECINTO_ID, tipo_recinto_id)


        return valores
    }
}

