package com.alanchen.cloudinteractive_alanchen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanchen.cloudinteractive_alanchen.R;
import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.alanchen.cloudinteractive_alanchen.service.PhotoImgAPIService;
import com.alanchen.cloudinteractive_alanchen.utils.Downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;
import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.tempPathMap;

public class PhotoAdapter2 extends RecyclerView.Adapter<PhotoAdapter2.PhotoViewHolder>{

    Context context;
    List<Photo> photoList;
    OnItemClick onItemClick;

    public PhotoAdapter2(Context context, List<Photo> photoList, OnItemClick onItemClick) {
        this.context = context;
        this.photoList = photoList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public PhotoAdapter2.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoAdapter2.PhotoViewHolder holder, int position) {
        final Photo photo = photoList.get(position);

        if( Downloader.checkIfCacheImg(photo.id) ) {
            File file = new File(context.getCacheDir(), "/"+ tempPathMap.get(photo.id));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            holder.pImage.setImageBitmap(bitmap);
        }else
            loadImage(holder, photo);

        holder.pId.setText(String.valueOf(photo.getId()));
        holder.pTitle.setText(photo.getTitle());

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView pImage ;
        TextView pId;
        TextView pTitle;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.pImage);
            pId = itemView.findViewById(R.id.pId);
            pTitle = itemView.findViewById(R.id.pTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( onItemClick != null) {
                        onItemClick.onClick(pImage, photoList.get(PhotoViewHolder.this.getAdapterPosition()));
                    }
                }
            });
        }
    }

    void loadImage (final PhotoAdapter2.PhotoViewHolder holder, final Photo photo) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://via.placeholder.com/150/35cedf/").build();
        PhotoImgAPIService photoImgAPIService = retrofit.create(PhotoImgAPIService.class);
        Call<ResponseBody> call = photoImgAPIService.getPhotoImg(photo.thumbnailUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if( response.isSuccessful()) {
                    Log.d(TAG, "server contacted ");
                    try {
                        byte[] bytes = response.body().bytes();
                        holder.pImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        saveToCache(bytes, photo.id);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else Log.d(TAG, "server contact failed");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure "+t.getMessage());
            }
        });
    }

    void saveToCache(byte[] bytes, int id) {
        try {
            File file = File.createTempFile("IMG"+id+"_", ".png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            tempPathMap.put(id, file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClick{
        void onClick(ImageView imgView, Photo photo);
    }
}
