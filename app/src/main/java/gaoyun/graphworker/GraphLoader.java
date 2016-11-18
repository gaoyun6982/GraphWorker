package gaoyun.graphworker;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Artem on 15.10.16.
 */

public class GraphLoader {

    public static final int SUCCESS_LOADING = 0;
    public static final int FAILED_LOADING = 1;
    public static final int UNKNOWN_GRAPH_TYPE = 10;
    public static final int SUCCESS_GRAPH_CHECK = 11;

    public static final int MATRIX_TYPE = 20;
    public static final int LIST_TYPE = 21;

    public static int graphType = 0;

    public static String fstLine;

    public static int[][] linksArray;
    public static int[] weightArray;

    public static int[][] matrixArray;
    public static int graphDimention;

    File sdPath;
    File sdFile;

    String fileName;

    int pos = 0;
    char[] file = new char[1000];

    //Access to graph File
    public GraphLoader(String filename) {

        //Setting name
        this.fileName = filename + ".txt";

        //Getting path
        sdPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        sdPath = new File(sdPath.getPath());

        //Access to file
        sdFile = new File(sdPath, fileName);

    }

    //Checking graph type.
    //If graph has 3x3 dimention then it can be matrix and list already.
    //So, LoadFileActivity will show UnknownGraphDialog.
    public int checkGraph(){

        try {

            BufferedReader graphBufferedReader = new BufferedReader(new FileReader(sdFile));
            fstLine = graphBufferedReader.readLine();

            if(fstLine.startsWith("m")) return MATRIX_TYPE;
            else if(fstLine.startsWith("l")) return LIST_TYPE;
            else return FAILED_LOADING;

        } catch (FileNotFoundException fnfe){

            fnfe.printStackTrace();
            return FAILED_LOADING;

        } catch (IOException ioe){

            ioe.printStackTrace();
            return FAILED_LOADING;

        }

    }

    //Load list graph
    public int loadGraphListed(){

        try{

            BufferedReader graphBufferedReader = new BufferedReader(new FileReader(sdFile));
            fstLine = graphBufferedReader.readLine();
            fstLine = fstLine.replaceFirst("l","");
            int maxVert = 0;
            //Create links and weight arrays
            linksArray = new int[2][Integer.valueOf(fstLine)];
            weightArray = new int[Integer.valueOf(fstLine)];

            for(int i=0; i<Integer.valueOf(fstLine);i++){

                String str1 = graphBufferedReader.readLine();
                file = str1.toCharArray();

                String fstPoint = "";
                String sndPoint = "";
                String weight = "";
                int linkPosition=0;

                //Read fst link position
                for(int j=0; j<file.length; j++){

                    if(file[j]!=' ') fstPoint += file[j];
                    else{

                        linkPosition=j;
                        break;

                    }

                }

                linksArray[0][pos] = Integer.valueOf(fstPoint);
                if (maxVert < linksArray[0][pos]) maxVert = linksArray[0][pos];

                //Read snd link position
                for(int j=linkPosition+1; j<file.length; j++){

                    if(file[j]!=' '){

                        sndPoint += file[j];

                    } else{

                        linkPosition=j;
                        break;

                    }

                }

                linksArray[1][pos] = Integer.valueOf(sndPoint);
                if (maxVert < linksArray[1][pos]) maxVert = linksArray[1][pos];

                //Read weight
                for(int j=linkPosition+1; j<file.length; j++){

                    if(file[j]!=' '){

                        weight += file[j];

                    } else{

                        break;

                    }

                }

                weightArray[pos] = Integer.valueOf(weight);

                pos++;

            }

            matrixArray = new int[maxVert][maxVert];
            graphDimention = maxVert;

            for(int i=0; i<Integer.valueOf(fstLine); i++){

                matrixArray[linksArray[0][i]-1][linksArray[1][i]-1] = weightArray[i];

            }

            return SUCCESS_LOADING;

        } catch (FileNotFoundException fnfe){

            fnfe.printStackTrace();
            return FAILED_LOADING;

        } catch (IOException ioe){

            ioe.printStackTrace();
            return FAILED_LOADING;

        } catch (ArrayIndexOutOfBoundsException aioofbe){

            aioofbe.printStackTrace();
            return FAILED_LOADING;

        } catch (Exception e){

            e.printStackTrace();
            return FAILED_LOADING;

        }

    }

    //Load matrix graph
    public int loadGraphMatriced(){

        try{

            BufferedReader graphBufferedReader = new BufferedReader(new FileReader(sdFile));
            fstLine = graphBufferedReader.readLine();
            fstLine = fstLine.replaceFirst("m","");
            //Create matrix
            matrixArray = new int[Integer.valueOf(fstLine)][Integer.valueOf(fstLine)];
            graphDimention = Integer.valueOf(fstLine);

            for(int i = 0; i<Integer.valueOf(fstLine); i++) {

                String fstLine = graphBufferedReader.readLine();
                fstLine += " ";
                file = fstLine.toCharArray();

                int matrixPosition = 0;
                String matrixNum = "";

                for (int j = 0; j < Integer.valueOf(GraphLoader.fstLine)*2; j++) {

                    //Read matrix
                    if(file[j]!=' ') matrixNum+=file[j];
                    else{

                        matrixArray[i][matrixPosition] = Integer.valueOf(matrixNum);
                        matrixPosition++;
                        matrixNum="";

                    }

                }

            }

            return SUCCESS_LOADING;

        } catch (FileNotFoundException fnfe){

            fnfe.printStackTrace();
            return FAILED_LOADING;

        } catch (IOException ioe){

            ioe.printStackTrace();
            return FAILED_LOADING;

        } catch (ArrayIndexOutOfBoundsException aioobe){

            aioobe.printStackTrace();
            return FAILED_LOADING;

        } catch (Exception e){

            e.printStackTrace();
            return FAILED_LOADING;

        }

    }

    public static void newMatrix(int[][] newMatrix){

        matrixArray = newMatrix;

    }

}
