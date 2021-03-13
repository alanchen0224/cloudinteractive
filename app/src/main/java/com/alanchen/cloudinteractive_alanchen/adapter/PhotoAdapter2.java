package com.alanchen.cloudinteractive_alanchen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;
import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.tempPathMap;

public class PhotoAdapter2 extends RecyclerView.Adapter<PhotoAdapter2.PhotoViewHolder>{

    private final ExecutorService mExecutorService;
    Context context;
    List<Photo> photoList;
    OnItemClick onItemClick;

    public PhotoAdapter2(Context context, List<Photo> photoList, OnItemClick onItemClick) {
        this.context = context;
        this.photoList = photoList;
        this.onItemClick = onItemClick;
        this.mExecutorService = Executors.newCachedThreadPool();
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
            this.mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    File file = new File(context.getCacheDir(), "/"+ tempPathMap.get(photo.id));
                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    holder.pImage.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.pImage.setImageBitmap(bitmap);
                        }
                    });
                }
            });
        }else{
            holder.pImage.setImageResource(android.R.color.transparent);
            Downloader.loadImage(photo.id, photo.thumbnailUrl, holder.loadImageCallBack);
        }

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
                        onItemClick.onClick(pImage, photoList.get(PhotoViewHolder.this.getLayoutPosition()));
                    }
                }
            });
        }

        public Downloader.LoadImageCallBack loadImageCallBack = new Downloader.LoadImageCallBack() {
            @Override
            public void onLoadedImage(int id, byte[] bytes) {
                if(PhotoViewHolder.this.getLayoutPosition() < 0){
                    return;
                }else if (photoList.get(PhotoViewHolder.this.getLayoutPosition()) == null) {
                    return;
                }else if ( photoList.get(PhotoViewHolder.this.getLayoutPosition()).id != id ){
                    return;
                }
                pImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                saveToCache(bytes, id);
            }
        };
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
