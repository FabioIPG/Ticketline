package pt.ipg.ticketline

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.ticketline.databinding.FragmentEditarEventoBinding
import pt.ipg.ticketline.databinding.FragmentEditarPromotorBinding

class EditarPromotorFragment : Fragment() {
    private var _binding: FragmentEditarPromotorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var promotor: Promotor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarPromotorBinding.inflate(inflater, container, false)
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
            promotor = EditarPromotorFragmentArgs.fromBundle(arguments!!).promotor


            if (promotor != null) {
                binding.editTextNomePromotor.setText(promotor!!.nome_promotor)
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
                voltaListaPromotores()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_promotor = binding.editTextNomePromotor.text.toString()
        if (nome_promotor.isBlank()) {
            binding.editTextNomePromotor.error = getString(R.string.nome_promotor_obrigatorio)
            binding.editTextNomePromotor.requestFocus()
            return
        }

        val promotorGuardado =
            if (promotor == null) {
                inserePromotor(nome_promotor)
            } else {
                alteraPromotor(nome_promotor)
            }

        if (promotorGuardado) {
            Toast.makeText(requireContext(), R.string.promotor_guardado_sucesso, Toast.LENGTH_LONG).show()
            voltaListaPromotores()
        } else {
            Snackbar.make(binding.editTextNomePromotor, R.string.erro_guardar_promotor, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraPromotor(nomePromotor: String) : Boolean {
        val promotor = Promotor(nomePromotor)

        val enderecoPromotor = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_PROMOTORES, "${this.promotor!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoPromotor, promotor.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun inserePromotor(nomePromotor: String): Boolean {

        val promotor = Promotor(nomePromotor)

        val enderecoPromotorInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_PROMOTORES, promotor.toContentValues())

        return enderecoPromotorInserido != null
    }


    private fun voltaListaPromotores() {
        findNavController().navigate(R.id.action_editar_promotor_to_lista_promotores)
    }

}