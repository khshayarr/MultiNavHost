package me.yadmand.instaonmvvm.data

class EmpRepository(private val empDao: EmpDao) {

    fun addEmployee(emp: EmpModelClass) = empDao.addEmployee(emp)
    fun viewEmployee() = empDao.viewEmployee()
    fun deleteEmployee(emp: EmpModelClass) = empDao.deleteEmployee(emp )
    fun updateEmployee(emp: EmpModelClass) = empDao.updateEmployee(emp )
    companion object {
        @Volatile
        private var instance: EmpRepository? = null

        fun getInstance(empDao: EmpDao) = instance ?: synchronized(this) {
            instance ?: EmpRepository(empDao).also { instance = it }
        }
    }

}