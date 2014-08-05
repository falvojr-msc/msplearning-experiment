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

@EActivity(R.layout.activity_conteudo_educacional)
public class ConteudoEducacionalActivity extends BaseActivity {

	public static String CHAVE_CONTEUDO_EDUCACIONAL = "CHAVE CONTEUDO EDUCACIONAL";

	@ViewById
	EditText txtTitulo, txtUrl,txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient conteudoEducacionalRestClient;

	private EducationalContent conteudoEducacionalEdicao;

	@AfterViews
	void tudoPronto(){
		conteudoEducacionalEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_CONTEUDO_EDUCACIONAL);
		if (conteudoEducacionalEdicao != null) {
			txtTitulo.setText(conteudoEducacionalEdicao.getTitle());
			txtUrl.setText(conteudoEducacionalEdicao.getUrl());
			txtNotaRodape.setText(conteudoEducacionalEdicao.getFootnote());
			txtPagina.setText(conteudoEducacionalEdicao.getPage().toString());

		}
		getIntent().removeExtra(CHAVE_CONTEUDO_EDUCACIONAL);

	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrl, txtPagina);
		if (isValido && validarUrl()) {
			super.iniciarLoading();
			EducationalContent conteudoEducacional = conteudoEducacionalEdicao == null ? new EducationalContent() : conteudoEducacionalEdicao;
			conteudoEducacional.setTitle(txtTitulo.getText().toString());
			conteudoEducacional.setUrl(txtUrl.getText().toString());
			conteudoEducacional.setFootnote(txtNotaRodape.getText().toString());
			conteudoEducacional.setPage(Long.parseLong(txtPagina.getText().toString()));
			salvarConteudoEducacional(conteudoEducacional);
		}
	}

	@Background
	void salvarConteudoEducacional(EducationalContent conteudoEducacional) {
		try {
			if (conteudoEducacionalEdicao == null) {
				conteudoEducacionalRestClient.insert(conteudoEducacional);
			} else {
				conteudoEducacionalRestClient.update(conteudoEducacional);
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

	private boolean validarUrl(){
		txtUrl.setError(null);
		if (!Patterns.WEB_URL.matcher(txtUrl.getText()).matches()) {
			txtUrl.setError(getString(R.string.msg_url_invalida));
			txtUrl.requestFocus();
			return false;
		}
		return true;
	}


}
