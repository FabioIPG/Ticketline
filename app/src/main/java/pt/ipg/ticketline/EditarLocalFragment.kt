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
import pt.ipg.ticketline.databinding.FragmentEditarEventoBinding
import pt.ipg.ticketline.databinding.FragmentEditarLocalBinding

class EditarLocalFragment : Fragment() {
    private var _binding: FragmentEditarLocalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var local: Local? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarLocalBinding.inflate(inflater, container, false)
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
            //local = EditarLocalFragmentArgs.fromBundle(arguments!!).local


            if (local != null) {
                binding.editTextNomeLocal.setText(local!!.nome_local)
                binding.editTextLocalizacao.setText(local!!.localizacao)
                binding.editTextEndereco.setText(local!!.endereco)
                binding.editTextCapacidade.setText(local!!.capacidade)
            }
        }
    }


    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLocais()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_local = binding.editTextNomeLocal.text.toString()
        if (nome_local.isBlank()) {
            binding.editTextNomeLocal.error = getString(R.string.nome_local_obrigatorio)
            binding.editTextNomeLocal.requestFocus()
            return
        }

        val localizacao = binding.editTextLocalizacao.text.toString()
        if (localizacao.isBlank()) {
            binding.editTextLocalizacao.error = getString(R.string.localizacao_obrigatoria)
            binding.editTextLocalizacao.requestFocus()
            return
        }

        val endereco = binding.editTextEndereco.text.toString()
        if (endereco.isBlank()) {
            binding.editTextEndereco.error = getString(R.string.endereco_obrigatorio)
            binding.editTextEndereco.requestFocus()
            return
        }

        val capacidade = binding.editTextCapacidade.text.toString()
        if (capacidade.isBlank()) {
            binding.editTextCapacidade.error = getString(R.string.capacidade_obrigatoria)
            binding.editTextCapacidade.requestFocus()
            return
        }


        val localGuardado =
            if (local == null) {
                insereLocal(nome_local,localizacao,endereco,capacidade)
            } else {
                alteraLocal(nome_local,localizacao,endereco,capacidade)
            }

        if (localGuardado) {
            Toast.makeText(requireContext(), R.string.local_guardado_sucesso, Toast.LENGTH_LONG).show()
            voltaListaLocais()
        } else {
            Snackbar.make(binding.editTextNomeLocal, R.string.erro_guardar_local, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraLocal(nome_local: String,localizacao: String,endereco: String,capacidade: String) : Boolean {
        val local = Local(nome_local, localizacao, endereco,capacidade)

        val enderecoLocal = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_LOCAIS, "${this.local!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoLocal, local.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereLocal(nome_local: String,localizacao: String,endereco: String,capacidade: String): Boolean {

        val local = Local(nome_local, localizacao, endereco,capacidade)

        val enderecoLocalInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_LOCAIS, local.toContentValues())

        return enderecoLocalInserido != null
    }


    private fun voltaListaLocais() {
        //findNavController().navigate(R.id.action_editar_local_to_lista_locais)
    }

}