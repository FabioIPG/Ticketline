package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Local(

        var nome_local: String = "",
        var localizacao: String = "",
        var endereco: String = "",
        var capacidade: String = "",
        var id: Long = 1
        ) : Serializable {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLocais.CAMPO_NOME_LOCAL, nome_local)

        valores.put(TabelaBDLocais.CAMPO_LOCALIZACAO, localizacao)
        valores.put(TabelaBDLocais.CAMPO_ENDRECO, endereco)
        valores.put(TabelaBDLocais.CAMPO_CAPACIDADE, capacidade)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Local {
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val posNome = cursor.getColumnIndex(TabelaBDLocais.CAMPO_NOME_LOCAL)

            val posLocalizacao = cursor.getColumnIndex(TabelaBDLocais.CAMPO_LOCALIZACAO)
            val posEndereco = cursor.getColumnIndex(TabelaBDLocais.CAMPO_ENDRECO)
            val posCapacidade = cursor.getColumnIndex(TabelaBDLocais.CAMPO_CAPACIDADE)


            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            val localizacao = cursor.getString(posLocalizacao)
            val endereco = cursor.getString(posEndereco)
            val capacidade = cursor.getString(posCapacidade)

            return Local(nome,localizacao,endereco,capacidade,id)

        }
    }
}

