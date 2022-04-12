package br.senai.sp.cotia.jogodavelhaapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.jogodavelhaapp.R;
import br.senai.sp.cotia.jogodavelhaapp.databinding.FragmentJogoBinding;
import br.senai.sp.cotia.jogodavelhaapp.util.PrefsUtil;


public class JogoFragment extends Fragment {
    private FragmentJogoBinding binding;
    //vetro para agru
    private Button[] botoes;
    //variavel que represanta o tabuleiro
    private String[][] tabuleiro;
    //varivel para os simbolos
    private String simbojoo1, simbojoo2, simbolo;
    //variavel rendom para sortear quem começa
    private Random random;
    private int numjogadadas = 0;
    private int placarjog1 = 0, placarjog2 = 0, palcarVelha = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        binding = FragmentJogoBinding.inflate(inflater, container, false);
        botoes = new Button[9];
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;
        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }
        //instancia o tabuleiro
        tabuleiro = new String[3][3];
        //preencher o tabuleiro vazio
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }
        //intansia
        random = new Random();
        simbojoo1 = PrefsUtil.getSimbolojogo1(getContext());
        simbojoo2 = PrefsUtil.getSimbolojogo2(getContext());

        binding.tvJogador1.setText(getResources().getString(R.string.jogador1, simbojoo1));
        binding.tvJogador2.setText(getResources().getString(R.string.jogador2, simbojoo2));
        sorteia();


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    private void sorteia() {
        // caso rendo gere um valor v, jogador 1 começa
        if (random.nextBoolean()) {
            simbolo = simbojoo1;

        } else {
            simbolo = simbojoo2;
        }
        atualizaVez();


    }

    private void atualizaVez() {
        if (simbolo.equals(simbojoo1)) {
            binding.linearJogo1.setBackgroundColor(getResources().getColor(R.color.teal_200));
            binding.linearJogo2.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            binding.linearJogo2.setBackgroundColor(getResources().getColor(R.color.selected_background));
            binding.linearJogo1.setBackgroundColor(getResources().getColor(R.color.white));

        }
    }

    private void restar() {
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        for (Button bt : botoes) {
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

    private void atualizarPlacar() {
        binding.tvpalcarjog1.setText(placarjog1 + "");
        binding.tvpalcarjog2.setText(placarjog2 + "");
        binding.tvpalcarVelha.setText(palcarVelha + "");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_resetar:
                placarjog1 = 0;
                placarjog2 = 0;
                palcarVelha = 0;
                restar();
                atualizarPlacar();
                break;
            case R.id.menu_preferences:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
            case R.id.menu_inicio:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_inicioFragment);
                break;

        }
        return true;
    }

    private boolean venceu() {
        for (int i = 0; i < 3; i++) {

            if (tabuleiro[i][0].equals(simbolo) && tabuleiro[i][1].equals(simbolo) && tabuleiro[i][2].equals(simbolo)) {

                return true;

            }
        }

        for (int i = 0; i < 3; i++) {
            if (tabuleiro[0][i].equals(simbolo) && tabuleiro[1][i].equals(simbolo) && tabuleiro[2][i].equals(simbolo)) {

                return true;

            }
        }

        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {

            return true;

        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {

            return true;

        }


        return false;
    }


    private View.OnClickListener listenerBotoes = btPress -> {
        numjogadadas++;
        //pega o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        String posico = nomeBotao.substring(nomeBotao.length() - 2);
        // extrai a posição
        int linha = Character.getNumericValue(posico.charAt(0));
        int coluna = Character.getNumericValue(posico.charAt(1));
        //
        tabuleiro[linha][coluna] = simbolo;
        // faz casting de view para Button
        Button button = (Button) btPress;
        button.setText(simbolo);
        button.setClickable(false);
        button.setBackgroundColor(Color.WHITE);
        if (numjogadadas >= 5 && venceu()) {
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();

            if (simbolo.equals(simbojoo1)) {
                placarjog1++;
            } else {
                placarjog2++;

            }
            atualizarPlacar();
            restar();

        } else if (numjogadadas == 9) {
            Toast.makeText(getContext(), R.string.deuvelha, Toast.LENGTH_LONG).show();
            palcarVelha++;
            atualizarPlacar();
            restar();

        } else {
            simbolo = simbolo.equals(simbojoo1) ? simbojoo2 : simbojoo1;
            atualizaVez();
        }
        //inverter a vesz


    };
}