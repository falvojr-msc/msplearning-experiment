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

	public static final String CHAVE_EDUCATIONAL = "CHAVE_EDUCATIONAL";

	@ViewById
	EditText txtTitulo, txtUrlImagem, txtNotaRodaPe, txtPagina;

	@RestService
	EducationalContentRestClient educationalRestClient;

	private EducationalContent educationalEdicao;

	@AfterViews
	public void carregaCamposEdicao() {
		educationalEdicao = (EducationalContent) getIntent()
				.getSerializableExtra(CHAVE_EDUCATIONAL);
		if (educationalEdicao != null) {
			txtTitulo.setText(educationalEdicao.getTitle());
			txtUrlImagem.setText(educationalEdicao.getUrl());
			txtNotaRodaPe.setText(educationalEdicao.getFootnote());
			txtPagina.setText(educationalEdicao.getPage().toString());
		}
		getIntent().removeExtra(CHAVE_EDUCATIONAL);
	}

	/* SALVAR */

	/**
	 * A annotation {@link Click} faz com que um método seja chamado no click do
	 * elemento com o R.id especificado.
	 */
	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtTitulo, txtUrlImagem);

		if (isValido) {
			boolean isValidoo = validarCampoObrigatorioNumerico(txtPagina.getText().toString());
			boolean isUrlValido = validarUrl(txtUrlImagem.getText().toString());
			if (isValidoo && isUrlValido) {
				super.iniciarLoading();
				EducationalContent educational = educationalEdicao == null ? new EducationalContent() : educationalEdicao;
				educational.setTitle(txtTitulo.getText().toString());
				educational.setUrl(txtUrlImagem.getText().toString());
				educational.setPage(Long.valueOf(txtPagina.getText().toString()));
				educational.setFootnote(txtNotaRodaPe.getText().toString());
				salvarEducational(educational);
			}



		}
	}



	@Background
	void salvarEducational(EducationalContent educational) {
		try {
			if (educational == null) {
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

	/* ÚTIL (VALIDAÇÃO) */

	private boolean validarUrl(String string) {
		boolean retorno = false;
		if (!Patterns.WEB_URL.matcher(txtUrlImagem.getText()).matches()) {
			txtUrlImagem.setError(getString(R.string.msg_url_invalido));
			txtUrlImagem.requestFocus();
		}else{
			retorno = true;
		}
		return retorno;
	}

	private boolean validarCampoObrigatorioNumerico(String pagina)
	{
		boolean retorno = false;
		if (pagina != null) {
			try{
				Long a = Long.valueOf(pagina);
				retorno = true;
			}
			catch (Exception e){
				txtPagina.setError(getString(R.string.msg_valor_pagina_invalido));
				txtPagina.requestFocus();
			}
		}

		return retorno;
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
