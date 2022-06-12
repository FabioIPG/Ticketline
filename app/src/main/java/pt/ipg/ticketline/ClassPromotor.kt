package pt.ipg.ticketline

import android.content.ContentValues

data class ClassPromotor (
    var nome_promotor: String,
    var id: Long= -1
    ) {
        fun toContentValues() : ContentValues {
            val valores = ContentValues()
            valores.put(TabelaBDPromotor.CAMPO_NOME_PROMOTOR, nome_promotor)
            return valores
        }
}