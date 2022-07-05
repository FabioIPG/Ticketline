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
import pt.ipg.ticketline.databinding.FragmentEliminarPromotorBinding

class EliminarPromotorFragment : Fragment() {

    private var _binding: FragmentEliminarPromotorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var promotor: Promotor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarPromotorBinding.inflate(inflater, container, false)
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

        promotor = EliminarPromotorFragmentArgs.fromBundle(arguments!!).promotor

        binding.textViewNomePromotor.text = promotor.nome_promotor

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaPromotor()
                true
            }
            R.id.action_cancelar -> {
                voltaListaPromotores()
                true
            }
            else -> false
        }

    private fun eliminaPromotor() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_promotor_label)
            setMessage(R.string.confirma_eliminar_promotor)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarPromotor() })
            show()
        }
    }

    private fun confirmaEliminarPromotor() {
        val enderecoPromotor = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_PROMOTORES, "${promotor.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoPromotor, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNomePromotor,
                R.string.erro_eliminar_promotor,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.promotor_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaPromotores()
    }

    private fun voltaListaPromotores() {

        val acao = EliminarPromotorFragmentDirections.actionEliminarPromotorFragmentToListaPromotoresFragment()
        findNavController().navigate(acao)

    }
}