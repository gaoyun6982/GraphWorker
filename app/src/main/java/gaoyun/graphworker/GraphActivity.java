package gaoyun.graphworker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GraphActivity extends AppCompatActivity {

    TextView graphMatrix;
    TextView graphCharacteristic;

    EditText fstEdgeToCheckAdjacency;
    EditText sndEdgeToCheckAdjacency;

    EditText fstEdgeToChangeWeight;
    EditText sndEdgeToChangeWeight;
    EditText newWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        setTitle("Graph Info");

        graphMatrix = (TextView) findViewById(R.id.graphMatrixText);
        graphCharacteristic = (TextView) findViewById(R.id.graphCharacteristicText);

        fstEdgeToCheckAdjacency = (EditText) findViewById(R.id.fstEdgeToCheckAdjacency);
        sndEdgeToCheckAdjacency = (EditText) findViewById(R.id.sndEdgeToCheckAdjacency);

        fstEdgeToChangeWeight = (EditText) findViewById(R.id.fstEdgeToChangeWeight);
        sndEdgeToChangeWeight = (EditText) findViewById(R.id.sndEdgeToChangeWeight);
        newWeight = (EditText) findViewById(R.id.newWeight);

        graphMatrix.setText(setMatrixText());
        graphCharacteristic.setText(setGraphChracteristic());

    }

    private String setMatrixText(){

        String graph = "";

        for (int i = 0; i < GraphLoader.graphDimention; i++) {

            for (int j = 0; j < Integer.valueOf(GraphLoader.graphDimention); j++)
                graph += GraphLoader.matrixArray[i][j]+" ";
            graph+="\n";

        }

        return graph;

    }

    private String setGraphChracteristic(){

        String characteristic = "";
        int edges = 0;

        for (int i = 0; i < GraphLoader.graphDimention; i++) {

            for (int j = 0; j < Integer.valueOf(GraphLoader.fstLine); j++)
                if(GraphLoader.matrixArray[i][j] > 0) edges++;

        }

        characteristic += "Number of vertices: " + GraphLoader.graphDimention + "\n";
        characteristic += "Number of edges: " + edges + "\n(a->b & b->a edges are different)\n";

        return characteristic;

    }

    public void checkAdjacency(View view) {

        try {

            int fstEdge = Integer.valueOf(fstEdgeToCheckAdjacency.getText().toString());
            int sndEdge = Integer.valueOf(sndEdgeToCheckAdjacency.getText().toString());

            if(GraphLoader.matrixArray[fstEdge-1][sndEdge-1] > 0){

                fstEdgeToCheckAdjacency.setTextColor(getResources().getColor(R.color.trueGreen));
                sndEdgeToCheckAdjacency.setTextColor(getResources().getColor(R.color.trueGreen));

                Snackbar.make(view, "Edges are adjacent", Snackbar.LENGTH_SHORT).show();

            } else{

                fstEdgeToCheckAdjacency.setTextColor(getResources().getColor(R.color.falseRed));
                sndEdgeToCheckAdjacency.setTextColor(getResources().getColor(R.color.falseRed));

                Snackbar.make(view, "Edges are not adjacent", Snackbar.LENGTH_SHORT).show();

            }

        } catch (Exception e){
            Snackbar.make(view, "Wrong edges!", Snackbar.LENGTH_SHORT).show();
        }

    }

    public void changeWeight(View view) {

        try {

            int fstEdge = Integer.valueOf(fstEdgeToChangeWeight.getText().toString());
            int sndEdge = Integer.valueOf(sndEdgeToChangeWeight.getText().toString());
            int newWeightNum = Integer.valueOf(newWeight.getText().toString());

            if(GraphLoader.matrixArray[fstEdge-1][sndEdge-1] > 0) {

                GraphLoader.matrixArray[fstEdge - 1][sndEdge - 1] = newWeightNum;
                graphMatrix.setText(setMatrixText());

                Snackbar.make(view, "Weight change successfully", Snackbar.LENGTH_SHORT).show();

                fstEdgeToChangeWeight.setText("");
                sndEdgeToChangeWeight.setText("");

            } else Snackbar.make(view, "Null edge", Snackbar.LENGTH_SHORT).show();

        } catch (Exception e){
            Snackbar.make(view, "Wrong edges or weight!", Snackbar.LENGTH_SHORT).show();
        }

    }

    public void createVertice(View view) {

        AsyncTask ast = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                int[][] newMatrix = new int[GraphLoader.graphDimention+1][GraphLoader.graphDimention+1];

                for (int i = 0; i < GraphLoader.graphDimention; i++) {

                    for (int j = 0; j < Integer.valueOf(GraphLoader.fstLine); j++)
                        newMatrix[i][j] = GraphLoader.matrixArray[i][j];

                }

                GraphLoader.newMatrix(newMatrix);
                GraphLoader.graphDimention++;

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                runOnUiThread(refreshUI);
            }
        };

        ast.execute();


    }

    Runnable refreshUI = new Runnable() {
        @Override
        public void run() {
            graphMatrix.setText(setMatrixText());
            graphCharacteristic.setText(setGraphChracteristic());
        }
    };
}
