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
    fun appContext() =
            InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDEventoOpenHelper(appContext())
        return openHelper.writableDatabase
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

        val tipoRecinto = ClassTipoRecinto("Pavilhão")
        TabelaBDTipoRecinto(db).insert(tipoRecinto.toContentValues())
    }

    @Test
    fun consegueInserirPromotor() {
        val db = getWritableDatabase()

        val promotor = ClassPromotor("CARLOS VELOSO - MUSIC & EVENTS, LDA.")
        TabelaBDPromotor(db).insert(promotor.toContentValues())
    }

    @Test
    fun consegueInserirLocal() {
        val db = getWritableDatabase()

        val local = ClassLocal("Multiusos De Guimarães","Guimarães, Braga","Alameda Cidade de Lisboa 481, Guimarães","7000")
        TabelaBDLocais(db).insert(local.toContentValues())
    }

    @Test
    fun consegueInserirEvento() {
        val db = getWritableDatabase()

        val evento = ClassEvento("DIOGOPIÇARRA","08-08-2022")
        TabelaBDEventos(db).insert(evento.toContentValues())
    }

    @Test
    fun consegueInserirCategoria() {
        val db = getWritableDatabase()

        val categoria = ClassCategoria("Pop")
        TabelaBDCategoria(db).insert(categoria.toContentValues())
    }

    @Test
    fun  consegueInserirArtista(){
        val db = getWritableDatabase()

        val artista = ClassArtista("Diogo Piçarra","Faro","Português","936464297")
        TabelaBDArtistas(db).insert(artista.toContentValues())
    }
}