package kailaine.mobile.atv_11_jogador_time_10_11.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import kailaine.mobile.atv_11_jogador_time_10_11.model.Time;
import kailaine.mobile.atv_11_jogador_time_10_11.persistence.TimeDao;

public class TimeController implements IController<Time>{

    private final TimeDao tDao;

    public TimeController(TimeDao tDao){
        this.tDao = tDao;
    }

    @Override
    public void inserir(Time time) throws SQLException {
        if(tDao.open() == null){
            tDao.open();
        }
        tDao.insert(time);
        tDao.close();

    }

    @Override
    public void modificar(Time time) throws SQLException {
        if(tDao.open() == null){
            tDao.open();
        }
        tDao.update(time);
        tDao.close();
    }

    @Override
    public void deletar(Time time) throws SQLException {
        if(tDao.open() == null){
            tDao.open();
        }
        tDao.delete(time);
        tDao.close();
    }

    @Override
    public Time buscar(Time time) throws SQLException {
        if(tDao.open() == null){
            tDao.open();
        }
        return tDao.findOne(time);
    }

    @Override
    public List<Time> listar() throws SQLException {
        if(tDao.open() == null){
            tDao.open();
        }
        return tDao.findAll();
    }
}
