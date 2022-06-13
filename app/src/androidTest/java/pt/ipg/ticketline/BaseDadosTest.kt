package pt.ipg.ticketline

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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

    private fun  insereCategoria(db: SQLiteDatabase, categoria: ClassCategoria) {
        categoria.id = TabelaBDCategoria(db).insert(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun  inserePromotor(db: SQLiteDatabase, promotor: ClassPromotor) {
        promotor.id = TabelaBDPromotor(db).insert(promotor.toContentValues())
        assertNotEquals(-1, promotor.id)
    }

    private fun  insereTipoRecinto(db: SQLiteDatabase, tipoRecinto: ClassTipoRecinto) {
        tipoRecinto.id = TabelaBDTipoRecinto(db).insert(tipoRecinto.toContentValues())
        assertNotEquals(-1, tipoRecinto.id)
    }

    private fun  insereArtista(db: SQLiteDatabase, artista: ClassArtista) {
        artista.id = TabelaBDArtistas(db).insert(artista.toContentValues())
        assertNotEquals(-1, artista.id)
    }

    private fun  insereLocal(db: SQLiteDatabase, local: ClassLocal) {
        local.id = TabelaBDLocais(db).insert(local.toContentValues())
        assertNotEquals(-1, local.id)
    }

    private fun  insereEvento(db: SQLiteDatabase, evento: ClassEvento) {
        evento.id = TabelaBDEventos(db).insert(evento.toContentValues())
        assertNotEquals(-1, evento.id)
    }

    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDEventoOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDEventoOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirTipoRecinto() {
        val db = getWritableDatabase()

        insereTipoRecinto(db, ClassTipoRecinto("Campo"))
        db.close()
    }

    @Test
    fun consegueInserirPromotor() {
        val db = getWritableDatabase()
        inserePromotor(db, ClassPromotor("CARLOS VELOSO - MUSIC & EVENTS, LDA."))
        db.close()

    }

    @Test
    fun consegueInserirLocal() {
        val db = getWritableDatabase()

        val tipoRecinto = ClassTipoRecinto("Pavilhão")
        insereTipoRecinto(db, tipoRecinto)

        val local = ClassLocal("Multiusos De Guimarães","Guimarães, Braga","Alameda Cidade de Lisboa 481, Guimarães","7000",tipoRecinto.id)
        local.id = TabelaBDLocais(db).insert(local.toContentValues())

        assertNotEquals(-1, local.id)

        db.close()
    }

    @Test
    fun consegueInserirEvento() {
        val db = getWritableDatabase()

        val categoria = ClassCategoria("Sertanejo")
        insereCategoria(db, categoria)

        val artista = ClassArtista("Luan Santana", "Campo Grande, MS","Brasileiro","938746865",categoria.id)
        insereArtista(db, artista)

        val tipoRecinto = ClassTipoRecinto("Pavilhão Sem Cobertura")
        insereTipoRecinto(db, tipoRecinto)

        val local = ClassLocal("Pavilhão Rosa Mota","Porto","Jardins do Palácio de Cristal, R. de Dom Manuel II, 4050-346 Porto","8500", tipoRecinto.id)
        insereLocal(db, local)

        val promotor = ClassPromotor("VIBES & BEATS - UNIP.LDA")
        inserePromotor(db, promotor)

        val evento = ClassEvento("DIOGOPIÇARRA","08-08-2022", artista.id, local.id, promotor.id)
        insereEvento(db, evento)

        db.close()
    }

    @Test
    fun consegueInserirCategoria() {
        val db = getWritableDatabase()

        insereCategoria(db, ClassCategoria("Pop"))
        db.close()
    }

    @Test
    fun  consegueInserirArtista(){
        val db = getWritableDatabase()

        val categoria = ClassCategoria("Pop Rock")
        insereCategoria(db, categoria)

        val artista = ClassArtista("Diogo Piçarra","Faro","Português","936464297", categoria.id)
        artista.id = TabelaBDArtistas(db).insert(artista.toContentValues())

        assertNotEquals(-1, artista.id)

        db.close()
    }

    @Test
    fun consegueAlterarCategoria() {
        val db = getWritableDatabase()

        val categoria = ClassCategoria("Teste")
        insereCategoria(db, categoria)

        categoria.nome_categoria = "Pop"

        val registosAlterados = TabelaBDCategoria(db).update(
                categoria.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${categoria.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarArtista() {
        val db = getWritableDatabase()
        val categoria = ClassCategoria("TesteCat")
        insereCategoria(db, categoria)

        val artista = ClassArtista("teste","teste","teste","935666458",categoria.id)
        insereArtista(db, artista)

        artista.nome_do_artista = "Billie Eilish"
        artista.endereço = " Los Angeles, Califórnia, EUA"
        artista.nacionalidade = "Estadunidense"
        artista.telemovel = "933516760"
        artista.categoria_id = categoria.id

        val registosAlterados = TabelaBDArtistas(db).update(
                artista.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${categoria.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarPromotor() {
        val db = getWritableDatabase()

        val promotor = ClassPromotor("Teste")
        inserePromotor(db, promotor)

        promotor.nome_promotor = "KILT"

        val registosAlterados = TabelaBDPromotor(db).update(
                promotor.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${promotor.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarTipoRecinto() {
        val db = getWritableDatabase()

        val tipoRecinto = ClassTipoRecinto("Teste")
        insereTipoRecinto(db, tipoRecinto)

        tipoRecinto.nome_tipo_recinto = "relvado"

        val registosAlterados = TabelaBDTipoRecinto(db).update(
                tipoRecinto.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${tipoRecinto.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarLocal() {
        val db = getWritableDatabase()
        val tipoRecinto = ClassTipoRecinto("Teste")
        insereTipoRecinto(db, tipoRecinto)

        val local = ClassLocal("teste","teste","teste","9356",tipoRecinto.id)
        insereLocal(db, local)

        local.nome_local = "Centro Cultural Gil Vicente"
        local.localizacao = " Sardoal"
        local.endereço = "Rua Dom João III, 2230-135 Sardoal"
        local.capacidade = "760"
        local.tipo_recinto_id = tipoRecinto.id

        val registosAlterados = TabelaBDLocais(db).update(
                local.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${local.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    fun consegueAlterarEvento() {
        val db = getWritableDatabase()
        val promotor = ClassPromotor("Teste")
        inserePromotor(db, promotor)

        val tipoRecinto = ClassTipoRecinto("Teste")
        insereTipoRecinto(db, tipoRecinto)

        val categoria = ClassCategoria("Teste")
        insereCategoria(db, categoria)

        val artista = ClassArtista("Teste","Teste","Teste","951265245",categoria.id)
        insereTipoRecinto(db, tipoRecinto)

        val local = ClassLocal("Teste","Teste","Teste","3515", tipoRecinto.id)
        insereLocal(db, local)

        val evento = ClassEvento("Teste","Teste",artista.id,local.id,promotor.id)
        insereEvento(db, evento)

        evento.nome_evento = "Processo"
        evento.data = "18-11-2022"
        evento.artista_id = artista.id
        evento.local_id = local.id
        evento.promotor_id = promotor.id

        val registosAlterados = TabelaBDEventos(db).update(
                evento.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf("${evento.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

}