package jobanbondi.jobanbondi.project.jobanbondi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jobanbondi.jobanbondi.project.jobanbondi.adapter.Customadapter;
import jobanbondi.jobanbondi.project.jobanbondi.util.ModelClass;
import jobanbondi.jobanbondi.project.jobanbondi.util.SingletonClass;

public class GetAllDataFromServerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static final String URL = "http://www.dogmatt.com/Project21/json_image.php";
    private List<ModelClass> modelClasses = new ArrayList<ModelClass>();
    private ListView listView;
    private Customadapter adapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_data_from_server);

        listView = (ListView) findViewById(R.id.list_of_files);
        adapter = new Customadapter(this,modelClasses);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        pDialog = new ProgressDialog(this);

        pDialog.setMessage("Loading..");
        pDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hidePDialog();
                for (int i = 0; i<=response.length(); i++){

                    try {
                        JSONObject object = response.getJSONObject(i);
                        ModelClass modelClass = new ModelClass();
                        modelClass.setFiles(object.getString("files"));
                        modelClass.setDateTime(object.getString("Datetime"));
                        modelClass.setComplain(object.getString("complain"));
                        modelClass.setIsImage(object.getString("isImage"));

                        modelClasses.add(modelClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });


        SingletonClass.getInstance(this).addToRequestQueue(request);
       // AppController.getInstance().addToRequestQueue(request);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ModelClass name = (ModelClass) adapter.getItem(i);


        if (name.getIsImage().contains("true")){

            Intent intent = new Intent(GetAllDataFromServerActivity.this, ShowDetails.class);
            intent.putExtra("url", name.getFiles());
            intent.putExtra("complain", name.getComplain());
            intent.putExtra("date", name.getDateTime());
            startActivity(intent);

        }else{

            Intent intent = new Intent(GetAllDataFromServerActivity.this, ShowVideoComplainActivity.class);
            intent.putExtra("url", name.getFiles());
            intent.putExtra("complain", name.getComplain());
            intent.putExtra("date", name.getDateTime());
            startActivity(intent);
        }
    }
}
