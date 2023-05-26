package com.ismael.petsinnocv.presentation.view.users

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismael.petsinnocv.core.utils.Utils
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.databinding.FragmentUsersBinding
import com.ismael.petsinnocv.presentation.view.MainActivity
import com.ismael.petsinnocv.presentation.view.users.adapter.UsersAdapter
import com.ismael.petsinnocv.presentation.viewModel.UsersViewModel
import com.ismael.petsinnocv.presentation.viewModel.UsersViewModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {
  lateinit var binding : FragmentUsersBinding
  private val viewModel : UsersViewModel by viewModels()
  private var adapter : UsersAdapter? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentUsersBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpFragment()
    setUpStates()
    initLiveData()
  }

  private fun initLiveData() {
    viewModel.users.observe(viewLifecycleOwner) {users ->
      adapter?.submitList(users)
    }
  }

  private fun setUpStates() = viewLifecycleOwner.lifecycleScope.launch {
    viewModel.state.flowWithLifecycle(
      viewLifecycleOwner.lifecycle,
      Lifecycle.State.CREATED
    ).collect() {state ->
      when(state) {
        is UsersViewModelState.Initial -> {
          (requireActivity() as MainActivity).mostrarLoader(false)
        }

        is UsersViewModelState.Loading -> {
          (requireActivity() as MainActivity).mostrarLoader(true)
        }

        is UsersViewModelState.Error ->{
          Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
          (requireActivity() as MainActivity).mostrarLoader(false)
        }

        is UsersViewModelState.Success -> {
          (requireActivity() as MainActivity).mostrarLoader(false)
        }
      }
    }
  }

  private fun setUpFragment() {
    adapter = UsersAdapter(onClickDelete = {
      showDeleteDialog(it)
    })
    binding.rvUsers.adapter = adapter
    binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
  }

  private fun showDeleteDialog(user: User) {
    Utils.customAlertDialog(requireContext(),
    onPossitiveButtonClick = {
      viewModel.deleteUser(user)
    },
    onNegativeButtonClick = {
      // No hacemos nada
    })
  }
}