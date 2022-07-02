package pt.ipg.ticketline

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.ticketline.databinding.FragmentEditarNacionalidadeBinding
import pt.ipg.ticketline.databinding.FragmentEditarPromotorBinding

class EditarNacionalidadeFragment : Fragment() {
    private var _binding: FragmentEditarNacionalidadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var nacionalidade: Nacionalidade? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarNacionalidadeBinding.inflate(inflater, container, false)
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
            //nacionalidade = EditarNacionalidadeFragmentArgs.fromBundle(arguments!!).nacionalidade


            if (nacionalidade != null) {
                binding.editTextNacionalidade.setText(nacionalidade!!.nacionalidade)
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
                voltaListaNacionalidades()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_nacionalidade = binding.editTextNacionalidade.text.toString()
        if (nome_nacionalidade.isBlank()) {
            binding.editTextNacionalidade.error = getString(R.string.nome_nacionalidade_obrigatorio)
            binding.editTextNacionalidade.requestFocus()
            return
        }

        val nacionalidadeGuardada =
            if (nacionalidade == null) {
                insereNacionalidade(nome_nacionalidade)
            } else {
                alteraNacionalidade(nome_nacionalidade)
            }

        if (nacionalidadeGuardada) {
            Toast.makeText(requireContext(), R.string.nacionalidade_guardada_sucesso, Toast.LENGTH_LONG).show()
            voltaListaNacionalidades()
        } else {
            Snackbar.make(binding.editTextNacionalidade, R.string.erro_guardar_nacionalidade, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraNacionalidade(nomeNacionalidade: String) : Boolean {
        val nacionalidade = Nacionalidade(nomeNacionalidade)

        val enderecoNacionalidade = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_NACIONALIDADES, "${this.nacionalidade!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoNacionalidade, nacionalidade.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereNacionalidade(nomeNacionalidade: String): Boolean {

        val nacionalidade = Nacionalidade(nomeNacionalidade)

        val enderecoNacionalidadeInserida = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_NACIONALIDADES, nacionalidade.toContentValues())

        return enderecoNacionalidadeInserida != null
    }


    private fun voltaListaNacionalidades() {
        //findNavController().navigate(R.id.action_editar_nacionalidade_to_lista_nacionalidades)
    }

}