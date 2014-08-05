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

@EActivity(R.layout.activity_education_content)
public class EducationContentActivity extends BaseActivity {

	public static String CHAVE_EDUCATIONAL = "CHAVE_EDUCATIONAL";

	@ViewById
	EditText txtTitulo, txtImg, txtNota, txtPagina;

	@RestService
	EducationalContentRestClient educationalRestClient;

	private EducationalContent educationalEdicao;

	@AfterViews
	public void tudoPronto() {
		educationalEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_EDUCATIONAL);
		if (educationalEdicao != null) {
			txtTitulo.setText(educationalEdicao.getTitle());
			txtImg.setText(educationalEdicao.getUrl());
			txtNota.setText(educationalEdicao.getTitle());
			txtPagina.setText(educationalEdicao.getPage().toString());
		}

		getIntent().removeExtra(CHAVE_EDUCATIONAL);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		super.iniciarLoading();

		if(camposObrigatoriosValidados(txtTitulo, txtImg, txtPagina) && validarCampoUrl()){
			EducationalContent educational = educationalEdicao == null ? new EducationalContent() : educationalEdicao;
			educational.setTitle(txtTitulo.getText().toString());
			educational.setUrl(txtImg.getText().toString());
			educational.setFootnote(txtNota.getText().toString());
			educational.setPage(Long.parseLong(txtPagina.getText().toString()));

			salvarDiscipline(educational);
		}
		super.terminarLoading();
	}

	private boolean camposObrigatoriosValidados(EditText... campos) {
		boolean campoValido = true;
		for (EditText campo : campos) {
			campo.setError(null);
			if (TextUtils.isEmpty(campo.getText())) {
				campo.setError(getString(R.string.msg_campo_obrigatorio));
				campo.requestFocus();
				campoValido = false;
			}
		}
		return campoValido;
	}

	private boolean validarCampoUrl() {
		boolean validaUrl = true;
		txtImg.setError(null);
		if (!Patterns.WEB_URL.matcher(txtImg.getText()).matches()) {
			txtImg.setError(getString(R.string.msg_url_invalido));
			txtImg.requestFocus();
			validaUrl = false;
		}

		return validaUrl;
	}

	@Background
	void salvarDiscipline(EducationalContent educational) {
		try {
			if (educationalEdicao == null) {
				educationalRestClient.insert(educational);
			} else {
				educationalRestClient.update(educational);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}
}
