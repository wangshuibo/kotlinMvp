package com.wang.javamvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.javamvp.base.ui.BaseActivity;
import com.wang.javamvp.bean.ResultsBean;
import com.wang.kotlinmvp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:40
 * @fuction JavaMvp框架示例
 */
public class JavaActivity extends BaseActivity<JavaPresenter> implements JavaView {
    @BindView(R.id.kotlin_rv)
    RecyclerView kotlin_rv;
    //适配器
    private MainAdapter adapter = null;
    private List<ResultsBean> datas = null;

    @Override
    protected int getLayoutId() {
        return R.layout.act_kotlin;
    }

    @Override
    protected JavaPresenter initPresenter() {
        return new JavaPresenter(this);
    }

    @Override
    protected void initView() {
        //初始化RecyclerView
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        datas = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        kotlin_rv.setLayoutManager(layoutManager);
        adapter = new MainAdapter(mContext, datas);
        kotlin_rv.setAdapter(adapter);
        adapter.addData(datas);
        getPresenter().getListDatas();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String code, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListData(List<ResultsBean> datas) {
        adapter.updateData(datas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空数据，防止内存泄漏
        adapter = null;
    }

    /**
     * description：RecyclerView的adapter
     */
    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>

    {
        private Context context;
        private List<ResultsBean> newsList;

        public MainAdapter(Context context, List<ResultsBean> newsList) {
            this.context = context;
            this.newsList = newsList;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (newsList.size() > 0) {
                ResultsBean datas = newsList.get(position);
                holder.tvTitle.setText(datas.getDesc());
            }
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder

        {
            TextView tvTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.item_rv_title);
            }
        }


        /**
         * 添加数据
         */
        void addData(List<ResultsBean> dataList) {
            if (dataList.size() == 0) {
                return;
            }
            newsList.addAll(dataList);
            notifyDataSetChanged();
        }

        /**
         * 更新数据
         */
        void updateData(List<ResultsBean> dataList) {
            if (dataList.size() == 0) {
                return;
            }
            newsList.clear();
            newsList.addAll(dataList);
            notifyDataSetChanged();
        }

    }
}
