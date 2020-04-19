package br.com.raphael.heroismarvel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.model.Personagem
import br.com.raphael.heroismarvel.ui.adapters.HeroisAdapter
import br.com.raphael.heroismarvel.viewmodel.ListagemHeroisViewModel
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_listagem_herois.*

class ListagemHeroisFragment : Fragment() {

    private val adapter by lazy {
        HeroisAdapter(
            mutableListOf()
        ) { onHeroiClicked(it) }
    }

    private val viewModel: ListagemHeroisViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listagem_herois, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_listagem.apply {
            adapter = this@ListagemHeroisFragment.adapter
            setItemTransitionTimeMillis(150)
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build()
            )
        }

        viewModel.todos.observe(viewLifecycleOwner, Observer {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.avengers.observe(viewLifecycleOwner, Observer {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.erro.observe(viewLifecycleOwner, Observer {
            it?.let {
                // Modal
                println("errroooo")
            }
        })

        fb_all.setOnClickListener {
            viewModel.getHerois()
            fb_all.isEnabled = false
            fb_avengers.isEnabled = true
        }

        fb_avengers.setOnClickListener {
            viewModel.getAvengers()
            fb_avengers.isEnabled = false
            fb_all.isEnabled = true
        }
    }

    fun onHeroiClicked(item: Personagem) {
        val action = ListagemHeroisFragmentDirections.listagemHeroisToDetalhesHerois(id = item.id)
        view?.findNavController()?.navigate(action)
    }
}
