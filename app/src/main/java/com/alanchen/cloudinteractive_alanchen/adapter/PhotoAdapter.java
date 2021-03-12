//package com.alanchen.cloudinteractive_alanchen.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import com.alanchen.cloudinteractive_alanchen.databinding.PhotoBinding;
//import com.alanchen.cloudinteractive_alanchen.viewmodel.PhotoViewModel;
//
//import java.util.List;
//
//public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyAdapter> {
//
//    List<PhotoViewModel> data;
//    Context context;
//    LayoutInflater inflater;
//
//    public PhotoAdapter(List<PhotoViewModel> data, Context context){
//        this.data = data;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if(inflater == null)
//            inflater = LayoutInflater.from(parent.getContext());
//
//        PhotoBinding photoBinding = PhotoBinding.inflate(inflater, parent, false);
//        return new MyAdapter(photoBinding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
//        holder.bind((data.get(position)));
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    public class MyAdapter extends RecyclerView.ViewHolder {
//
//        PhotoBinding photoBinding;
//
//        public MyAdapter(PhotoBinding photoBinding) {
//            super(photoBinding.getRoot());
//            this.photoBinding = photoBinding;
//        }
//
//        public void bind(PhotoViewModel photoViewModel) {
//            this.photoBinding.setViewModel(photoViewModel);
//        }
//
//        public PhotoBinding getPhotoBinding() {
//            return photoBinding;
//        }
//    }
//}
