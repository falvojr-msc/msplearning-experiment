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

@EActivity(R.layout.activity_image)
public class ImageActivity extends BaseActivity {

	public static String CHAVE_IMAGEM = "CHAVE_IMAGEM";

	@ViewById
	EditText txtTitulo, txtImagemUrl, txtPagina, txtRodape;

	@RestService
	EducationalContentRestClient rest;

	private EducationalContent imagemEdicao;



	@AfterViews
	public void after() {
		// Lógica para carregar os campos no fluxo de "Edição":
		imagemEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_IMAGEM);
		if (imagemEdicao != null) {
			txtTitulo.setText(imagemEdicao.getTitle());
			txtRodape.setText(imagemEdicao.getFootnote());
			txtImagemUrl.setText(imagemEdicao.getUrl());
			txtPagina.setText(imagemEdicao.getPage().toString());

		}
		getIntent().removeExtra(CHAVE_IMAGEM);
	}


	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampos(txtTitulo, txtImagemUrl, txtPagina,
				txtRodape);
		if (isValido) {
			if (Patterns.WEB_URL.matcher(txtImagemUrl.getText()).matches()) {
				super.iniciarLoading();
				EducationalContent imagem = imagemEdicao == null ? new EducationalContent()
				: imagemEdicao;
				imagem.setTitle(txtTitulo.getText().toString());
				imagem.setUrl(txtImagemUrl.getText().toString());
				imagem.setFootnote(txtRodape.getText().toString());
				imagem.setPage(Long.valueOf(txtTitulo.getText().toString()));
				salvarImagem(imagem);
			}else{
				txtImagemUrl.setError(getString(R.string.msg_url_invalida));
				txtImagemUrl.requestFocus();
			}
		}
	}

	@Background
	void salvarImagem(EducationalContent imagem) {
		try {
			if (imagemEdicao == null) {
				rest.insert(imagem);
			} else {
				rest.update(imagem);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(99);
		}
		super.terminarLoading();
		finish();
	}

	private boolean validarCampos(EditText... campos) {
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
