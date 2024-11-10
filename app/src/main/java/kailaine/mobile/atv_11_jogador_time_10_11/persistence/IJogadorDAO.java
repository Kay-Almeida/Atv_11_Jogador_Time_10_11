package kailaine.mobile.atv_11_jogador_time_10_11.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;

public interface IJogadorDAO {
    public JogadorDao open() throws SQLException;
    public void close();
}
