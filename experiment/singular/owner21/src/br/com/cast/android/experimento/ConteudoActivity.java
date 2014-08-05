package br.com.cast.android.experimento;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.text.TextUtils;
import android.widget.EditText;
import br.com.cast.android.experimento.base.BaseActivity;
import br.com.cast.android.experimento.rest.ContentRestClient;
import br.com.cast.android.experimento.rest.entity.EducationalContent;

/**
 * {@link BaseActivity} que representa a tela de inclusão e alteração de um {@link User}.
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_educational)
public class ConteudoActivity extends BaseActivity {

	/**
	 * Constante pública utilizada no fluxo de "Edição".
	 */
	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtTitulo, txtUrl, txtRodape, txtPagina;

	@RestService
	ContentRestClient restClient;

	private EducationalContent conteudoEdicao;

	@AfterViews
	public void tudoPronto() {
		// Lógica para carregar os campos no fluxo de "Edição":
		conteudoEdicao = (EducationalContent) getIntent().getSerializableExtra(CHAVE_USUARIO);
		if (conteudoEdicao != null) {
			txtPagina.setText(conteudoEdicao.getPage().toString());
			txtRodape.setText(conteudoEdicao.getFootnote());
			txtTitulo.setText(conteudoEdicao.getTitle());
			txtUrl.setText(conteudoEdicao.getUrl());
			getIntent().removeExtra(CHAVE_USUARIO);
		}
	}

	/* SALVAR */

	/**
	 * A annotation {@link Click} faz com que um método seja chamado no click do elemento com o R.id especificado.
	 */
	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtPagina, txtRodape, txtTitulo, txtUrl);
		if (isValido) {
			super.iniciarLoading();
			EducationalContent itens = conteudoEdicao == null ? new EducationalContent() : conteudoEdicao;
			itens.setPage(Long.valueOf(txtPagina.getText().toString()));
			itens.setFootnote(txtRodape.getText().toString());
			itens.setTitle(txtTitulo.getText().toString());
			salvarConteudo(itens);
		}
	}

	@Background
	void salvarConteudo(EducationalContent itens) {
		try {
			if (conteudoEdicao == null) {
				restClient.insert(itens);
			} else {
				restClient.update(itens);
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
