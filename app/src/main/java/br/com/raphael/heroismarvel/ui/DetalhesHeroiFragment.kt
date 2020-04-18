package br.com.raphael.heroismarvel.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.viewmodel.DetalhesHeroiViewModel

class DetalhesHeroiFragment : Fragment() {

    private val viewModel: DetalhesHeroiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listagem_herois, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}