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

	public static String CHAVE_EDUCATIONAL_CONTENT = "CHAVE_EDUCATIONAL_CONTENT";

	@ViewById
	EditText txtTitulo, txtUrl, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent educationalContentEdicao;

	@AfterViews
	public void ready() {
		// Lógica para carregar os campos no fluxo de "Edição":
		educationalContentEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_EDUCATIONAL_CONTENT);
		if (educationalContentEdicao != null) {
			txtTitulo.setText(educationalContentEdicao.getTitle());
			txtUrl.setText(educationalContentEdicao.getUrl());
			txtNotaRodape.setText(educationalContentEdicao.getFootnote());
			txtPagina.setText(Long.valueOf(educationalContentEdicao.getPage()).toString());

		}
		getIntent().removeExtra(CHAVE_EDUCATIONAL_CONTENT);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrl, txtNotaRodape, txtPagina);
		if (isValido) {
			super.iniciarLoading();
			EducationalContent educationalContent = educationalContentEdicao == null ? new EducationalContent() : educationalContentEdicao;
			educationalContent.setFootnote(txtNotaRodape.getText().toString());
			educationalContent.setPage(Long.parseLong(txtPagina.getText().toString()));
			educationalContent.setTitle(txtTitulo.getText().toString());
			educationalContent.setUrl(txtUrl.getText().toString());
			salvar(educationalContent);
		}
	}

	@Background
	void salvar(EducationalContent educationalContent) {
		try {
			if (educationalContentEdicao == null) {
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


	private boolean validarCampoObrigatorio(EditText txtTitulo,EditText txtUrl,EditText txtNotaRodape,EditText txtPagina) {
		boolean isValido = true;
		if(TextUtils.isEmpty(txtTitulo.getText())){
			txtTitulo.setError(getString(R.string.msg_campo_obrigatorio));
			txtTitulo.requestFocus();
			isValido = false;
		}

		if(TextUtils.isEmpty(txtPagina.getText())){
			txtPagina.setError(getString(R.string.msg_campo_obrigatorio));
			txtPagina.requestFocus();
			isValido = false;
		}

		if(TextUtils.isEmpty(txtUrl.getText())){
			txtUrl.setError(getString(R.string.msg_campo_obrigatorio));
			txtUrl.requestFocus();
			isValido = false;
		}else if(!Patterns.WEB_URL.matcher(txtUrl.getText()).matches()){
			txtUrl.setError(getString(R.string.msg_campo_url_invalido));
			txtUrl.requestFocus();
			isValido = false;
		}
		return isValido;
	}
}
