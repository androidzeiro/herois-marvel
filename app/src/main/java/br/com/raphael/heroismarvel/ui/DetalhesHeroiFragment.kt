package br.com.raphael.heroismarvel.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.viewmodel.DetalhesHeroiViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import coil.api.load
import coil.size.Scale
import kotlinx.android.synthetic.main.fragment_detalhes_heroi.*
import kotlinx.android.synthetic.main.item_heroi.iv_heroi
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

        viewModel.erro.observe(viewLifecycleOwner, Observer {
            it?.let {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.attention))
                    .setContentText(it)
                    .setConfirmText(getString(R.string.ok))
                    .show()
            }
        })

        viewModel.sucesso.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.thumbnail.path.isNotEmpty()) {
                    iv_heroi.load("${it.thumbnail.path}.${it.thumbnail.extension}") {
                        crossfade(750)
                        placeholder(R.drawable.ic_image_placeholder)
                        error(R.drawable.ic_image_placeholder)
                    }
                }

                tv_nome.text = it.name
                tv_descricao.text = if(it.description.isNotEmpty()) it.description else getString(R.string.description_available)
            }
        })

    }
}