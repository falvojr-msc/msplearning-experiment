package br.com.cast.android.aula1;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * {@link Activity} que representa a tela de saudação, chamada após o login.
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends Activity {

	public static final String CHAVE_NOME_USUARIO = "E.user.firstName";

	@ViewById
	TextView lblSaudacao;

	@AfterViews
	public void onReady() {
		// Recupera o nome do usuário, enviado via intent:
		lblSaudacao.setText("Bem vindo " + getIntent().getStringExtra(CHAVE_NOME_USUARIO));
		getIntent().removeExtra(CHAVE_NOME_USUARIO);
		// Apresenta uma mensagem, tipo toast, notificando o sucesso do login:
		Toast.makeText(this, R.string.msg_sucesso_login, Toast.LENGTH_LONG).show();
	}
}
