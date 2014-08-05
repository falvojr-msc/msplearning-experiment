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

	/**
	 * Constante pública utilizada no fluxo de "Edição".
	 */
	public static String CHAVE_CONTENT = "CHAVE_CONTENT";

	@ViewById
	EditText txtContentTitle, txtContentUri, txtContentFootNote,
	txtContentPage;

	@RestService
	EducationalContentRestClient restClient;

	private EducationalContent econtentEdit;

	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		econtentEdit = (EducationalContent) getIntent().getSerializableExtra(
				CHAVE_CONTENT);
		if (econtentEdit != null) {
			txtContentTitle.setText(econtentEdit.getTitle());
			txtContentUri.setText(econtentEdit.getUrl());
			txtContentPage.setText(econtentEdit.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_CONTENT);
	}

	/* SALVAR */

	/**
	 * A annotation {@link Click} faz com que um método seja chamado no click do
	 * elemento com o R.id especificado.
	 */
	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtContentTitle, txtContentUri, txtContentPage);
		if (isValido ) {
			if(validarUrl(txtContentUri)){
				super.iniciarLoading();
				EducationalContent econtent = econtentEdit == null ? new EducationalContent() : econtentEdit;
				econtent.setTitle(txtContentTitle.getText().toString());
				econtent.setUrl(txtContentUri.getText().toString());
				econtent.setFootnote(txtContentFootNote.getText().toString());
				econtent.setPage(converteParaLong(txtContentPage.getText().toString()));
				salvarEContent(econtent);
			}
		}
	}

	@Background
	void salvarEContent(EducationalContent econtent) {
		try {
			if (econtentEdit == null) {
				restClient.insert(econtent);
			} else {
				restClient.update(econtent);
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
				campo.setError(getString(R.string.me01));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;
	}

	private boolean validarUrl(EditText campoUrl) {
		boolean isValido = true;
		campoUrl.setError(null);
		if(!Patterns.WEB_URL.matcher(campoUrl.getText()).matches()){
			campoUrl.setError(getString(R.string.me02));
			campoUrl.requestFocus();
			isValido = false;
		}
		return isValido;
	}


	private long converteParaLong(String valor) {
		long page = 0;
		try {
			page = Long.parseLong(valor);
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return page;
	}

}
