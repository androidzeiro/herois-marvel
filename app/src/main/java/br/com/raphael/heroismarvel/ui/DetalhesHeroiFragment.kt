package br.com.raphael.heroismarvel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.viewmodel.DetalhesHeroiViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import coil.api.load
import kotlinx.android.synthetic.main.fragment_detalhes_heroi.*
import kotlinx.android.synthetic.main.item_heroi.*
import kotlinx.android.synthetic.main.item_heroi.tv_nome

class DetalhesHeroiFragment : Fragment() {

    private val args: DetalhesHeroiFragmentArgs by navArgs()
    private val viewModel: DetalhesHeroiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalhes_heroi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHeroi(args.id)

        observerError()
        observerSuccess()
        observerLoading()
    }

    private fun observerError(){
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

    private fun observerSuccess(){
        viewModel.success.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data?.thumbnail?.path?.isNotEmpty() == true) {
                    iv_heroi_detalhes.load("${it.data.thumbnail.path}.${it.data.thumbnail.extension}") {
                        crossfade(750)
                        placeholder(R.drawable.ic_image_placeholder)
                        error(R.drawable.ic_image_placeholder)
                    }
                }

                tv_nome.text = it.data?.name
                tv_descricao.text = if(it.data?.description?.isNotEmpty() == true) it.data.description else getString(R.string.description_available)
            }
        })
    }

    private fun observerLoading(){
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            pb_carregando.isVisible = it
        })
    }
}