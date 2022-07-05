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
import pt.ipg.ticketline.databinding.FragmentEliminarLocalBinding

class EliminarLocalFragment : Fragment() {
    private var _binding: FragmentEliminarLocalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var local: Local

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarLocalBinding.inflate(inflater, container, false)
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

        local = EliminarLocalFragmentArgs.fromBundle(arguments!!).local

        binding.textViewNomeLocal.text = local.nome_local
        binding.textViewLocalizacao.text = local.localizacao
        binding.textViewEndereco.text = local.endereco
        binding.textViewCapacidade.text = local.capacidade

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaLocal()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLocais()
                true
            }
            else -> false
        }

    private fun eliminaLocal() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_local_label)
            setMessage(R.string.confirma_eliminar_local)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarLocal() })
            show()
        }
    }

    private fun confirmaEliminarLocal() {
        val enderecoLocal = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_LOCAIS, "${local.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoLocal, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNomeLocal,
                R.string.erro_eliminar_local,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.local_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaLocais()
    }

    private fun voltaListaLocais() {
        val acao = EliminarLocalFragmentDirections.actionEliminarLocalFragmentToListaLocaisFragment()
        findNavController().navigate(acao)
    }
}