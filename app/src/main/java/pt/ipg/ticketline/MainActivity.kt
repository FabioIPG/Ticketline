package pt.ipg.ticketline

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pt.ipg.ticketline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var fragment : Fragment? = null

    var idMenuAtual = R.menu.menu_main
        get() = field
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(idMenuAtual, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val opcaoProcessada : Boolean

        if (fragment is FragMenuPrincipal) {
            opcaoProcessada = (fragment as FragMenuPrincipal).processaOpcaoMenu(item)
        } else if (fragment is ListaEventosFragment) {
            opcaoProcessada = (fragment as ListaEventosFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarEventoFragment) {
            opcaoProcessada = (fragment as EditarEventoFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarEventoFragment) {
            opcaoProcessada = (fragment as EliminarEventoFragment).processaOpcaoMenu(item)
        } else {
            opcaoProcessada = false
        }

        if (opcaoProcessada) return true

        return super.onOptionsItemSelected(item)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaOpcoesLista(mostraAlterarEliminar: Boolean) {
        menu!!.findItem(R.id.action_alterar).setVisible(mostraAlterarEliminar)
        menu!!.findItem(R.id.action_eliminar).setVisible(mostraAlterarEliminar)
    }

    fun atualizaNomeEvento(id_string_nomeEvento: Int) {
        binding.toolbar.setTitle(id_string_nomeEvento)
    }

    fun atualizaNomePromotor(id_string_nomePromotor: Int) {
        binding.toolbar.setTitle(id_string_nomePromotor)
    }

    fun atualizaNomeArtista(id_string_nomeArtista: Int) {
        binding.toolbar.setTitle(id_string_nomeArtista)
    }

    fun atualizaNomeCategoria(id_string_nomeCategoria: Int) {
        binding.toolbar.setTitle(id_string_nomeCategoria)
    }

    fun atualizaNomeLocal(id_string_nomeLocal: Int) {
        binding.toolbar.setTitle(id_string_nomeLocal)
    }

    fun atualizaNomeNacionalidade(id_string_nomeNacionalidade: Int) {
        binding.toolbar.setTitle(id_string_nomeNacionalidade)
    }

    fun atualizaNomeTipoRecinto(id_string_nomeTipoRecinto: Int) {
        binding.toolbar.setTitle(id_string_nomeTipoRecinto)
    }


}