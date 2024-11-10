package kailaine.mobile.atv_11_jogador_time_10_11.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "SPORTCLUB.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_JOGADOR = "CREATE TABLE jogador ( "+
                                                        "id INT PRIMARY KEY," +
                                                        "nome VARCHAR(255) NOT NULL," +
                                                        "data_nasc VARCHAR(10),"+
                                                        "altura DECIMAL(4,2),"+
                                                        "peso DECIMAL(4,2),"+
                                                        "time INT," +
                                                        "FOREIGN KEY (time) REFERENCES time(codigo))";


    private static final String CREATE_TABLE_TIME = "CREATE TABLE time ( "+
                                                    "codigo INT PRIMARY KEY," +
                                                    "nome VARCHAR(255) NOT NULL," +
                                                    "cidade VARCHAR(255));";

    public GenericDao(Context context){
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TIME);
        sqLiteDatabase.execSQL(CREATE_TABLE_JOGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if(novaVersao>antigaVersao){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS time");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS jogador");
            onCreate(sqLiteDatabase);

        }
    }
}
