package br.com.cast.android.exp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import br.com.cast.android.exp.base.BaseActivity;
import br.com.cast.android.exp.rest.EducationalContentRestClient;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EActivity(R.layout.activity_educational_content)
public class EducationalContentActivity extends BaseActivity {

	public static String CHAVE_REGISTRO = "CHAVE_REGISTRO";

	@ViewById
	EditText txtTitulo, txtURLImagem, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent registroEdicao;

	@AfterViews
	public void tudoPronto() {
		registroEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_REGISTRO);
		if (registroEdicao != null) {
			txtTitulo.setText(registroEdicao.getTitle());
			txtURLImagem.setText(registroEdicao.getUrl());
			txtNotaRodape.setText(registroEdicao.getFootnote());
			txtPagina.setText(registroEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_REGISTRO);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtURLImagem, txtNotaRodape, txtPagina) && validarURL();
		if (isValido) {
			super.iniciarLoading();
			EducationalContent registro = registroEdicao == null ? new EducationalContent() : registroEdicao;
			registro.setTitle(txtTitulo.getText().toString());
			registro.setUrl(txtURLImagem.getText().toString());
			registro.setFootnote(txtNotaRodape.getText().toString());
			registro.setPage(Long.parseLong(txtPagina.getText().toString()));
			salvar(registro);
		}
	}

	@Background
	void salvar(EducationalContent registro) {
		try {
			if (registroEdicao == null) {
				educationalContentRestClient.insert(registro);
			} else {
				educationalContentRestClient.update(registro);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}

	private boolean validarCampoObrigatorio(EditText... campos) {
		boolean isValido = true;
		EditText campoFocus = null;
		for (EditText campo : campos) {
			campo.setError(null);
			if (TextUtils.isEmpty(campo.getText())) {
				campo.setError(getString(R.string.msg_me01));
				campoFocus = campoFocus == null? campo : campoFocus;
				isValido = false;
			}
		}
		if(campoFocus != null){
			campoFocus.requestFocus();
		}
		return isValido;
	}

	private boolean validarURL() {
		boolean isValido = true;
		txtURLImagem.setError(null);
		if (!Patterns.WEB_URL.matcher(txtURLImagem.getText()).matches()) {
			txtURLImagem.setError(getString(R.string.msg_me02));
			txtURLImagem.requestFocus();
			isValido = false;
		}
		return isValido;
	}
}
