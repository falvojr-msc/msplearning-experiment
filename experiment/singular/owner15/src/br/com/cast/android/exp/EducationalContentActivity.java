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
public class EducationalContentActivity extends BaseActivity  {


	public static String CHAVE = "CHAVE_EDUCATIONAL_CONTENT";

	@ViewById
	EditText txtTitle, txtVideo, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient  itemRestClient;

	private EducationalContent itemEdicao;
	public EducationalContentActivity() {
		// TODO Auto-generated constructor stub
	}


	@AfterViews
	public void init() {
		itemEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE);
		if (itemEdicao != null) {
			txtTitle.setText(itemEdicao.getTitle());
			txtVideo.setText(itemEdicao.getUrl());
			txtNotaRodape.setText(itemEdicao.getFootnote());
			txtPagina.setText(itemEdicao.getPage().toString());
			txtVideo.setText(itemEdicao.getUrl());

		}
		getIntent().removeExtra(CHAVE);
	}


	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitle, txtVideo,txtNotaRodape,txtPagina) && validarCampoURL();
		if (isValido) {
			super.iniciarLoading();
			EducationalContent item = itemEdicao == null ? new EducationalContent() : itemEdicao;
			item.setTitle(txtTitle.getText().toString());
			item.setFootnote(txtNotaRodape.getText().toString());
			item.setUrl(txtVideo.getText().toString());
			item.setPage(Long.parseLong(txtPagina.getText().toString()));
			salvar(item);
		}
	}

	@Background
	void salvar(EducationalContent item) {
		try {
			if (itemEdicao == null) {
				itemRestClient.insert(item);
			} else {
				itemRestClient.update(item);
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

	private boolean validarCampoURL() {
		txtVideo.setError(null);
		boolean isValid = true;
		if (!Patterns.WEB_URL.matcher(txtVideo.getText()).matches()) {
			txtVideo.setError(getString(R.string.msg_erro_url));
			txtVideo.requestFocus();
			isValid = false;
		}
		return isValid;
	}
}
