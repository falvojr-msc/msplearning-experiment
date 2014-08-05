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

@EActivity(R.layout.activity_videos)
public class Videos extends BaseActivity {

	public static String CHAVE_VIDEOS = "CHAVE_VIDEOS";

	@ViewById
	EditText txtTitulo, txtUrl, txtRodape, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent educationalEdit;

	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		educationalEdit = (EducationalContent) getIntent().getSerializableExtra(CHAVE_VIDEOS);
		if (educationalEdit != null) {
			txtTitulo.setText(educationalEdit.getTitle());
			txtUrl.setText(educationalEdit.getUrl());
			txtRodape.setText(educationalEdit.getFootnote());
			txtPagina.setText(educationalEdit.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_VIDEOS);
	}


	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrl, txtPagina) && validarUrl(txtUrl);
		if (isValido) {
			super.iniciarLoading();
			EducationalContent edu = educationalEdit == null ? new EducationalContent() : educationalEdit;
			edu.setTitle(txtTitulo.getText().toString());
			edu.setUrl(txtUrl.getText().toString());
			edu.setFootnote(txtRodape.getText().toString());
			edu.setPage(Long.parseLong(txtPagina.getText().toString()));
			salvarEducational(edu);
		}
	}

	@Background
	void salvarEducational(EducationalContent edu) {
		try {
			if (educationalEdit == null) {
				educationalContentRestClient.insert(edu);
			} else {
				educationalContentRestClient.update(edu);
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
				campo.setError(getString(R.string.msg_ME01));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;
	}

	private boolean validarUrl(EditText url) {
		boolean isValido = true;
		url.setError(null);
		if (!Patterns.WEB_URL.matcher(url.getText()).matches()) {
			url.setError(getString(R.string.msg_ME02));
			url.requestFocus();
			isValido = false;
		}
		return isValido;
	}
}
