package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassArtista (

        var nome_do_artista: String,
        var endereço: String,
        var nacionalidade: String,
        var telemovel: String,
        var categoria_id: Long,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA, nome_do_artista)
        valores.put(TabelaBDArtistas.CAMPO_ENDERECO, endereço)
        valores.put(TabelaBDArtistas.CAMPO_NACIONALIDADE, nacionalidade)

        valores.put(TabelaBDArtistas.CAMPO_CATEGORIA_ID, categoria_id)

        valores.put(TabelaBDArtistas.CAMPO_TELEMOVEL, telemovel)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassArtista {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA)
            val posEndereco = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_ENDERECO)
            val posNacionalidade = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NACIONALIDADE)
            val posIdCateg = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_CATEGORIA_ID)
            val posTelemovel = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_TELEMOVEL)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val endereco = cursor.getString(posEndereco)
            val nacionalidade = cursor.getString(posNacionalidade)
            val idCategoria = cursor.getLong(posIdCateg)
            val telemovel = cursor.getString(posTelemovel)

            return ClassArtista(nome,endereco,nacionalidade,telemovel,idCategoria,id)
        }
    }

}
