package br.com.fulltime.myapplicationpaging

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val DEFAULT_PAGE_INDEX = 1

class MainActivity : AppCompatActivity() {

    private var lastPageIndex = 0
    private val mainAdapter = MainAdapter()
    private var listAux = mutableListOf<Int>()
    private lateinit var textCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textCount = findViewById(R.id.text_count)
        val rv = findViewById<RecyclerView>(R.id.rv)

        /**
         * primeira chamada para o repositorio ou remote
         */
        mainAdapter.list = Repository.getPageForIndex(DEFAULT_PAGE_INDEX).toMutableList()

        /**
         * coletando o index inicial
         */
        lastPageIndex = DEFAULT_PAGE_INDEX

        /**
         * setando o tamanho total da lista inicialmente
         */
        setTotal(mainAdapter.list.size)

        /**
         * apresentando a mensagem inicial de quanto itens foram adicionados
         */
        messageCount(mainAdapter.list.size)
        rv.adapter = mainAdapter
        /**
         * criando o listener para capturar o scroll da lista
         */
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                /**
                 * pegando o gerenciador do layout da lista
                 */
                val manager = recyclerView.layoutManager as LinearLayoutManager

                /**
                 * pegando o ultimo item da lista
                 */
                val lastItem = manager.findLastVisibleItemPosition()

                /**
                 * pegando a quantidade de itens na lista
                 */
                val itemCount = recyclerView.adapter?.itemCount

                /**
                 * quando scrollar a lista e chegar no ultimo item
                 * se o scroll for positivo no eixo y
                 * isso quer dizer se o scroll foi para cima
                 * e fazemos uma subtracao para validar a condição
                 * para buscar novos itens
                 */
                if (lastItem == itemCount?.minus(1) && dy > 0) {

                    /**
                     * adicionamos mais um no contador de paginacao
                     */
                    lastPageIndex += 1

                    /**
                     * pegamos uma nova lista
                     */
                    val list = Repository.getPageForIndex(lastPageIndex)

                    /**
                     * se a lista não estiver vazia fazemos a atualização da recycler
                     */
                    if (list.isNotEmpty()) {
                        /**
                         * limpamos a lista auxiliar
                         */
                        listAux.clear()

                        /**
                         * adicionamos a lista ja carregada no adapter
                         */
                        listAux.addAll(mainAdapter.list)

                        /**
                         * adicionamos a nova lista
                         */
                        listAux.addAll(list)

                        /**
                         * limpamos a lista que ja estava carregada no adapter
                         */
                        mainAdapter.list.clear()

                        /**
                         * adicionamos a lista auxiliar com os dados atualizados
                         */
                        mainAdapter.list.addAll(listAux)

                        /**
                         * notificamos o adapter para adicionar novos itens a recycler
                         * depois do ultimo item da lista atual
                         * por isso pego o index do ultimo item e adiciono mais 1
                         * e passo o tamanho da lista atualmente
                         */
                        mainAdapter.notifyItemRangeInserted(lastItem.plus(1), itemCount)

                        /**
                         * aqui só atualizo os contadores
                         */
                        setTotal(mainAdapter.list.size)
                        messageCount(list.size)
                    }
                }
            }
        })

    }

    private fun messageCount(count: Int) {
        Toast.makeText(this, "Adicionado mais $count na lista", Toast.LENGTH_SHORT).show()
    }

    private fun setTotal(total: Int) {
        textCount.text = getString(R.string.total, total.toString())
    }

}