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
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by <huaijiefeng@gmail.com> on 9/2/14.
// */
//public class ContentAdapter extends HGridAdapter<TvHomeBean.Object> {
//    private Context context;
//    private List<TvHomeBean.Object> objects;
//
//    private int mSize = 0;
//
//    public ContentAdapter(Context context, List<TvHomeBean.Object> objects) {
//        this.context = context;
//        this.objects = objects;
//        mList = (ArrayList) objects;
//        mSize = objects.size();
//    }
//
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
//        return "";
//    }
//
//    @Override
//    public int getCount() {
//        return objects.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        int size = 0;
//        for(int i=0; i<mList.size(); i++) {
//            final int sectionCount = mList.size();
//            if(size +sectionCount > position) {
//                int indexOfCurrentSection = position - size;
//                return objects.get(indexOfCurrentSection);
//            }
//            size += sectionCount;
//        }
//        return null;
//    }
//
//    @Override
//    public long getItemId(int id) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
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
//
////        holder.imageView.setImageUrl(objects.get(position).getImage(), HttpClient.getInstance().getImageLoader(context));
//        holder.textView.setText(objects.get(position).getTitle());
//
//        return convertView;
//    }
//
//    class Holder {
//        NetworkImageView imageView;
//        TextView textView;
//    }
//}
