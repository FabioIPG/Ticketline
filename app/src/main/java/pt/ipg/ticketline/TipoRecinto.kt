package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class TipoRecinto (
    var nome_tipo_recinto: String,
    var local: Local,
    var id: Long = 1
) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO, nome_tipo_recinto)
        valores.put(TabelaBDTipoRecinto.CAMPO_LOCAL_ID, local.id)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): TipoRecinto {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDTipoRecinto.CAMPO_NOME_TIPO_RECINTO)
            val posLocalId = cursor.getColumnIndex(TabelaBDTipoRecinto.CAMPO_LOCAL_ID)
            val posNomeLocal = cursor.getColumnIndex(TabelaBDLocais.CAMPO_NOME_LOCAL)
            val posLocalizacao = cursor.getColumnIndex(TabelaBDLocais.CAMPO_LOCALIZACAO)
            val posEndereco = cursor.getColumnIndex(TabelaBDLocais.CAMPO_ENDRECO)
            val posCapacidade = cursor.getColumnIndex(TabelaBDLocais.CAMPO_CAPACIDADE)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val idLocal = cursor.getLong(posLocalId)
            val nomeLocal = cursor.getString(posNomeLocal)
            val localizacao = cursor.getString(posLocalizacao)
            val endereco = cursor.getString(posEndereco)
            val capacidade = cursor.getString(posCapacidade)

            val local = Local(nomeLocal, localizacao,endereco,capacidade,idLocal)

            return TipoRecinto(nome, local, id)
        }
    }
}