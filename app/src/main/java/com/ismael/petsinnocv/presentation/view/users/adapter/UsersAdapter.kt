package com.ismael.petsinnocv.presentation.view.users.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ismael.petsinnocv.core.utils.Utils
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.databinding.ItemUserBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UsersAdapter(
  private val onClickDelete : ((User) -> Unit)? = null
) : RecyclerView.Adapter<UsersHolder>(){
  private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
      return oldItem == newItem
    }

  })

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
    val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UsersHolder(onClickDelete, binding)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onBindViewHolder(holder: UsersHolder, position: Int) {
    val valor = differ.currentList[position]
    holder.bind(valor)
  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }

  fun submitList(users: List<User>?) {
    differ.submitList(users)
  }
}

class UsersHolder(
  private val onClickDelete: ((User) -> Unit)?,
  private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {
  @RequiresApi(Build.VERSION_CODES.O)
  fun bind(item : User) {
    binding.txtName.text = if (item.name.isNullOrEmpty()) "Nombre Desconocido" else item.name
    val date = item.birthdate?.substring(0,19)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val finalDate = LocalDate.parse(date, formatter)
    binding.txtBirthdate.text = "Birthdate : ${Utils.convertDate("yyyy-MM-dd", "dd-MM-yyyy", finalDate.toString())}"

    onClickDelete?.let {accion ->
      binding.btnDelete.setOnClickListener { accion(item) }
    }
  }
}
