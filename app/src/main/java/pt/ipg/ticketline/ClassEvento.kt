package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ClassEvento(

        var nome_evento: String,
        var data: Long,
        var local: ClassLocal,
        var id: Long = 1

) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEventos.CAMPO_NOME_EVENTO,nome_evento )
        valores.put(TabelaBDEventos.CAMPO_DATA, data)
        valores.put(TabelaBDEventos.CAMPO_LOCAL_ID, local.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ClassEvento {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDEventos.CAMPO_NOME_EVENTO)
            val posData = cursor.getColumnIndex(TabelaBDEventos.CAMPO_DATA)
            val posIdLocal = cursor.getColumnIndex(TabelaBDEventos.CAMPO_LOCAL_ID)
            val posNomeLocal = cursor.getColumnIndex(TabelaBDLocais.CAMPO_NOME_LOCAL)

            val posEndereco = cursor.getColumnIndex(TabelaBDLocais.CAMPO_ENDRECO)
            val posCapacidade = cursor.getColumnIndex(TabelaBDLocais.CAMPO_CAPACIDADE)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val data = cursor.getLong(posData)
            val idLocal = cursor.getString(posIdLocal)
            val nomeLocal = cursor.getString(posNomeLocal)


            val endereco = cursor.getString(posEndereco)
            val capacidade = cursor.getString(posCapacidade)

            val local = ClassLocal(nomeLocal,idLocal,endereco,capacidade)

            return ClassEvento(nome,data,local,id)

        }
    }
}
