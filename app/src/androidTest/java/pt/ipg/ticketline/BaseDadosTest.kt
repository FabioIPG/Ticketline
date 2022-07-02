package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    private fun appContext() =
            InstrumentationRegistry.getInstrumentation().targetContext


    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDEventoOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun  insereCategoria(db: SQLiteDatabase, categoria: Categoria) {
        categoria.id = TabelaBDCategoria(db).insert(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun  insereNacionalidade(db: SQLiteDatabase, nacionalidade: Nacionalidade) {
        nacionalidade.id = TabelaBDNacionalidade(db).insert(nacionalidade.toContentValues())
        assertNotEquals(-1, nacionalidade.id)
    }

    private fun  insereArtista(db: SQLiteDatabase, artista: Artista) {
        artista.id = TabelaBDArtistas(db).insert(artista.toContentValues())
        assertNotEquals(-1, artista.id)
    }

    private fun  insereLocal(db: SQLiteDatabase, local: Local) {
        local.id = TabelaBDLocais(db).insert(local.toContentValues())
        assertNotEquals(-1, local.id)
    }

    private fun  insereTipoRecinto(db: SQLiteDatabase, tipoRecinto: TipoRecinto) {
        tipoRecinto.id = TabelaBDTipoRecinto(db).insert(tipoRecinto.toContentValues())
        assertNotEquals(-1, tipoRecinto.id)
    }

    private fun  inserePromotor(db: SQLiteDatabase, promotor: Promotor) {
        promotor.id = TabelaBDPromotor(db).insert(promotor.toContentValues())
        assertNotEquals(-1, promotor.id)
    }

    private fun  insereEvento(db: SQLiteDatabase, evento: Evento) {
        evento.id = TabelaBDEventos(db).insert(evento.toContentValues())
        assertNotEquals(-1, evento.id)
    }

    @Before
    fun apagaBaseDados() {
        //appContext().deleteDatabase(BDEventoOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDEventoOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }


    @Test
    fun consegueInserirCategoria() {
        val db = getWritableDatabase()

        insereCategoria(db, Categoria("Pop"))
        db.close()
    }

    @Test
    fun consegueInserirNacionalidade() {
        val db = getWritableDatabase()

        insereNacionalidade(db, Nacionalidade("Portuguesa"))
        db.close()
    }

    @Test
    fun  consegueInserirArtista(){
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Brasileira")
        insereNacionalidade(db, nacionalidade)

        val artista = Artista("Diogo Piçarra","Faro","936464297", nacionalidade)
        insereArtista(db, artista)

        db.close()
    }

    @Test
    fun consegueInserirLocal() {
        val db = getWritableDatabase()

        insereLocal(db, Local("Multiusos De Guimarães","Guimarães, Braga","Alameda Cidade de Lisboa 481, Guimarães","7000"))
        db.close()

    }

    @Test
    fun consegueInserirTipoRecinto() {
        val db = getWritableDatabase()

        val local = Local("Teste","Teste","Teste","9999")
        insereLocal(db, local)

        val tipoRecinto = TipoRecinto("Teste", local)
        insereTipoRecinto(db, tipoRecinto)

        db.close()
    }

    @Test
    fun consegueInserirPromotor() {
        val db = getWritableDatabase()
        inserePromotor(db, Promotor("CARLOS VELOSO - MUSIC & EVENTS, LDA."))
        db.close()

    }

    @Test
    fun consegueInserirEvento() {
        val db = getWritableDatabase()

        val local = Local("Teste2","Teste2","Teste2","8888")
        insereLocal(db, local)

        val evento = Evento("DIOGOPIÇARRA","21062022", local)
        insereEvento(db, evento)

        db.close()
    }

    @Test
    fun consegueAlterarCategoria() {
        val db = getWritableDatabase()

        val categoria = Categoria("Teste")
        insereCategoria(db, categoria)

        categoria.nome_categoria = "Pop Rock"

        val registosAlterados = TabelaBDCategoria(db).update(
            categoria.toContentValues(),
            "${TabelaBDCategoria.CAMPO_ID}=?",
            arrayOf("${categoria.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarNacionalidade() {
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Teste")
        insereNacionalidade(db, nacionalidade)

        nacionalidade.nacionalidade = "Espanhol"

        val registosAlterados = TabelaBDNacionalidade(db).update(
            nacionalidade.toContentValues(),
            "${TabelaBDNacionalidade.CAMPO_ID}=?",
            arrayOf("${nacionalidade.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarArtista() {
        val db = getWritableDatabase()
        val nacionalidade = Nacionalidade("Teste2")
        insereNacionalidade(db, nacionalidade)

        val artista = Artista("teste2","teste2","teste2",nacionalidade)
        insereArtista(db, artista)

        artista.nome_do_artista = "Billie Eilish"
        artista.endereco = " Los Angeles, Califórnia, EUA"
        artista.telemovel = "933516760"
        artista.nacionalidade = nacionalidade

        val registosAlterados = TabelaBDArtistas(db).update(
            artista.toContentValues(),
            "${TabelaBDArtistas.CAMPO_ID}=?",
            arrayOf("${artista.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarLocal() {
        val db = getWritableDatabase()

        val local = Local("teste3","teste3","teste3","teste3")
        insereLocal(db, local)

        local.nome_local = "Centro Cultural Gil Vicente"
        local.localizacao = " Sardoal"
        local.endereco = "Rua Dom João III, 2230-135 Sardoal"
        local.capacidade = "760"

        val registosAlterados = TabelaBDLocais(db).update(
            local.toContentValues(),
            "${TabelaBDLocais.CAMPO_ID}=?",
            arrayOf("${local.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarTipoRecinto() {
        val db = getWritableDatabase()

        val local = Local("teste4","teste4","teste4","teste4")
        insereLocal(db, local)

        val tipoRecinto = TipoRecinto("Teste2",local)
        insereTipoRecinto(db, tipoRecinto)

        tipoRecinto.nome_tipo_recinto = "relvado"
        tipoRecinto.local = local

        val registosAlterados = TabelaBDTipoRecinto(db).update(
            tipoRecinto.toContentValues(),
            "${TabelaBDTipoRecinto.CAMPO_ID}=?",
            arrayOf("${tipoRecinto.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarPromotor() {
        val db = getWritableDatabase()

        val promotor = Promotor("Teste2")
        inserePromotor(db, promotor)

        promotor.nome_promotor = "KILT"

        val registosAlterados = TabelaBDPromotor(db).update(
            promotor.toContentValues(),
            "${TabelaBDPromotor.CAMPO_ID}=?",
            arrayOf("${promotor.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarEvento() {
        val db = getWritableDatabase()


        val local = Local("teste5","teste5","teste5","teste5")
        insereLocal(db, local)

        val evento = Evento("Teste","22062022",local)
        insereEvento(db, evento)

        evento.nome_evento = "Processo"
        evento.data = "23062022"
        evento.local = local

        val registosAlterados = TabelaBDEventos(db).update(
            evento.toContentValues(),
            "${TabelaBDEventos.CAMPO_ID}=?",
            arrayOf("${evento.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun  consegueEliminarCategoria() {
        val db = getWritableDatabase()

        val categoria = Categoria("Teste4")
        insereCategoria(db, categoria)

        val registosEliminados = TabelaBDCategoria(db).delete(
            "${TabelaBDCategoria.CAMPO_ID}=?",
            arrayOf("${categoria.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun  consegueEliminarNacionalidade() {
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Teste4")
        insereNacionalidade(db, nacionalidade)

        val registosEliminados = TabelaBDNacionalidade(db).delete(
            "${TabelaBDNacionalidade.CAMPO_ID}=?",
            arrayOf("${nacionalidade.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarArtista() {
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Teste5")
        insereNacionalidade(db, nacionalidade)

        val artista = Artista("teste5","teste5","teste5",nacionalidade)
        insereArtista(db, artista)

        val registosEliminados = TabelaBDArtistas(db).delete(
            "${TabelaBDArtistas.CAMPO_ID}=?",
            arrayOf("${artista.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarLocal() {
        val db = getWritableDatabase()

        val local = Local("teste6","teste6","teste6","teste6")
        insereLocal(db, local)

        val registosEliminados = TabelaBDLocais(db).delete(
            "${TabelaBDLocais.CAMPO_ID}=?",
            arrayOf("${local.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarTipoRecinto() {
        val db = getWritableDatabase()

        val local = Local("teste6","teste6","teste6","teste6")
        insereLocal(db, local)

        val tipoRecinto = TipoRecinto("Teste6",local)
        insereTipoRecinto(db, tipoRecinto)

        val registosEliminados = TabelaBDTipoRecinto(db).delete(
            "${TabelaBDTipoRecinto.CAMPO_ID}=?",
            arrayOf("${tipoRecinto.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarPromotor() {
        val db = getWritableDatabase()

        val promotor = Promotor("Teste7")
        inserePromotor(db, promotor)

        val registosEliminados = TabelaBDPromotor(db).delete(
            "${TabelaBDPromotor.CAMPO_ID}=?",
            arrayOf("${promotor.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarEvento() {
        val db = getWritableDatabase()

        val local = Local("teste7","teste7","teste7","teste7")
        insereLocal(db, local)

        val evento = Evento("Teste7","23062022",local)
        insereEvento(db, evento)

        val registosEliminados = TabelaBDEventos(db).delete(
            "${TabelaBDEventos.CAMPO_ID}=?",
            arrayOf("${evento.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCategoria() {
        val db = getWritableDatabase()

        val categoria = Categoria("Teste8")
        insereCategoria(db, categoria)

        val cursor = TabelaBDCategoria(db).query(
            TabelaBDCategoria.TODAS_COLUNAS,
            "${TabelaBDCategoria.CAMPO_ID}=?",
            arrayOf("${categoria.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val categoriaBD = Categoria.fromCursor(cursor)

        assertEquals(categoria, categoriaBD)

        db.close()
    }

    @Test
    fun consegueLerNacionalidade() {
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Teste8")
        insereNacionalidade(db, nacionalidade)

        val cursor = TabelaBDNacionalidade(db).query(
            TabelaBDNacionalidade.TODAS_COLUNAS,
            "${TabelaBDNacionalidade.CAMPO_NACIONALIDADE}=?",
            arrayOf("${nacionalidade.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val nacionalidadeBD = Nacionalidade.fromCursor(cursor)

        assertEquals(nacionalidade, nacionalidadeBD)

        db.close()
    }

    @Test
    fun consegueLerArtista() {
        val db = getWritableDatabase()

        val nacionalidade = Nacionalidade("Teste9")
        insereNacionalidade(db, nacionalidade)

        val artista = Artista("teste9","teste9","teste9",nacionalidade)
        insereArtista(db, artista)

        val cursor = TabelaBDArtistas(db).query(
            TabelaBDArtistas.TODAS_COLUNAS,
            "${TabelaBDArtistas.CAMPO_ID}=?",
            arrayOf("${artista.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val artistaBD = Artista.fromCursor(cursor)

        assertEquals(artista, artistaBD)

        db.close()
    }

    @Test
    fun consegueLerLocal() {
        val db = getWritableDatabase()

        val local = Local("teste9","teste9","teste9","teste9")
        insereLocal(db, local)

        val cursor = TabelaBDLocais(db).query(
            TabelaBDLocais.TODAS_COLUNAS,
            "${TabelaBDLocais.CAMPO_ID}=?",
            arrayOf("${local.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val localBD = Local.fromCursor(cursor)

        assertEquals(local, localBD)

        db.close()
    }

    @Test
    fun consegueLerTipoRecinto() {
        val db = getWritableDatabase()

        val local = Local("teste10","teste10","teste10","teste10")
        insereLocal(db, local)

        val tipoRecinto = TipoRecinto("Teste10",local)
        insereTipoRecinto(db, tipoRecinto)

        val cursor = TabelaBDTipoRecinto(db).query(
            TabelaBDTipoRecinto.TODAS_COLUNAS,
            "${TabelaBDTipoRecinto.CAMPO_ID}=?",
            arrayOf("${tipoRecinto.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val tipoRecintoBD = TipoRecinto.fromCursor(cursor)

        assertEquals(tipoRecinto, tipoRecintoBD)

        db.close()
    }

    @Test
    fun consegueLerPromotor() {
        val db = getWritableDatabase()

        val promotor = Promotor("Teste11")
        inserePromotor(db, promotor)

        val cursor = TabelaBDPromotor(db).query(
            TabelaBDPromotor.TODAS_COLUNAS,
            "${TabelaBDPromotor.CAMPO_ID}=?",
            arrayOf("${promotor.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val promotorBD = Promotor.fromCursor(cursor)

        assertEquals(promotor, promotorBD)

        db.close()
    }

    @Test
    fun consegueLerEvento() {
        val db = getWritableDatabase()

        val local = Local("teste11","teste11","teste11","teste11")
        insereLocal(db, local)

        val evento = Evento("Teste11","24062022",local)
        insereEvento(db, evento)

        val cursor = TabelaBDEventos(db).query(
            TabelaBDEventos.TODAS_COLUNAS,
            "${TabelaBDEventos.CAMPO_ID}=?",
            arrayOf("${evento.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val eventoBD = Evento.fromCursor(cursor)

        assertEquals(evento, eventoBD)

        db.close()
    }
}