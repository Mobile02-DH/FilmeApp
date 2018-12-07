package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.control.ConfiguracaoFirebase;
import com.digitalhouse.whatchapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastar = findViewById(R.id.btnCadastrar);

        botaoCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

            //validar se os campos foram preenchidos

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(( textoNome ));
                            usuario.setEmail(( textoEmail ));
                            usuario.setSenha(( textoSenha ));

                            cadastrarUsuario();

                        }else{
                            Toast.makeText(CadastroActivity.this, "Preencha sua senha!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()

        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(CadastroActivity.this, HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });

    }
    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Você esta Logado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Você não esta Logado", Toast.LENGTH_SHORT).show();
        }

    }
}

