package pt.ipg.ticketline

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.ticketline.databinding.FragmentEliminarEventoBinding
import pt.ipg.ticketline.databinding.FragmentEliminarNacionalidadeBinding

class EliminarNacionalidadeFragment : Fragment() {
    private var _binding: FragmentEliminarNacionalidadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var nacionalidade: Nacionalidade

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarNacionalidadeBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_eliminar

        nacionalidade = EliminarNacionalidadeFragmentArgs.fromBundle(arguments!!).nacionalidade

        binding.textViewNacionalidade.text = nacionalidade.nacionalidade

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaNacionalidade()
                true
            }
            R.id.action_cancelar -> {
                voltaListaNacionalidades()
                true
            }
            else -> false
        }

    private fun eliminaNacionalidade() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_nacionalidade_label)
            setMessage(R.string.confirma_eliminar_nacionalidade)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarNacionalidade() })
            show()
        }
    }

    private fun confirmaEliminarNacionalidade() {
        val enderecoNacionalidade = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_NACIONALIDADES, "${nacionalidade.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoNacionalidade, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNacionalidade,
                R.string.erro_eliminar_nacionalidade,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.nacionalidade_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaNacionalidades()
    }

    private fun voltaListaNacionalidades() {
        val acao = EliminarNacionalidadeFragmentDirections.actionEliminarNacionalidadeFragmentToListaNacionalidadesFragment()
        findNavController().navigate(acao)
    }
}