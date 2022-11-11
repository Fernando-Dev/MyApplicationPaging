package br.com.fulltime.myapplicationpaging



object Repository {


    fun getPageForIndex(pageIndex: Int): List<Int> {
        when (pageIndex) {
            1 -> {
                return getListPaginated(1, 10)
            }
            2 -> {
                return getListPaginated(11, 20)
            }
            3 -> {
                return getListPaginated(21, 30)
            }
            4 -> {
                return getListPaginated(31, 40)
            }
            5 -> {
                return getListPaginated(41, 50)
            }
            else -> {
                return listOf()
            }
        }
    }

    private fun getListPaginated(i: Int, f: Int): List<Int> {
        val list = arrayListOf<Int>()
        for (d in i..f) {
            list.add(d)
        }
        return list
    }
}