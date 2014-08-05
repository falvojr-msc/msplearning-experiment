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

/**
 * {@link BaseActivity} que representa a tela de inclusão e alteração de um
 * {@link User}.
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_educational)
public class EducationalContentActivity extends BaseActivity {

	/**
	 * Constante pública utilizada no fluxo de "Edição".
	 */
	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtTitulo, txtUrl, txtNota, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent educationalContentEdicao;

	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		educationalContentEdicao = (EducationalContent) getIntent()
				.getSerializableExtra(CHAVE_USUARIO);
		if (educationalContentEdicao != null) {
			txtTitulo.setText(educationalContentEdicao.getTitle());
			txtUrl.setText(educationalContentEdicao.getUrl());
			txtNota.setText(educationalContentEdicao.getFootnote());
			txtPagina.setText(educationalContentEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}

	/* SALVAR */

	/**
	 * A annotation {@link Click} faz com que um método seja chamado no click do
	 * elemento com o R.id especificado.
	 */
	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrl, txtPagina);
		if (isValido) {
			if (validarCampoUrl()) {
				super.iniciarLoading();
				EducationalContent educationalContent = educationalContentEdicao == null ? new EducationalContent()
				: educationalContentEdicao;
				educationalContent.setTitle(txtTitulo.getText().toString());
				educationalContent.setUrl(txtUrl.getText().toString());
				educationalContent.setFootnote(txtNota.getText().toString());
				educationalContent.setPage(Long.parseLong(txtPagina.getText()
						.toString()));

				salvarEducationalContent(educationalContent);
			}
		}
	}

	private boolean validarCampoUrl() {
		txtUrl.setError(null);
		if (!Patterns.WEB_URL.matcher(txtUrl.getText()).matches()) {
			txtUrl.setError(getString(R.string.msg_url_invalida));
			txtUrl.requestFocus();
			return false;
		}
		return true;
	}

	@Background
	void salvarEducationalContent(EducationalContent educationalContent) {
		try {
			if (educationalContentEdicao == null) {
				educationalContentRestClient.insert(educationalContent);
			} else {
				educationalContentRestClient.update(educationalContent);
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
