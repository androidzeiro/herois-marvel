package br.com.raphael.heroismarvel.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
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
            if (viewModel.all.value == true) {
                adapter.items.clear()
                adapter.items.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.avengers.observe(viewLifecycleOwner, Observer {
            if (viewModel.all.value == false) {
                adapter.items.clear()
                adapter.items.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.erro.observe(viewLifecycleOwner, Observer {
            it?.let {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.attention))
                    .setContentText(it)
                    .setConfirmText(getString(R.string.ok))
                    .show()
            }
        })

        viewModel.all.observe(viewLifecycleOwner, Observer {
            it?.let {
                fb_all.isEnabled = !it
                fb_avengers.isEnabled = it
                if(it){
                    (activity as AppCompatActivity?)?.supportActionBar?.title = getString(R.string.all_characters)
                    fb_all.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimaryDark))
                    fb_avengers.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimary))
                } else {
                    (activity as AppCompatActivity?)?.supportActionBar?.title = getString(R.string.avengers)
                    fb_avengers.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimaryDark))
                    fb_all.backgroundTintList = ColorStateList.valueOf(getColor(requireContext(), R.color.colorPrimary))
                }
            }
        })

        fb_all.setOnClickListener {
            viewModel.getHerois()
        }

        fb_avengers.setOnClickListener {
            viewModel.getAvengers()
        }
    }

    fun onHeroiClicked(item: Personagem) {
        val action = ListagemHeroisFragmentDirections.listagemHeroisToDetalhesHerois(id = item.id)
        view?.findNavController()?.navigate(action)
    }
}
