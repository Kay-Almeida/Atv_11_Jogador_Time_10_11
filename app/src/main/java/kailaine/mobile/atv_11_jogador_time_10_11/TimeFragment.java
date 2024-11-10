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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.atv_11_jogador_time_10_11.controller.TimeController;
import kailaine.mobile.atv_11_jogador_time_10_11.model.Time;
import kailaine.mobile.atv_11_jogador_time_10_11.persistence.TimeDao;

public class TimeFragment extends Fragment {

    private View view;
    private EditText etCodigoTime, etNomeTime, etCidadeTime;
    private TextView tvTimeListar;
    private Button btnPesquisarTime, btnInserirTime, btnModificarTime, btnDeletarTime, btnListarTime ;

    private TimeController tCont;

    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        tvTimeListar = view.findViewById(R.id.tvTimeListar);
        btnModificarTime = view.findViewById(R.id.btnModificarTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnDeletarTime = view.findViewById(R.id.btnDeletarTime);
        btnPesquisarTime = view.findViewById(R.id.btnPesquisarTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);
        tvTimeListar.setMovementMethod(new ScrollingMovementMethod());

        tCont = new TimeController(new TimeDao(view.getContext()));

        btnInserirTime.setOnClickListener(op -> acaoInserirTime());
        btnDeletarTime.setOnClickListener(op -> acaoDeletarTime());
        btnListarTime.setOnClickListener(op -> acaoListarTime());
        btnModificarTime.setOnClickListener(op -> acaoModificarTime());
        btnPesquisarTime.setOnClickListener(op -> acaoPesquisarTime());

        return view;
    }

    private void acaoPesquisarTime() {
        Time time = montaTime();
        try{
            time = tCont.buscar(time);
            if(time.getNome() != null){
                preencheCampos(time);
            }else{
                Toast.makeText(view.getContext(), "Time Não Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        }catch(SQLException e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();


    }

    private void acaoModificarTime() {
        Time time = montaTime();
        try{
            tCont.modificar(time);
            Toast.makeText(view.getContext(), "Time Atualizado com Sucesso", Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();

    }

    private void acaoListarTime() {
        try {
            List<Time> times = tCont.listar();
            StringBuffer buffer = new StringBuffer();
            for(Time t : times){
                buffer.append(t.toString() + "\n");
            }
            tvTimeListar.setText(buffer.toString());
        }catch(SQLException e){

        }
    }

    private void acaoDeletarTime() {
        Time time = montaTime();
        try{
            tCont.deletar(time);
            Toast.makeText(view.getContext(), "Time inserido com Sucesso", Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();

    }

    private void acaoInserirTime() {
        Time time = montaTime();
        try{
            tCont.inserir(time);
            Toast.makeText(view.getContext(), "Time inserido com Sucesso", Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();

    }

    private Time montaTime(){
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());

        return time;
    }

    private void preencheCampos(Time time){
        etCodigoTime.setText(String.valueOf(time.getCodigo()));
        etNomeTime.setText(time.getNome());
        etCidadeTime.setText(time.getCidade());
    }

    private void limpaCampos(){
        etCodigoTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }


}