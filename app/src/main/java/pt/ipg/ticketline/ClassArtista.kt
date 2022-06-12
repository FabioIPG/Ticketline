package pt.ipg.ticketline

import android.content.ContentValues

data class ClassArtista (

        var nome_do_artista: String,
        var endereço: String,
        var nacionalidade: String,
        /*
        var categoria_id: String,
         */
        var telemovel: String,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA, nome_do_artista)
        valores.put(TabelaBDArtistas.CAMPO_ENDERECO, endereço)
        valores.put(TabelaBDArtistas.CAMPO_NACIONALIDADE, nacionalidade)
        /*
        valores.put(TabelaBDArtistas.CAMPO_CATEGORIA_ID, categoria_id)
         */
        valores.put(TabelaBDArtistas.CAMPO_TELEMOVEL, telemovel)

        return valores
    }
}
