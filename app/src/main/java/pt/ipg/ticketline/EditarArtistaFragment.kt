package pt.ipg.ticketline

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.ticketline.EditarArtistaFragment.Companion.ID_LOADER_NACIONALIDADES
import pt.ipg.ticketline.ListaNacionalidadesFragment.Companion.ID_LOADER_NACIONALIDADES
import pt.ipg.ticketline.databinding.FragmentEditarArtistaBinding
import pt.ipg.ticketline.databinding.FragmentEditarEventoBinding

class EditarArtistaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarArtistaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var artista: Artista? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarArtistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            //artista = EditarArtistaFragmentArgs.fromBundle(arguments!!).artista


            if (artista != null) {
                binding.editTextNomeArtista.setText(artista!!.nome_do_artista)
                binding.editTextEnderecoArt.setText(artista!!.endereco)
                binding.editTextTelemovelArt.setText(artista!!.telemovel)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_NACIONALIDADES, null, this)
    }

    companion object {
        const val ID_LOADER_NACIONALIDADES = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderEventos.ENDERECO_NACIONALIDADES,
            TabelaBDNacionalidade.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDNacionalidade.CAMPO_NACIONALIDADE}"
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val adapterNacionalidades = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDNacionalidade.CAMPO_NACIONALIDADE),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.spinnerNacionalidades.adapter = adapterNacionalidades

        atualizaNacionalidadeSelecionada()
    }

    private fun atualizaNacionalidadeSelecionada() {
        if (artista == null) return
        val idNacionalidade = artista!!.nacionalidade.id

        val ultimaNacionalidade = binding.spinnerNacionalidades.count - 1

        for (i in 0..ultimaNacionalidade) {
            if (binding.spinnerNacionalidades.getItemIdAtPosition(i) == idNacionalidade) {
                binding.spinnerNacionalidades.setSelection(i)
                return
            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerNacionalidades.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaAristas()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_artista = binding.editTextNomeArtista.text.toString()
        if (nome_artista.isBlank()) {
            binding.editTextNomeArtista.error = getString(R.string.nome_artista_obrigatorio)
            binding.editTextNomeArtista.requestFocus()
            return
        }

        val endereco = binding.editTextEnderecoArt.text.toString()
        if (endereco.isBlank()) {
            binding.editTextEnderecoArt.error = getString(R.string.endereco_obrigatorio)
            binding.editTextEnderecoArt.requestFocus()
            return
        }

        val telemovel = binding.editTextTelemovelArt.text.toString()
        if (nome_artista.isBlank()) {
            binding.editTextTelemovelArt.error = getString(R.string.telemovel_obrigatorio)
            binding.editTextTelemovelArt.requestFocus()
            return
        }

        val idNacionalidade = binding.spinnerNacionalidades.selectedItemId
        if (idNacionalidade == Spinner.INVALID_ROW_ID) {
            binding.textViewNacionalidade.error = getString(R.string.nacionalidade_obrigatorio)
            binding.spinnerNacionalidades.requestFocus()
            return
        }

        val artistaGuardado =
            if (artista == null) {
                insereArtista(nome_artista, endereco, telemovel, idNacionalidade)
            } else {
                alteraArtista(nome_artista, endereco, telemovel, idNacionalidade)
            }

        if (artistaGuardado) {
            Toast.makeText(requireContext(), R.string.artista_guardado_sucesso, Toast.LENGTH_LONG).show()
            voltaListaAristas()
        } else {
            Snackbar.make(binding.editTextNomeArtista, R.string.erro_guardar_artista, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraArtista(nome_artista: String, endereco: String, telemovel: String, idNacionalidade: Long) : Boolean {
        val artista = Artista(nome_artista, endereco, telemovel, Nacionalidade(id = idNacionalidade))

        val enderecoArtista = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_ARTISTAS, "${this.artista!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoArtista, artista.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereArtista(nome_artista: String, endereco: String, telemovel: String, idNacionalidade: Long): Boolean {

        val artista = Artista(nome_artista, endereco, telemovel, Nacionalidade(id = idNacionalidade))

        val enderecoArtistaInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_ARTISTAS, artista.toContentValues())

        return enderecoArtistaInserido != null
    }


    private fun voltaListaAristas() {
        findNavController().navigate(R.id.action_editar_evento_to_lista_eventos)
    }

}