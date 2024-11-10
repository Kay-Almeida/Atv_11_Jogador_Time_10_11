package kailaine.mobile.atv_11_jogador_time_10_11.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.atv_11_jogador_time_10_11.model.Jogador;
import kailaine.mobile.atv_11_jogador_time_10_11.model.Time;

public class JogadorDao implements IJogadorDAO, ICRUDDao<Jogador>{
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;


    public JogadorDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        database.insert("jogador", null, contentValues);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        int ret = database.update("jogador", contentValues, "id = "+ jogador.getId(), null);
        return ret;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        database.delete("jogador", "id = "+ jogador.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String sql = "SELECT j.id, j.nome, j.data_nasc, j.altura, j.peso, j.time, t.nome AS nome_time, t.cidade AS cidade_time " +
                "FROM jogador j " +
                "INNER JOIN time t ON j.time = t.codigo";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            Time time = new Time();
            time.setNome(cursor.getString(cursor.getColumnIndex("nome_time")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade_time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nasc"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    public List<Jogador> findAll() throws SQLException {
       List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT j.id, j.nome, j.data_nasc, j.altura, j.peso, j.time, t.nome AS nome_time, t.cidade AS cidade_time " +
                "FROM jogador j " +
                "INNER JOIN time t ON j.time = t.codigo";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Jogador jogador = new Jogador();
            Time time = new Time();
 //           time.setCodigo(cursor.getInt(cursor.getColumnIndex("")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome_time")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade_time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nasc"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao  =new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    private static ContentValues getContentValues(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("data_nasc", jogador.getDataNasc());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("time", jogador.getTime().getCodigo());
        return contentValues;
    }
}
