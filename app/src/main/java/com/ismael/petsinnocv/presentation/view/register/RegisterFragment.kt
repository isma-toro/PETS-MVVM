package com.ismael.petsinnocv.presentation.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ismael.petsinnocv.core.utils.Utils
import com.ismael.petsinnocv.databinding.FragmentRegisterBinding
import com.ismael.petsinnocv.presentation.view.MainActivity
import com.ismael.petsinnocv.presentation.viewModel.SignUpViewModel
import com.ismael.petsinnocv.presentation.viewModel.SignUpViewModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class RegisterFragment : Fragment() {
  lateinit var binding :  FragmentRegisterBinding
  private val viewModel : SignUpViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentRegisterBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpFragment()
    setUpStates()
    initLiveData()
  }

  private fun initLiveData() {

  }

  private fun setUpStates() = viewLifecycleOwner.lifecycleScope.launch {
    viewModel.state.flowWithLifecycle(
      viewLifecycleOwner.lifecycle,
      Lifecycle.State.CREATED
    ).collect() { state ->
      when(state) {
        is SignUpViewModelState.Initial -> {
          (requireActivity() as MainActivity).mostrarLoader(false)
        }

        is SignUpViewModelState.Loading -> {
          (requireActivity() as MainActivity).mostrarLoader(true)
        }

        is SignUpViewModelState.Error -> {
          Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
          (requireActivity() as MainActivity).mostrarLoader(false)
        }

        is SignUpViewModelState.dataError ->{
          binding.layoutUsuario.error = "Usuario no válido"
          binding.layoutBirthdate.error = "Fecha no válida"
          (requireActivity() as MainActivity).mostrarLoader(false)
        }
        is SignUpViewModelState.Success -> {
          Toast.makeText(requireContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
          (requireActivity() as MainActivity).mostrarLoader(false)

        }
      }
    }
  }

  private fun setUpFragment() {
    binding.edtBirthdate.setOnClickListener {
      Utils.pickerDialog(requireContext()) { it->
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
        binding.edtBirthdate.setText(sdf.format(it.time))
      }
    }

    binding.btnSingUp.setOnClickListener {
      viewModel.checkUser(binding.edtUsuario.text, binding.edtBirthdate.text)
    }
  }
}