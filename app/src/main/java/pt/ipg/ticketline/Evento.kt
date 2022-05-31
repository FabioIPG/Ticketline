package pt.ipg.ticketline

import java.util.*

data class Evento(
        var id: Long,
        var nome_evento: String,
        var data: Date,
        var artistaId: Long,
        var localId: Long

) {
}