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
import pt.ipg.ticketline.databinding.FragmentEditarCategoriaBinding
import pt.ipg.ticketline.databinding.FragmentEditarEventoBinding
import pt.ipg.ticketline.databinding.FragmentEditarTipoRecintoBinding

class EditarTipoRecintoFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarTipoRecintoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var tipoRecinto: TipoRecinto? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarTipoRecintoBinding.inflate(inflater, container, false)
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
            tipoRecinto = EditarTipoRecintoFragmentArgs.fromBundle(arguments!!).tipoRecinto


            if (tipoRecinto != null) {
                binding.editTextNomeTipoRecinto.setText(tipoRecinto!!.nome_tipo_recinto)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_LOCAIS, null, this)
    }

    companion object {
        const val ID_LOADER_LOCAIS = 0
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
            ContentProviderEventos.ENDERECO_LOCAIS,
            TabelaBDLocais.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDLocais.CAMPO_NOME_LOCAL}"
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
        val adapterLocais = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDLocais.CAMPO_NOME_LOCAL),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.spinnerLocais.adapter = adapterLocais

        atualizaLocalSelecionada()
    }

    private fun atualizaLocalSelecionada() {
        if (tipoRecinto == null) return
        val idLocal = tipoRecinto!!.local.id

        val ultimoLocal = binding.spinnerLocais.count - 1

        for (i in 0..ultimoLocal) {
            if (binding.spinnerLocais.getItemIdAtPosition(i) == idLocal) {
                binding.spinnerLocais.setSelection(i)
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
        binding.spinnerLocais.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaTipoRecintos()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_tipoRecinto = binding.editTextNomeTipoRecinto.text.toString()
        if (nome_tipoRecinto.isBlank()) {
            binding.editTextNomeTipoRecinto.error = getString(R.string.nome_tipoRecinto_obrigatorio)
            binding.editTextNomeTipoRecinto.requestFocus()
            return
        }

        val idLocal = binding.spinnerLocais.selectedItemId
        if (idLocal == Spinner.INVALID_ROW_ID) {
            binding.textViewLocal.error = getString(R.string.local_obrigatorio)
            binding.spinnerLocais.requestFocus()
            return
        }

        val tipoRecintoGuardado =
            if (tipoRecinto == null) {
                insereTipoRecinto(nome_tipoRecinto, idLocal)
            } else {
                alteraTipoRecinto(nome_tipoRecinto, idLocal)
            }

        if (tipoRecintoGuardado) {
            Toast.makeText(requireContext(), R.string.tipoTecinto_guardado_sucesso, Toast.LENGTH_LONG).show()
            voltaListaTipoRecintos()
        } else {
            Snackbar.make(binding.editTextNomeTipoRecinto, R.string.erro_guardar_tipoRecinto, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraTipoRecinto(nome_tipoRecinto: String, idLocal: Long) : Boolean {
        val tipoRecinto = TipoRecinto(nome_tipoRecinto, Local(id = idLocal))

        val enderecoTipoRecinto = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_TIPO_RECINTOS, "${this.tipoRecinto!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoTipoRecinto, tipoRecinto.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereTipoRecinto(nome_tipoRecinto: String, idLocal: Long): Boolean {

        val tipoRecinto = TipoRecinto(nome_tipoRecinto, Local(id = idLocal))

        val enderecoTipoRecintoInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_TIPO_RECINTOS, tipoRecinto.toContentValues())

        return enderecoTipoRecintoInserido != null
    }


    private fun voltaListaTipoRecintos() {
        findNavController().navigate(R.id.action_editar_tipoRecinto_to_lista_tipoRecintos)
    }

}