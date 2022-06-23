package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassArtista (

        var nome_do_artista: String,
        var endereco: String,
        var telemovel: String,
        var nacionalidade_id: Long,
        var id: Long= 1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA, nome_do_artista)
        valores.put(TabelaBDArtistas.CAMPO_ENDERECO, endereco)
        valores.put(TabelaBDArtistas.CAMPO_TELEMOVEL, telemovel)
        valores.put(TabelaBDArtistas.CAMPO_NACIONALIDADE_ID, nacionalidade_id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassArtista {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA)
            val posEndereco = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_ENDERECO)
            val posTelemovel = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_TELEMOVEL)
            val posIdNacionalidade = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NACIONALIDADE_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val endereco = cursor.getString(posEndereco)
            val telemovel = cursor.getString(posTelemovel)
            val idNacionalidade = cursor.getLong(posIdNacionalidade)


            return ClassArtista(nome,endereco,telemovel,idNacionalidade,id)
        }
    }

}
