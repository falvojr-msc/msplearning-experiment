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

	public static String CHAVE_EDUCATIONAL = "CHAVE_EDUCATIONAL";

	@ViewById
	EditText txtTitulo, txtVideo, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent educationalEdition;

	@AfterViews
	public void carregou(){
		educationalEdition = (EducationalContent) getIntent().getSerializableExtra(CHAVE_EDUCATIONAL);
		if (educationalEdition != null) {
			txtTitulo.setText(educationalEdition.getTitle());
			txtVideo.setText(educationalEdition.getUrl());
			txtNotaRodape.setText(educationalEdition.getFootnote());
			txtPagina.setText(educationalEdition.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_EDUCATIONAL);
	}

	@Click(R.id.btnSalvar)
	public void onSalvar(){
		if(validaCampoObrigatorio(txtTitulo, txtVideo, txtNotaRodape, txtPagina) && validaURL(txtVideo)){
			super.iniciarLoading();
			EducationalContent educational = educationalEdition == null ? new EducationalContent() : educationalEdition;
			educational.setTitle(txtTitulo.getText().toString());
			educational.setUrl(txtVideo.getText().toString());
			educational.setFootnote(txtNotaRodape.getText().toString());
			educational.setPage(Long.parseLong(txtPagina.getText().toString()));
			salvarEducational(educational);
		}
	}

	private boolean validaURL(EditText url) {
		boolean valido = true;
		if(!TextUtils.isEmpty(url.getText())){
			url.setError(null);
			if (!Patterns.WEB_URL.matcher(url.getText()).matches()) {
				url.setError(getString(R.string.msg_url_invalida));
				url.requestFocus();
				valido = false;
			}
		}
		return valido;
	}

	@Background
	protected void salvarEducational(EducationalContent educational) {
		try {
			if (educational == null) {
				educationalContentRestClient.insert(educational);
			} else {
				educationalContentRestClient.update(educational);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(RESULT_CANCELED);
		}
		super.terminarLoading();
		finish();

	}

	private boolean validaCampoObrigatorio(EditText... editTexts) {
		boolean preencheu = true;
		for (EditText editText : editTexts) {
			editText.setError(null);
			if(TextUtils.isEmpty(editText.getText())){
				editText.setError(getString(R.string.msg_campo_obrigatorio));
				editText.requestFocus();
				preencheu = false;
			}
		}
		return preencheu;
	}

}
