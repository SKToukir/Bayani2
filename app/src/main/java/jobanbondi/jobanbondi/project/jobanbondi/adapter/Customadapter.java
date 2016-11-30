package jobanbondi.jobanbondi.project.jobanbondi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.util.List;
import jobanbondi.jobanbondi.project.jobanbondi.R;
import jobanbondi.jobanbondi.project.jobanbondi.util.AppController;
import jobanbondi.jobanbondi.project.jobanbondi.util.ModelClass;

public class Customadapter extends BaseAdapter{


    Context context;
    LayoutInflater inflater;
    List<ModelClass> modelClasses;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public Customadapter(Context context, List<ModelClass> modelClasses){
        this.context = context;
        this.modelClasses = modelClasses;
    }

    @Override
    public int getCount() {
        return modelClasses.size();
    }

    @Override
    public Object getItem(int location) {
        return modelClasses.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.files_row,null);
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) view
                .findViewById(R.id.imageView);
        ImageView imageView = (ImageView) view.findViewById(R.id.VideoView);
        TextView txtComplain = (TextView) view.findViewById(R.id.txtComplain);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDateTime);

         ModelClass model = modelClasses.get(position);

        txtComplain.setText(model.getComplain());
        txtDate.setText(model.getDateTime());
       // thumbNail.setDefaultImageResId(R.drawable.video);
        thumbNail.setImageUrl(model.getFiles(), imageLoader);





//        if (model.getIsImage().contains("true")) {
//            //for image
//
//            txtComplain.setText(model.getComplain());
//            txtDate.setText(model.getDateTime());
//            thumbNail.setImageUrl(model.getFiles(), imageLoader);
//
//        }else {
//            //for video
//            //thumbNail.setVisibility(View.GONE);
//            //imageView.setVisibility(View.VISIBLE);
//            //thumbNail.setImageUrl(String.valueOf(R.drawable.video), imageLoader);
//            txtComplain.setText(model.getComplain());
//            txtDate.setText(model.getDateTime());
//
//        }

        return view;
    }
}