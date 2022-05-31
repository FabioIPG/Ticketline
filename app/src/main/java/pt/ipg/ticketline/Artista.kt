package pt.ipg.ticketline

import android.content.ContentValues

data class Artista (
        var id: Long,
        var nome_artista: String,
        var genero_musical: String,
        var nacionalidade: String
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDArtistas.CAMPO_NOME, nome_artista)
        valores.put(TabelaBDArtistas.CAMPO_GENERO_MUSICAL, genero_musical)
        valores.put(TabelaBDArtistas.CAMPO_NACIONALIDADE, nacionalidade)

        return valores
    }
}
