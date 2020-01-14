package uz.nisd.firstproject.uploadimage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import uz.nisd.firstproject.uploadimage.Model.FileInfoDetails;
import uz.nisd.firstproject.uploadimage.R;


public class GetAdapter extends RecyclerView.Adapter<GetAdapter.ViewHolder> {

    private Context context;
    private List<FileInfoDetails> fileList;


    public GetAdapter(Context context, List<FileInfoDetails> fileList) {
        this.context = context;
        this.fileList = fileList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FileInfoDetails file = fileList.get(position);
        holder.titled.setText(file.getTitle());

//        InputStream in;
////        Bitmap bmp;
//        int responseCode = -1;
//        try {
//            URL url = new URL(file.getUrl());
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setDoInput(true);
//            con.connect();
//            responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//                in = con.getInputStream();
////                bmp = BitmapFactory.decodeStream(in);
//                in.close();
//
////                Picasso.with(context)
////                .load(file.getUrl())
////                .placeholder(R.mipmap.ic_launcher)
////                .error(R.drawable.ic_cloud_download_black_24dp)
////                .resize(250, 200)
////                .rotate(90)
////                .into(holder.imageview);
//
//                Glide.with(context)
//                .load(file.getUrl())
//                .asBitmap()
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(holder.imageview);
////                holder.imageview.setImageBitmap(bmp);
//                System.out.println(file.getUrl());
//            }
//        }
//        catch (Exception ex) {
//            Log.e("Exception", ex.toString());
//        }

        Picasso.with(context)
                .load(file.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_cloud_download_black_24dp)
                .resize(250, 200)
                .into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageview;
        public TextView titled;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titled = (TextView) itemView.findViewById(R.id.titled);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }

}