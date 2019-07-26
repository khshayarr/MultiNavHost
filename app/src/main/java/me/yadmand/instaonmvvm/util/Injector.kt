package me.yadmand.instaonmvvm.util

import androidx.fragment.app.FragmentActivity
import me.yadmand.instaonmvvm.data.Database
import me.yadmand.instaonmvvm.data.EmpRepository
import me.yadmand.instaonmvvm.employee.EmpViewModelFactory

object Injector {
    fun provideEmpViewModelFactory(context : FragmentActivity): EmpViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val empRepository = EmpRepository.getInstance(Database.getInstance(context).empDao)
        return EmpViewModelFactory(empRepository)
    }
}