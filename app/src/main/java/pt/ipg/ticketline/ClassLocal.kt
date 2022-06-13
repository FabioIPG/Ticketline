package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

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

    companion object {
        fun fromCursor(cursor: Cursor): ClassLocal {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDLocais.CAMPO_NOME_LOCAL)
            val posLocalizacao = cursor.getColumnIndex(TabelaBDLocais.CAMPO_LOCALIZACAO)
            val posEndereco = cursor.getColumnIndex(TabelaBDLocais.CAMPO_ENDRECO)
            val posCapacidade = cursor.getColumnIndex(TabelaBDLocais.CAMPO_CAPACIDADE)
            val posIdTipoRecinto = cursor.getColumnIndex(TabelaBDLocais.CAMPO_TIPO_RECINTO_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val localizacao = cursor.getString(posLocalizacao)
            val endereco = cursor.getString(posEndereco)
            val capacidade = cursor.getString(posCapacidade)
            val idTipoRecinto = cursor.getLong(posIdTipoRecinto)

            return ClassLocal(nome,localizacao,endereco,capacidade,idTipoRecinto,id)
        }
    }
}

