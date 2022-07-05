package pt.ipg.ticketline

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Promotor (
    var nome_promotor: String,
    var id: Long= 1
    ) : Serializable {
        fun toContentValues() : ContentValues {
            val valores = ContentValues()
            valores.put(TabelaBDPromotor.CAMPO_NOME_PROMOTOR, nome_promotor)
            return valores
        }

    companion object {
        fun fromCursor(cursor: Cursor): Promotor {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDPromotor.CAMPO_NOME_PROMOTOR)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Promotor(nome, id)
        }
    }
}