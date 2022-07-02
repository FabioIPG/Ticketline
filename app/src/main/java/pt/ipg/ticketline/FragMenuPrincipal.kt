package pt.ipg.ticketline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.ticketline.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragMenuPrincipal : Fragment() {


    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonEventos.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaEventos)
        }

        binding.buttonArtistas.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaArtistas)
        }

        binding.buttonCategorias.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaCategorias)
        }

        binding.buttonLocais.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaLocais)
        }

        binding.buttonNacionalidade.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaNacionalidades)
        }

        binding.buttonPromotores.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaPromotores)
        }
        binding.buttonTipoRecintos.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ListaTipoRecintos)
        }

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_main
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}