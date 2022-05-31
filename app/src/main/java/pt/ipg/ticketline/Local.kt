package pt.ipg.ticketline

import android.content.ContentValues
import android.provider.BaseColumns

data class Local(
        var id: Long,
        var nome_local: String,
        var localizacao: String,
        var endereco: String,
        var propriatario: String,
        var ano_inauguracao: Int,
        var tipo: String,
        var capacidade: Int
        ) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDLocais.CAMPO_NOME, nome_local)
        valores.put(TabelaBDLocais.CAMPO_LOCALIZACAO, localizacao)
        valores.put(TabelaBDLocais.CAMPO_ENDRECO, endereco)
        valores.put(TabelaBDLocais.CAMPO_PROPRIATARIO, propriatario)
        valores.put(TabelaBDLocais.CAMPO_ANO_INAUGURACAO, ano_inauguracao)
        valores.put(TabelaBDLocais.CAMPO_TIPO, tipo)
        valores.put(TabelaBDLocais.CAMPO_CAPACIDADE, capacidade)

        return valores
    }
}
