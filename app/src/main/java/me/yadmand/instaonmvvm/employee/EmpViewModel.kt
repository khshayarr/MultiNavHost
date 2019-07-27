package me.yadmand.instaonmvvm.employee

import androidx.lifecycle.ViewModel
import me.yadmand.instaonmvvm.data.EmpModelClass
import me.yadmand.instaonmvvm.data.EmpRepository

// CommentRepository dependency will again be passed in the
// constructor using dependency injection
class EmpViewModel  (private val empRepository: EmpRepository) : ViewModel() {
    fun getEmp() = empRepository.viewEmployee()
    fun addEmp(emp: EmpModelClass) = empRepository.addEmployee(emp)
    fun updateEmp(emp: EmpModelClass) = empRepository.updateEmployee(emp)
    fun deleteEmp(emp: EmpModelClass) = empRepository.deleteEmployee(emp)

}
