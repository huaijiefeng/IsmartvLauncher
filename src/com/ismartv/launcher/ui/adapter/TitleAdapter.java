//package com.ismartv.launcher.ui.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import com.android.volley.toolbox.NetworkImageView;
//import com.ismartv.launcher.R;
//import com.ismartv.launcher.data.TvHomeBean;
//import org.sakuratya.horizontal.adapter.HGridAdapter;
//
//import java.util.List;
//
///**
// * Created by <huaijiefeng@gmail.com> on 9/2/14.
// */
//public class TitleAdapter extends HGridAdapter<TvHomeBean.Object> {
//    private Context context;
//    private List<TvHomeBean.Object> objects;
//
//    public TitleAdapter(Context context, List<TvHomeBean.Object> objects) {
//        this.context = context;
//        this.objects = objects;
//
//
//    }
//
//    @Override
//    public int getSectionIndex(int position) {
//        return 0;
//    }
//
//    @Override
//    public int getSectionCount(int sectionIndex) {
//        return 0;
//    }
//
//    @Override
//    public String getLabelText(int sectionIndex) {
//        return null;
//    }
//
//    @Override
//    public int getCount() {
//        return objects.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return objects.get(position);
//    }
//
//    @Override
//    public long getItemId(int id) {
//        return id;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        Holder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_content, null);
//            holder = new Holder();
//            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.content_image);
//            holder.textView = (TextView) convertView.findViewById(R.id.content_title);
//            convertView.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
//        return convertView;
//    }
//
//    class Holder {
//        NetworkImageView imageView;
//        TextView textView;
//    }
//}
