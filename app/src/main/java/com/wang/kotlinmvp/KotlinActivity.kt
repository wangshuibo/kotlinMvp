package com.wang.kotlinmvp

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.wang.kotlinmvp.app.MyApp
import com.wang.kotlinmvp.base.ui.BaseActivity
import com.wang.kotlinmvp.bean.ResultsBean
import kotlinx.android.synthetic.main.act_kotlin.*

/**
 * @author Mis Wang
 * @date  2018/5/14  10:21
 * @fuction Kotlin框架
 */
class KotlinActivity : BaseActivity<MainPresenter>(), MainView {


    //适配器
    var adapter: MainAdapter? = null
    var datas: MutableList<ResultsBean>? = null
    override fun showLoading(title: String) {

    }

    override fun stopLoading() {
    }

    override fun showErrorTip(code: String, msg: String) {
        Toast.makeText(MyApp.instance, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getListData(datas: MutableList<ResultsBean>) {
        adapter!!.updateData(datas)
    }

    override fun initPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun initView() {
        //初始化RecyclerView
        initRecyclerView()
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        datas = ArrayList()
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        kotlin_rv.layoutManager = layoutManager
        adapter = MainAdapter(mContext!!, datas!!)
        kotlin_rv.adapter = adapter
        adapter!!.addData(datas!!)
        getPresenter()!!.getListDatas()
    }


    override fun getLayoutId(): Int {
        return R.layout.act_kotlin
    }

    override fun onDestroy() {
        super.onDestroy()
        //清空数据，防止内存泄漏
        adapter = null
    }

    /**
     * description：RecyclerView的adapter
     */
    class MainAdapter//这是构造方法
    (context: Context, newsList: MutableList<ResultsBean>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (newsList?.size as Int > position) {
                val datas = newsList?.get(position)
                holder.tvTitle.text = datas!!.desc
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_rv, parent, false)
            return ViewHolder(view)
        }

        private var context: Context? = context
        private var newsList: MutableList<ResultsBean>? = newsList

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvTitle: TextView = itemView.findViewById(R.id.item_rv_title)
        }


        override fun getItemCount(): Int {
            return newsList?.size as Int
        }


        /**
         * 添加数据
         */
        fun addData(dataList: MutableList<ResultsBean>) {
            //这里不用像java一样判断空了,这里肯定是非空的
            if (dataList.size == 0) {
                return
            }
            newsList?.addAll(dataList)
            notifyDataSetChanged()
        }

        /**
         * 更新数据
         */
        fun updateData(dataList: MutableList<ResultsBean>) {
            if (dataList.size == 0) {
                return
            }
            newsList?.clear()
            newsList?.addAll(dataList)
            notifyDataSetChanged()
        }

    }

}