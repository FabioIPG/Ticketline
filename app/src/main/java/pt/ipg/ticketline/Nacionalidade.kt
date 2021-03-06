package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Nacionalidade(

    var nacionalidade: String = "",
    var id: Long = 1

    ) : Serializable {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDNacionalidade.CAMPO_NACIONALIDADE, nacionalidade)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Nacionalidade {
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val posNacionalidade = cursor.getColumnIndex(TabelaBDNacionalidade.CAMPO_NACIONALIDADE)

            val id = cursor.getLong(posId)
            val nacionalidade = cursor.getString(posNacionalidade)

            return Nacionalidade(nacionalidade, id)
        }
    }
}