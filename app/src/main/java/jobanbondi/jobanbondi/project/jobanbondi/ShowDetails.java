package jobanbondi.jobanbondi.project.jobanbondi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import jobanbondi.jobanbondi.project.jobanbondi.util.LoadImageTask;
import jobanbondi.jobanbondi.project.jobanbondi.util.SingletonClass;

public class ShowDetails extends AppCompatActivity implements LoadImageTask.Listener{

    private ProgressDialog pDialog;
    TextView txtComplain;
    TextView txtDate;
    ImageView iamge;
    String url,complain,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading..");
        pDialog.show();

        txtComplain = (TextView) findViewById(R.id.txtShowComplain);
        txtDate = (TextView) findViewById(R.id.txtShowDate);
        iamge = (ImageView) findViewById(R.id.images);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        complain = intent.getStringExtra("complain");
        date = intent.getStringExtra("date");

        new LoadImageTask(this).execute(url);

    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        iamge.setImageBitmap(bitmap);
        txtComplain.setText(complain);
        txtDate.setText(date);
        hidePDialog();

    }

    @Override
    public void onError() {
        hidePDialog();
        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
}
