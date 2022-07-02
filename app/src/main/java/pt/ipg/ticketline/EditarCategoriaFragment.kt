package pt.ipg.ticketline

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import pt.ipg.ticketline.databinding.FragmentEditarCategoriaBinding

class EditarCategoriaFragment : Fragment() {

    private var _binding: FragmentEditarCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var categoria: Categoria? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarCategoriaBinding.inflate(inflater, container, false)
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
            //categoria = EditarCategoriaFragmentArgs.fromBundle(arguments!!).categoria


            if (categoria != null) {
                binding.editTextNomeCategoria.setText(categoria!!.nome_categoria)

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
                voltaListaCategorias()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome_categoria = binding.editTextNomeCategoria.text.toString()
        if (nome_categoria.isBlank()) {
            binding.editTextNomeCategoria.error = getString(R.string.nome_categoria_obrigatorio)
            binding.editTextNomeCategoria.requestFocus()
            return
        }

        val categoriaGuardada =
            if (categoria == null) {
                insereCategoria(nome_categoria)
            } else {
                alteraCategoria(nome_categoria)
            }

        if (categoriaGuardada) {
            Toast.makeText(requireContext(), R.string.categoria_guardado_sucesso, Toast.LENGTH_LONG).show()
            voltaListaCategorias()
        } else {
            Snackbar.make(binding.editTextNomeCategoria, R.string.erro_guardar_categoria, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }


    private fun alteraCategoria(nome_categoria: String) : Boolean {
        val categoria = Categoria(nome_categoria)

        val enderecoCategoria = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_CATEGORIAS, "${this.categoria!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoCategoria, categoria.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereCategoria(nome_categoria: String): Boolean {

        val categoria = Categoria(nome_categoria)

        val enderecoCategoriaInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_CATEGORIAS, categoria.toContentValues())

        return enderecoCategoriaInserido != null
    }


    private fun voltaListaCategorias() {
        //findNavController().navigate(R.id.action_editar_categoria_to_lista_categorias)
    }

}