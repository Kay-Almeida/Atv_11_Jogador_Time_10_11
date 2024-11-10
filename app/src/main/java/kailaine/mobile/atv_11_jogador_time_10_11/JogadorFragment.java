package kailaine.mobile.atv_11_jogador_time_10_11;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import kailaine.mobile.atv_11_jogador_time_10_11.controller.JogadorController;
import kailaine.mobile.atv_11_jogador_time_10_11.controller.TimeController;
import kailaine.mobile.atv_11_jogador_time_10_11.model.Jogador;
import kailaine.mobile.atv_11_jogador_time_10_11.model.Time;
import kailaine.mobile.atv_11_jogador_time_10_11.persistence.JogadorDao;
import kailaine.mobile.atv_11_jogador_time_10_11.persistence.TimeDao;

public class JogadorFragment extends Fragment {

    private  View view;
    private EditText etIdJogador, etNomeJogador, etDataJogador, etAlturaJogador, etPesoJogador;
    private TextView tvJogadorListar;
    private Button btnPesquisarJogador, btnInserirJogador, btnDeletarJogador, btnModificarJogador, btnListarJogador;
    private Spinner spTimeJogador;

    private TimeController tCont;
    
    private List<Time> times;

    private JogadorController jCont;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        etIdJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataJogador = view.findViewById(R.id.etDataJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        tvJogadorListar = view.findViewById(R.id.tvJogadorListar);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnDeletarJogador = view.findViewById(R.id.btnDeletarJogador);
        btnModificarJogador = view.findViewById(R.id.btnModificarJogador);
        btnPesquisarJogador = view.findViewById(R.id.btnPesquisarJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);
        spTimeJogador = view.findViewById(R.id.spTimeJogador);

        tvJogadorListar.setMovementMethod(new ScrollingMovementMethod());

        jCont = new JogadorController(new JogadorDao(view.getContext()));
        tCont = new TimeController(new TimeDao(view.getContext()));

        preencheSpinner();

        btnInserirJogador.setOnClickListener(op -> acaoInserirJogador());
        btnDeletarJogador.setOnClickListener(op -> acaoDeletarJogador());
        btnModificarJogador.setOnClickListener(op -> acaoModificarJogador());
        btnPesquisarJogador.setOnClickListener(op -> acaoPesquisarJogador());
        btnListarJogador.setOnClickListener(op -> acaoListarJogador());

        return view;
    }

    private void preencheSpinner() {
         Time t0 = new Time();
         t0.setCodigo(0);
         t0.setNome("Selecione um Time");
         t0.setCidade("");

         try{
             times = tCont.listar();
             times.add(0, t0);

             ArrayAdapter<Time> ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, times);
             spTimeJogador.setAdapter(ad);
         } catch (SQLException e) {
             Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
         }
    }

    private void acaoDeletarJogador() {
        Jogador jogador = montaJogador();
            try {
                jCont.deletar(jogador);
                Toast.makeText(view.getContext(), "Jogador Deletado com Sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();

    }

    private void acaoInserirJogador() {
        int spTime = spTimeJogador.getSelectedItemPosition();
        if(spTime > 0){
            Jogador jogador = montaJogador();
            try {
                jCont.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador Inserido com Sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        }else{
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }

    }

    private void acaoModificarJogador() {
        int spTime = spTimeJogador.getSelectedItemPosition();
        if(spTime > 0){
            Jogador jogador = montaJogador();
            try {
                jCont.modificar(jogador);
                Toast.makeText(view.getContext(), "Jogador Alterado com Sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        }else{
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }

    }

    private void acaoPesquisarJogador() {
        Jogador jogador = montaJogador();
        try {
            times = tCont.listar();
            jogador = jCont.buscar(jogador);
            if(jogador.getNome() != null){
                preencheCampos(jogador);
            }else{
                Toast.makeText(view.getContext(), "Jogador Não Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void acaoListarJogador() {
        try {
            List<Jogador> jogadores = jCont.listar();
            StringBuffer buffer = new StringBuffer();
            for(Jogador j : jogadores){
                buffer.append(j.toString() + "\n");
            }
            tvJogadorListar.setText(buffer.toString());
        }catch(SQLException e){

        }
    }


    private Jogador montaJogador(){
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));
        jogador.setNome(etNomeJogador.getText().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        jogador.setDataNasc(LocalDate.parse(etDataJogador.getText().toString(), formatter));
//        jogador.setDataNasc(LocalDate.parse(etDataJogador.getText()));
        jogador.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
        jogador.setTime((Time) spTimeJogador.getSelectedItem());

        return jogador;
    }

   private void preencheCampos(Jogador jogador){
        etIdJogador.setText(String.valueOf(jogador.getId()));
        etNomeJogador.setText(jogador.getNome());
        etDataJogador.setText(jogador.getDataNasc());
        etAlturaJogador.setText(String.valueOf(jogador.getAltura()));
        etPesoJogador.setText(String.valueOf(jogador.getPeso()));
        int cont = 1;
        for(Time time : times){
            if(time.getCodigo() == jogador.getTime().getCodigo()){
                spTimeJogador.setSelection(cont);
            }else{
                cont++;
            }
        }
        if(cont > times.size()){
            spTimeJogador.setSelection(0);
        }

    }

    private void limpaCampos(){
        etIdJogador.setText("");
        etNomeJogador.setText("");
        etDataJogador.setText("");
        etAlturaJogador.setText("");
        etPesoJogador.setText("");
      spTimeJogador.setSelection(0);
    }
}