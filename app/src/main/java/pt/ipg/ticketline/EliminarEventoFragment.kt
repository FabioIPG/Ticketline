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

class EliminarEventoFragment : Fragment() {
    private var _binding: FragmentEliminarEventoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var evento: Evento

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarEventoBinding.inflate(inflater, container, false)
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

        evento = EliminarEventoFragmentArgs.fromBundle(arguments!!).evento

        binding.textViewNomeEvento.text = evento.nome_evento
        binding.textViewData.text = evento.data
        binding.textViewLocal.text = evento.local.nome_local
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaEvento()
                true
            }
            R.id.action_cancelar -> {
                voltaListaEventos()
                true
            }
            else -> false
        }

    private fun eliminaEvento() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_evento_label)
            setMessage(R.string.confirma_eliminar_evento)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarEvento() })
            show()
        }
    }

    private fun confirmaEliminarEvento() {
        val enderecoEvento = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_EVENTOS, "${evento.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoEvento, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNomeEvento,
                R.string.erro_eliminar_evento,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.evento_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaEventos()
    }

    private fun voltaListaEventos() {
        val acao = EliminarEventoFragmentDirections.actionEliminarEventoFragmentToListaEventosFragment()
        findNavController().navigate(acao)
    }
}