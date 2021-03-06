package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Evento(

    var nome_evento: String,
    var data: String,
    var local: Local,
    var id: Long = 1

) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEventos.CAMPO_NOME_EVENTO,nome_evento )
        valores.put(TabelaBDEventos.CAMPO_DATA, data)
        valores.put(TabelaBDEventos.CAMPO_LOCAL_ID, local.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Evento {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDEventos.CAMPO_NOME_EVENTO)
            val posData = cursor.getColumnIndex(TabelaBDEventos.CAMPO_DATA)
            val posIdLocal = cursor.getColumnIndex(TabelaBDEventos.CAMPO_LOCAL_ID)
            val posNomeLocal = cursor.getColumnIndex(TabelaBDLocais.CAMPO_NOME_LOCAL)
            val posLocalizacao = cursor.getColumnIndex(TabelaBDLocais.CAMPO_LOCALIZACAO)
            val posEndereco = cursor.getColumnIndex(TabelaBDLocais.CAMPO_ENDRECO)
            val posCapacidade = cursor.getColumnIndex(TabelaBDLocais.CAMPO_CAPACIDADE)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val data = cursor.getString(posData)
            val localizacao = cursor.getString(posLocalizacao)
            val endereco = cursor.getString(posEndereco)
            val capacidade = cursor.getString(posCapacidade)
            val idLocal = cursor.getLong(posIdLocal)
            val nomeLocal = cursor.getString(posNomeLocal)


            val local = Local(nomeLocal,localizacao,endereco,capacidade,idLocal)

            return Evento(nome,data,local,id)

        }
    }
}
