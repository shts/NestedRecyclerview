package jp.shts.android.nestedrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * like GooglePlayStore
 * http://sourceandroid365.com/horizontal-recyclerview-vertical-recyclerview-google-play-store/
 */
public class MainActivity extends AppCompatActivity {

    private static final List<Data> DATA_LIST = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            List<Data.InnerData> innerDataList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                innerDataList.add(new Data.InnerData());
            }
            DATA_LIST.add(new Data("Section " + i, innerDataList));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView r = (RecyclerView) findViewById(R.id.recycler_view);
        r.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        r.setAdapter(new DataAdapter(this, DATA_LIST));
    }

    private static class DataAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final Context context;
        private final List<Data> dataList;

        private DataAdapter(Context context, List<Data> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(ViewHolder.LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(dataList.get(position).getSection());
            holder.r.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.r.setAdapter(new InnerDataAdapter(context, dataList.get(position).getInnerDataList()));
            holder.r.setHasFixedSize(true);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @LayoutRes
        private static final int LAYOUT_ID = R.layout.list_item;

        private final TextView name;
        private final RecyclerView r;

        private ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.section);
            r = (RecyclerView) view.findViewById(R.id.inner_recycler_view);
        }
    }

    private static class InnerDataAdapter extends RecyclerView.Adapter<InnerViewHolder> {

        private final Context context;
        private final List<Data.InnerData> innerDataList;

        InnerDataAdapter(Context context, List<Data.InnerData> innerDataList) {
            this.context = context;
            this.innerDataList = innerDataList;
        }

        @Override
        public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InnerViewHolder(LayoutInflater.from(context)
                    .inflate(InnerViewHolder.LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(InnerViewHolder holder, int position) {
            holder.name.setText(innerDataList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return innerDataList.size();
        }
    }

    private static class InnerViewHolder extends RecyclerView.ViewHolder {

        @LayoutRes
        private static final int LAYOUT_ID = R.layout.inner_list_item;

        private final TextView name;
        private final ImageView icon;

        private InnerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    private static class Data {

        private final String section;
        private final List<InnerData> innerDataList;

        private Data(String section, List<InnerData> innerDataList) {
            this.section = section;
            this.innerDataList = innerDataList;
        }

        String getSection() {
            return section;
        }

        List<InnerData> getInnerDataList() {
            return innerDataList;
        }

        private static class InnerData {

            private final String title = "title";
            @LayoutRes
            private final int res = R.mipmap.ic_launcher;

            String getTitle() {
                return title;
            }

            @LayoutRes
            int getRes() {
                return res;
            }
        }
    }
}
