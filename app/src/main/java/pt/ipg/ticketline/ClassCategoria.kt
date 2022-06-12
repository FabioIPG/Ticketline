package pt.ipg.ticketline

import android.content.ContentValues

data class ClassCategoria (
        var nome_categoria: String,
        var id: Long= -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDCategoria.CAMPO_NOME_CATEGORIA, nome_categoria)
        return valores
    }
}