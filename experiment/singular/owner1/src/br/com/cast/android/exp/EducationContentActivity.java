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

@EActivity(R.layout.activity_education_content)
public class EducationContentActivity extends BaseActivity {

	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtTitulo, txtUrlDoVideo, txtNotaRodape, txtPagina;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	private EducationalContent educationalEdicao;

	@AfterViews
	public void tudoPronto(){
		educationalEdicao = (EducationalContent)getIntent().getSerializableExtra(CHAVE_USUARIO);
		if(educationalEdicao != null){
			txtTitulo.setText(educationalEdicao.getTitle());
			txtUrlDoVideo.setText(educationalEdicao.getUrl());
			txtNotaRodape.setText(educationalEdicao.getFootnote());
			txtPagina.setText(educationalEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}

	/*Salvar*/

	@Click(R.id.btnSalvar)
	void onSalvar(){
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrlDoVideo, txtNotaRodape, txtPagina);

		if(isValido){
			super.iniciarLoading();
			EducationalContent educacional = educationalEdicao == null ? new EducationalContent() : educationalEdicao;
			educacional.setTitle(txtTitulo.getText().toString());
			educacional.setUrl(txtUrlDoVideo.getText().toString());
			educacional.setFootnote(txtNotaRodape.getText().toString());
			educacional.setPage(Long.valueOf(txtPagina.getText().toString()));

			salvarContenudoEducacional(educacional);
		}
	}

	@Background
	void salvarContenudoEducacional(EducationalContent discipline) {

		try{
			if(educationalEdicao == null){
				educationalContentRestClient.insert(discipline);
			} else{
				educationalContentRestClient.update(discipline);
			}
		}catch(RestClientException excecaoRest){
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}

	/*Util Validação*/


	private void validarCampoEmail() {
		txtUrlDoVideo.setError(null);
		if (TextUtils.isEmpty(txtUrlDoVideo.getText())) {
			txtUrlDoVideo.setError(getString(R.string.msg_campo_obrigatorio));
			txtUrlDoVideo.requestFocus();
		} else if (!Patterns.WEB_URL.matcher(txtUrlDoVideo.getText()).matches()) {
			txtUrlDoVideo.setError(getString(R.string.msg_url_invalido));
			txtUrlDoVideo.requestFocus();
		}
	}

	private boolean validarCampoObrigatorio(EditText... campos){
		boolean isValido = true;
		for(EditText campo : campos){
			campo.setError(null);
			if(TextUtils.isEmpty(campo.getText())){
				campo.setError(getString(R.string.msg_campo_obrigatorio));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;

	}

}

