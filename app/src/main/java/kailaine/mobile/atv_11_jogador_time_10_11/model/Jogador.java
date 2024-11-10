package kailaine.mobile.atv_11_jogador_time_10_11.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.time.LocalDate;

public class Jogador {
    private int id;
    private String nome;
    private LocalDate dataNasc;
    private float altura;
    private float peso;
    private Time time;

    public Jogador(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return String.valueOf(dataNasc);
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + time + " - " + altura+ " - " + peso + " - " + dataNasc;
    }
}
