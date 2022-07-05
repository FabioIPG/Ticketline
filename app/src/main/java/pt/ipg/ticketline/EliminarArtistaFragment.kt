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
import pt.ipg.ticketline.databinding.FragmentEliminarArtistaBinding
import pt.ipg.ticketline.databinding.FragmentEliminarEventoBinding

class EliminarArtistaFragment : Fragment() {
    private var _binding: FragmentEliminarArtistaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var artista: Artista

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarArtistaBinding.inflate(inflater, container, false)
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

        artista = EliminarArtistaFragmentArgs.fromBundle(arguments!!).artista

        binding.textViewNomeArtista.text = artista.nome_do_artista
        binding.textViewEndereco.text = artista.endereco
        binding.textViewTelemovel.text = artista.telemovel
        binding.textViewNacionalidade.text = artista.nacionalidade.nacionalidade
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaArtista()
                true
            }
            R.id.action_cancelar -> {
                voltaListaArtistas()
                true
            }
            else -> false
        }

    private fun eliminaArtista() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_artista_label)
            setMessage(R.string.confirma_eliminar_artista)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarArtista() })
            show()
        }
    }

    private fun confirmaEliminarArtista() {
        val enderecoArtista = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_ARTISTAS, "${artista.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoArtista, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNomeArtista,
                R.string.erro_eliminar_artista,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.artista_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaArtistas()
    }

    private fun voltaListaArtistas() {
        val acao = EliminarArtistaFragmentDirections.actionEliminarArtistaFragmentToListaArtistasFragment()
        findNavController().navigate(acao)
    }
}