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
public class EducationalContentActivity extends BaseActivity{

	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtTitulo, txtURL, txtNota, txtPagina;


	@RestService
	EducationalContentRestClient educationalContentRestClient;


	private EducationalContent educationContentEdicao;


	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		educationContentEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_USUARIO);
		if (educationContentEdicao != null) {
			txtTitulo.setText(educationContentEdicao.getTitle());
			txtURL.setText(educationContentEdicao.getUrl());
			txtNota.setText(educationContentEdicao.getFootnote());
			txtPagina.setText(educationContentEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtPagina, txtURL) && validarCampoURL();

		if (isValido) {
			super.iniciarLoading();
			EducationalContent educationalContent = educationContentEdicao == null ? new EducationalContent() : educationContentEdicao;
			educationalContent.setTitle(txtTitulo.getText().toString());
			educationalContent.setUrl(txtURL.getText().toString());
			educationalContent.setFootnote(txtNota.getText().toString());
			educationalContent.setPage(Long.valueOf(txtPagina.getText().toString()));

			salvarUsuario(educationalContent);
		}
	}

	@Background
	void salvarUsuario(EducationalContent educationalContent) {
		try {
			if (educationContentEdicao == null) {
				educationalContentRestClient.insert(educationalContent);
			} else {
				educationalContentRestClient.update(educationalContent);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(RESULT_CANCELED);
		}
		super.terminarLoading();
		finish();
	}

	//	/* ÚTIL (VALIDAÇÃO) */

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

	private boolean validarCampoURL() {
		txtURL.setError(null);
		if (!Patterns.WEB_URL.matcher(txtURL.getText()).matches()) {
			txtURL.setError(getString(R.string.msg_url_invalida));
			txtURL.requestFocus();
			return false;
		}
		return true;
	}


}
