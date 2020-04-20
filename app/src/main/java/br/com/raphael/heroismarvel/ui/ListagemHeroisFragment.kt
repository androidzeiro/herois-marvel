package br.com.raphael.heroismarvel.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.raphael.heroismarvel.MainActivity
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.model.Personagem
import br.com.raphael.heroismarvel.ui.adapters.HeroisAdapter
import br.com.raphael.heroismarvel.viewmodel.ListagemHeroisViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
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

        setupList()

        observerallCharacters()
        observerAvengers()
        observerError()
        observerLoading()
        observerTurn()

        fb_all.setOnClickListener {
            viewModel.getHerois()
        }

        fb_avengers.setOnClickListener {
            viewModel.getAvengers()
        }
    }

    private fun onHeroiClicked(item: Personagem) {
        val action = ListagemHeroisFragmentDirections.listagemHeroisToDetalhesHerois(id = item.id)
        view?.findNavController()?.navigate(action)
    }

    private fun setupList(){
        rv_listagem.apply {
            adapter = this@ListagemHeroisFragment.adapter
            setItemTransitionTimeMillis(150)
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build()
            )
        }
    }

    private fun observerallCharacters() {
        viewModel.allCharacters.observe(viewLifecycleOwner, Observer {
            if (viewModel.turn.value == true) {
                adapter.items.clear()
                adapter.items.addAll(it.data?.toMutableList() ?: mutableListOf())
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun observerAvengers() {
        viewModel.avengers.observe(viewLifecycleOwner, Observer {
            if (viewModel.turn.value == false) {
                adapter.items.clear()
                adapter.items.addAll(it.data?.toMutableList() ?: mutableListOf())
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun observerError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.attention))
                    .setContentText(it)
                    .setConfirmText(getString(R.string.ok))
                    .show()
            }
        })
    }

    private fun observerLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            pb_carregando.isVisible = it
        })
    }

    private fun observerTurn(){
        viewModel.turn.observe(viewLifecycleOwner, Observer {
            it?.let {
                fb_all.isEnabled = !it
                fb_avengers.isEnabled = it
                if(it){
                    if (activity is MainActivity) {
                        (activity as AppCompatActivity?)?.supportActionBar?.title =
                            getString(R.string.all_characters)
                    }
                    fb_all.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimaryDark))
                    fb_avengers.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimary))
                } else {
                    if (activity is MainActivity) {
                        (activity as AppCompatActivity?)?.supportActionBar?.title =
                            getString(R.string.avengers)
                    }
                    fb_avengers.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimaryDark))
                    fb_all.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimary))
                }
            }
        })
    }
}
