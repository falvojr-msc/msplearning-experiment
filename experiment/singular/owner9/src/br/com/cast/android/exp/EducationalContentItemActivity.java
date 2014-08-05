package br.com.cast.android.exp;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.activity_educational_content_item)
public class EducationalContentItemActivity extends LinearLayout {
	@ViewById
	TextView txtConteudo;

	public EducationalContentItemActivity(Context context) {
		super(context);
	}

	/**
	 * Método responsável pela configuração do {@link TextView} txtNomeCompleto.<br>
	 * Ele é chamado antes do retorno do método getView da classe {@link UserListAdapter}.
	 * 
	 * @param usuario
	 */
	public void configurarItem(EducationalContent educationalContent) {
		txtConteudo.setText(educationalContent.getTitle());
	}
}
