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

	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtTitulo, txtUrlImagem, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient contentRestClient;

	private EducationalContent contentEdicao;

	@AfterViews
	public void tudoPronto() {
		contentEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_USUARIO);
		if (contentEdicao != null) {
			txtTitulo.setText(contentEdicao.getTitle());
			txtUrlImagem.setText(contentEdicao.getUrl());
			txtNotaRodape.setText(contentEdicao.getFootnote());
			txtPagina.setText(contentEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}
	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrlImagem, txtPagina);
		if (isValido && validarURL()) {
			super.iniciarLoading();
			EducationalContent content = contentEdicao == null ? new EducationalContent() : contentEdicao;
			content.setTitle(txtTitulo.getText().toString());
			content.setUrl(txtUrlImagem.getText().toString());
			content.setFootnote(txtNotaRodape.getText().toString());
			content.setPage(Long.valueOf(txtPagina.getText().toString()));
			salvarContent(content);
		}
	}
	private boolean validarURL() {
		boolean isValid =Patterns.WEB_URL.matcher(txtUrlImagem.getText()).matches();
		if(!isValid)
		{
			txtUrlImagem.setError(null);
			txtUrlImagem.setError(getString(R.string.me02));
			txtUrlImagem.requestFocus();
		}
		return isValid;
	}

	@Background
	void salvarContent(EducationalContent content) {
		try {
			if (contentEdicao == null) {
				contentRestClient.insert(content);
			} else {
				contentRestClient.update(content);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}

	/* ÚTIL (VALIDAÇÃO) */

	private boolean validarCampoObrigatorio(EditText... campos) {
		boolean isValido = true;
		for (EditText campo : campos) {
			campo.setError(null);
			if (TextUtils.isEmpty(campo.getText())) {
				campo.setError(getString(R.string.msg_campo_obrigatorio));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;
	}
}
