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
import pt.ipg.ticketline.databinding.FragmentEliminarCategoriaBinding
import pt.ipg.ticketline.databinding.FragmentEliminarEventoBinding

class EliminarCategoriaFragment : Fragment() {
    private var _binding: FragmentEliminarCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var categoria: Categoria

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarCategoriaBinding.inflate(inflater, container, false)
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

        //categoria = EliminarCategoriaFragmentArgs.fromBundle(arguments!!).categoria

        binding.textViewCategoria.text = categoria.nome_categoria

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaCategoria()
                true
            }
            R.id.action_cancelar -> {
                voltaListaCategorias()
                true
            }
            else -> false
        }

    private fun eliminaCategoria() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_categoria_label)
            setMessage(R.string.confirma_eliminar_categoria)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarCategoria() })
            show()
        }
    }

    private fun confirmaEliminarCategoria() {
        val enderecoCategoria = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_CATEGORIAS, "${categoria.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoCategoria, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewCategoria,
                R.string.erro_eliminar_categoria,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.categoria_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaCategorias()
    }

    private fun voltaListaCategorias() {
        //val acao = EliminarCategoriaFragmentDirections.actionEliminarCategoriaFragmentToListaCategoriasFragment()
        //findNavController().navigate(acao)
    }
}