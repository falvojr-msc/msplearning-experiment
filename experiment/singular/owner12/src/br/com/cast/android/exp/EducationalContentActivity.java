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

	public static final String EDUCATIONAL_CONTENT_KEY = "educational_content_edit";

	@ViewById
	EditText txtTitulo, txtUrlImagem, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient restClient;

	private EducationalContent conteudoEducacional;

	public EducationalContent getConteudoEducacional() {
		if (conteudoEducacional == null) {
			conteudoEducacional = new EducationalContent();
		}
		return conteudoEducacional;
	}

	@AfterViews
	void init() {
		conteudoEducacional = (EducationalContent) getIntent()
				.getSerializableExtra(EDUCATIONAL_CONTENT_KEY);
		if (conteudoEducacional != null) {
			txtTitulo.setText(conteudoEducacional.getTitle());
			txtUrlImagem.setText(conteudoEducacional.getUrl());
			txtNotaRodape.setText(conteudoEducacional.getFootnote());
			txtPagina.setText(conteudoEducacional.getPage().toString());
		}
		getIntent().removeExtra(EDUCATIONAL_CONTENT_KEY);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		if (validarCamposObrigatorios(txtTitulo, txtUrlImagem, txtPagina)
				&& validarFormatoUrl(txtUrlImagem)) {
			EducationalContent conteudoEducacional = getConteudoEducacional();
			conteudoEducacional.setTitle(txtTitulo.getText().toString());
			conteudoEducacional.setUrl(txtUrlImagem.getText().toString());
			conteudoEducacional.setFootnote(txtNotaRodape.getText().toString());
			conteudoEducacional.setPage(Long.parseLong(txtPagina.getText()
					.toString()));
			salvar(conteudoEducacional);
			super.iniciarLoading();
		}
	}

	private boolean validarFormatoUrl(EditText textBox) {
		String url = textBox.getText().toString();
		boolean isUrlValida = Patterns.WEB_URL.matcher(url).matches();
		if (!isUrlValida) {
			textBox.setError(getString(R.string.msg_url_invalida));
			textBox.requestFocus();
		}
		return isUrlValida;
	}

	private boolean validarCamposObrigatorios(EditText... campos) {
		boolean isValid = true;
		for (EditText campo : campos) {
			if (TextUtils.isEmpty(campo.getText())) {
				isValid = false;
				campo.setError(getString(R.string.msg_campo_obrigatorio));
			}
		}
		return isValid;
	}

	@Background
	void salvar(EducationalContent conteudoEducacional) {
		try {
			if (conteudoEducacional.getId() == null) {
				restClient.insert(conteudoEducacional);
			} else {
				restClient.update(conteudoEducacional);
			}
			setResult(RESULT_OK);
			super.terminarLoading();
		} catch (RestClientException ex) {
			setResult(RESULT_CANCELED);
		}
		finish();
	}
}
