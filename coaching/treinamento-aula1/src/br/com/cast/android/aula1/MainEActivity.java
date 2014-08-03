package br.com.cast.android.aula1;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import br.com.cast.android.aula1.rest.LoginRestClient;
import br.com.cast.android.aula1.rest.entity.User;

/**
 * {@link ActionBarActivity} que representa a tela de login, usando Android Annotations.
 * Essa classe é uma evolução da {@link MainEActivity}.
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainEActivity extends ActionBarActivity {

	@ViewById
	EditText txtEmail, txtSenha;

	@RestService
	LoginRestClient loginRestClient;

	@Click(R.id.btnLogar)
	void clickBotaoLogar() {
		validarCampoSenha();
		validarCampoEmail();

		boolean isValid = txtEmail.getError() == null && txtSenha.getError() == null;
		if (isValid) {
			autenticarUsuarioAsync();
		}
	}

	@OptionsItem(R.id.action_sobre)
	void clickMenuSobre() {
		new AlertDialog.Builder(this)
		.setTitle(this.getString(R.string.titulo_dialog_sobre))
		.setMessage(getString(R.string.descricao_dialog_sobre)).setIcon(android.R.drawable.ic_dialog_info)
		.setNeutralButton(android.R.string.ok, null)
		.show();
	}

	private void validarCampoEmail() {
		txtEmail.setError(null);
		if (TextUtils.isEmpty(txtEmail.getText())) {
			txtEmail.setError(getString(R.string.msg_campo_obrigatorio));
			txtEmail.requestFocus();
		} else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText()).matches()) {
			txtEmail.setError(getString(R.string.msg_email_invalido));
			txtEmail.requestFocus();
		}
	}

	private void validarCampoSenha() {
		txtSenha.setError(null);
		if (TextUtils.isEmpty(txtSenha.getText())) {
			txtSenha.setError(getString(R.string.msg_campo_obrigatorio));
			txtSenha.requestFocus();
		}
	}

	@Background
	void autenticarUsuarioAsync() {
		try {
			// Consome o REST service de autenticação:
			User credenciais = new User(txtEmail.getText().toString(), txtSenha.getText().toString());
			User user = loginRestClient.authenticate(credenciais);
			// Cria e configura o intent para redirecionar para a activity de home:
			Intent intent = HomeActivity_.intent(this).get();
			intent.putExtra(HomeActivity.CHAVE_NOME_USUARIO, user.getFirstName());
			startActivity(intent);
		} catch (RestClientException erroAutenticacao) {
			showErroAutenticacao();
		}
	}

	@UiThread
	void showErroAutenticacao() {
		txtEmail.setError(this.getString(R.string.msg_erro_autenticacao));
		txtEmail.requestFocus();
	}
}
