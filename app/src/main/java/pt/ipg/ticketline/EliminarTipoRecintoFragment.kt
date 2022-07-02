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
import pt.ipg.ticketline.databinding.FragmentEliminarTipoRecintoBinding

class EliminarTipoRecintoFragment : Fragment() {
    private var _binding: FragmentEliminarTipoRecintoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tipoRecinto: TipoRecinto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarTipoRecintoBinding.inflate(inflater, container, false)
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

        //tipoRecinto = EliminarTipoRecintoFragmentArgs.fromBundle(arguments!!).tipoRecinto

        binding.textViewLocal.text = tipoRecinto.nome_tipo_recinto
        binding.textViewLocal.text = tipoRecinto.local.nome_local
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaTipoRecinto()
                true
            }
            R.id.action_cancelar -> {
                voltaListaTipoRecintos()
                true
            }
            else -> false
        }

    private fun eliminaTipoRecinto() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_tipoRecinto_label)
            setMessage(R.string.confirma_eliminar_tipoRecinto)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarTipoRecinto() })
            show()
        }
    }

    private fun confirmaEliminarTipoRecinto() {
        val enderecoTipoRecinto = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_TIPO_RECINTOS, "${tipoRecinto.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoTipoRecinto, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNomeTipoRecinto,
                R.string.erro_eliminar_tipoRecinto,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.tipoRecinto_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaTipoRecintos()
    }

    private fun voltaListaTipoRecintos() {
        //val acao = EliminarTipoRecintoFragmentDirections.actionEliminarTipoRecintoFragmentToListaTipoRecintosFragment()
        //findNavController().navigate(acao)
    }
}