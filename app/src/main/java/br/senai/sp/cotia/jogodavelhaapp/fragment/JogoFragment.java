package br.senai.sp.cotia.jogodavelhaapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.jogodavelhaapp.R;
import br.senai.sp.cotia.jogodavelhaapp.databinding.FragmentJogoBinding;


public class JogoFragment extends Fragment {
    private FragmentJogoBinding binding;
    //vetro para agru
    private Button[] botoes;
    //variavel que represanta o tabuleiro
    private String[][]tabuleiro;
    //varivel para os simbolos
    private  String simbojoo1,simbojoo2,simbolo;
    //variavel rendom para sortear quem começa
    private Random random;
    private  int numjogadadas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentJogoBinding.inflate(inflater,container, false);
      botoes =  new  Button[9];
      botoes[0] = binding.bt00;
      botoes[1] = binding.bt01;
      botoes[2] = binding.bt02;
      botoes[3] = binding.bt10;
      botoes[4] = binding.bt11;
      botoes[5] = binding.bt12;
      botoes[6] = binding.bt20;
      botoes[7] = binding.bt21;
      botoes[8] = binding.bt22;
      for (Button bt: botoes){
          bt.setOnClickListener(listenerBotoes);
      }
      //instancia o tabuleiro
      tabuleiro = new String[3][3];
      //preencher o tabuleiro vazio
        for (String[] vetor:  tabuleiro){
            Arrays.fill(vetor,"");
        }
      //intansia
      random = new Random();
      simbojoo1 = "x";
      simbojoo2 = "0";
      sorteia();


              return binding.getRoot();
    }
    private  void  sorteia(){
        // caso rendo gere um valor v, jogador 1 começa
        if(random.nextBoolean()){
            simbolo = simbojoo1;

        }else {
            simbolo = simbojoo2;
        }
        atualizaVez();


    }
    private void  atualizaVez(){
        if(simbolo.equals(simbojoo1)){
            binding.linearJogo1.setBackgroundColor(getResources().getColor(R.color.teal_200));
            binding.linearJogo2.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            binding.linearJogo2.setBackgroundColor(getResources().getColor(R.color.selected_background));
            binding.linearJogo1.setBackgroundColor(getResources().getColor(R.color.white));

        }
    }
    private void restar(){
         for (String[] vetor:  tabuleiro){
            Arrays.fill(vetor,"");
        }

        for (Button bt: botoes){
            bt.setBackgroundColor(getResources().getColor(R.color.purple_500));
            bt.setText("");
            bt.setClickable(true);
        }
        // sorteia quem itra iniciar
         sorteia();
        //atualizar a vez no placar
        atualizaVez();
        numjogadadas = 0;


    }
    private boolean venceu(){
        for (int i = 0;i < 3; i++) {

            if (tabuleiro[i][0].equals(simbolo) && tabuleiro[i][1].equals(simbolo) && tabuleiro[i][2].equals(simbolo)) {

                return true;

            }
        }

            for (int i = 0;i < 3; i++) {
                if (tabuleiro[0][i].equals(simbolo) && tabuleiro[1][i].equals(simbolo) && tabuleiro[2][i].equals(simbolo)) {

                    return true;

                }
            }

            if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){

                return true;

            }
            if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)){

                return true;

            }


        return false;
    }



    private  View.OnClickListener listenerBotoes = btPress -> {
        numjogadadas++;
        //pega o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        String posico = nomeBotao.substring(nomeBotao.length() -2);
        // extrai a posição
        int linha = Character.getNumericValue(posico.charAt(0));
        int coluna =  Character.getNumericValue(posico.charAt(1));
        //
        tabuleiro[linha][coluna] = simbolo;
        // faz casting de view para Button
        Button button = (Button) btPress;
        button.setText(simbolo);
        button.setClickable(false);
        button.setBackgroundColor(Color.WHITE);
        if(numjogadadas >= 5 && venceu()){
            Toast.makeText(getContext(),R.string.venceu, Toast.LENGTH_LONG).show();
            restar();
        }else if (numjogadadas == 9){
            Toast.makeText(getContext(),R.string.deuvelha, Toast.LENGTH_LONG).show();
            restar();

        }else {
            simbolo  = simbolo.equals(simbojoo1)?simbojoo2:simbojoo1;
            atualizaVez();
        }
        //inverter a vesz





    };
}