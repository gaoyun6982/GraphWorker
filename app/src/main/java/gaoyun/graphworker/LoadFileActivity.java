package gaoyun.graphworker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoadFileActivity extends AppCompatActivity {

    EditText filenameEdit;
    static RadioGroup rg;

    FragmentManager fragmentManager;

    GraphLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file);

        rg = (RadioGroup)findViewById(R.id.radioGroup);
    }

    public void loadGraphFile(View view) {

        filenameEdit = (EditText)findViewById(R.id.editFilename);

        loader = new GraphLoader(filenameEdit.getText().toString());

        checkGraphTypeAndLoad(view);



    }

    private void checkGraphTypeAndLoad(View view){

        if(rg.getCheckedRadioButtonId()==R.id.radioList){
            if (loader.checkGraph() == GraphLoader.LIST_TYPE){

                if (loader.loadGraphListed() == GraphLoader.SUCCESS_LOADING) {

                    Toast.makeText(this, "Graph load success", Toast.LENGTH_SHORT).show();
                    goToGraphActivity();

                } else Snackbar.make(view, "Graph load failed", Snackbar.LENGTH_SHORT).show();

            } else Snackbar.make(view, "Graph load failed", Snackbar.LENGTH_SHORT).show();
        } else if(rg.getCheckedRadioButtonId()==R.id.radioMatrix){
            if (loader.checkGraph() == GraphLoader.MATRIX_TYPE){

                GraphLoader.graphType = GraphLoader.MATRIX_TYPE;
                if(loader.loadGraphMatriced()==GraphLoader.SUCCESS_LOADING){

                    Toast.makeText(this, "Graph load success", Toast.LENGTH_SHORT).show();
                    goToGraphActivity();

                } else Snackbar.make(view, "Graph load failed", Snackbar.LENGTH_SHORT).show();

            } else Snackbar.make(view, "Graph load failed", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void goToGraphActivity(){

        Intent graphIntent = new Intent(LoadFileActivity.this,GraphActivity.class);
        startActivity(graphIntent);

    }
}
