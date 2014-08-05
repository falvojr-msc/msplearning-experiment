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

@EActivity(R.layout.activity_educational_form)
public class EducationalFormActivity extends BaseActivity {

	public static String CHAVE = "CHAVE";

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	@ViewById
	EditText txtTitulo,txtUrl,txtRodape,txtPagina;

	private EducationalContent educationalEdition;


	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		educationalEdition = (EducationalContent) getIntent().getSerializableExtra(CHAVE);
		if (educationalEdition != null) {
			txtTitulo.setText(educationalEdition.getTitle());
			txtUrl.setText(educationalEdition.getUrl());
			txtRodape.setText(educationalEdition.getFootnote());
			txtPagina.setText(educationalEdition.getPage().toString());

		}
		getIntent().removeExtra(CHAVE);
	}


	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo,txtUrl,txtRodape,txtPagina);
		if (isValido ) {
			if(validarCampoUrl()){
				super.iniciarLoading();
				EducationalContent education = educationalEdition == null ? new EducationalContent() : educationalEdition;
				education.setTitle(txtTitulo.getText().toString());
				education.setUrl(txtUrl.getText().toString());
				education.setFootnote(txtRodape.getText().toString());
				education.setPage(Long.valueOf(txtPagina.getText().toString()));
				salvarUsuario(education);
			}
		}
	}


	@Background
	void salvarUsuario(EducationalContent education) {
		try {
			if (educationalEdition == null) {
				educationalContentRestClient.insert(education);
			} else {
				educationalContentRestClient.update(education);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}

	private boolean validarCampoUrl() {
		txtUrl.setError(null);
		boolean isValido = true;

		if (!Patterns.WEB_URL.matcher(txtUrl.getText()).matches()) {
			isValido = false;
			txtUrl.setError(getString(R.string.msg_url_invalida));
			txtUrl.requestFocus();

		}
		return isValido;
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
}
