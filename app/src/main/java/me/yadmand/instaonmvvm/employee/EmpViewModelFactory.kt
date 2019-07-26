package me.yadmand.instaonmvvm.employee


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.yadmand.instaonmvvm.data.EmpRepository

class EmpViewModelFactory (private val emptRepository: EmpRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmpViewModelFactory(emptRepository) as T
    }
}