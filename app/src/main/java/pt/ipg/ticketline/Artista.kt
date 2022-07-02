package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Artista (

    var nome_do_artista: String,
    var endereco: String,
    var telemovel: String,
    var nacionalidade: Nacionalidade,
    var id: Long= 1
) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA, nome_do_artista)
        valores.put(TabelaBDArtistas.CAMPO_ENDERECO, endereco)
        valores.put(TabelaBDArtistas.CAMPO_TELEMOVEL, telemovel)
        valores.put(TabelaBDArtistas.CAMPO_NACIONALIDADE_ID, nacionalidade.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Artista {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NOME_DO_ARTISTA)
            val posEndereco = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_ENDERECO)
            val posTelemovel = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_TELEMOVEL)
            val posIdNacionalidade = cursor.getColumnIndex(TabelaBDArtistas.CAMPO_NACIONALIDADE_ID)
            val posNomeNacionalidade = cursor.getColumnIndex(TabelaBDNacionalidade.CAMPO_NACIONALIDADE)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val endereco = cursor.getString(posEndereco)
            val telemovel = cursor.getString(posTelemovel)
            val idNacionalidade = cursor.getLong(posIdNacionalidade)
            val nomeNacionalidade = cursor.getString(posNomeNacionalidade)

            val nacionalidade = Nacionalidade(nomeNacionalidade,idNacionalidade)


            return Artista(nome,endereco,telemovel,nacionalidade,id)
        }
    }

}
