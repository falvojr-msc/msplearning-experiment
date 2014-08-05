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

@EActivity(R.layout.activity_educational)
public class EducationalActivity extends BaseActivity {

	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtVideo, txtTitle, txtRodape, txtNumeroPagina;

	@RestService
	EducationalContentRestClient educationalRestClient;
	//
	private EducationalContent educationalEdicao;

	//
	@AfterViews
	public void tudoPronto() {
		educationalEdicao = (EducationalContent) getIntent()
				.getSerializableExtra(CHAVE_USUARIO);
		if (educationalEdicao != null) {
			txtTitle.setText(educationalEdicao.getTitle());
			txtRodape.setText(educationalEdicao.getFootnote());
			txtNumeroPagina.setText(educationalEdicao.getPage().toString());
			txtVideo.setText(educationalEdicao.getUrl());
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}

	@Click(R.id.botaoSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitle, txtRodape,
				txtNumeroPagina, txtVideo);
		if(!Patterns.WEB_URL.matcher(txtVideo.getText()).matches()){
			txtVideo.setError(getString(R.string.msg_url_invalida));
			txtVideo.requestFocus();
			isValido = false;
		}
		if (isValido) {
			super.iniciarLoading();
			EducationalContent educational = educationalEdicao == null ? new EducationalContent()
			: educationalEdicao;
			educational.setTitle(txtTitle.getText().toString());
			educational.setFootnote(txtRodape.getText().toString());
			educational.setPage(Long.parseLong(txtNumeroPagina.getText()
					.toString()));
			educational.setUrl(txtVideo.getText().toString());
			salvar(educational);
		}
	}

	@Background
	void salvar(EducationalContent educational) {
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

	private boolean validarCampoObrigatorio(EditText... campos) {
		boolean isValido = true;
		for (EditText campo : campos) {
			campo.setError(null);
			if (TextUtils.isEmpty(campo.getText())) {
				campo.setError(getString(R.string.prenchimento_obrigatorio));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;
	}
}
