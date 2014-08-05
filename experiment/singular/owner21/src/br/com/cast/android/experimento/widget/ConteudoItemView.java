package br.com.cast.android.experimento.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.experimento.R;
import br.com.cast.android.experimento.rest.entity.EducationalContent;

/**
 * Componente que representa o template de cada um dos usuários manipulador no {@link UserListAdapter}.
 * A annotation {@link EViewGroup} identifica que esta classe representa um elemnto de visão que pode agrupar outros, como é o caso do {@link LinearLayout}.
 * 
 * @author venilton.junior
 */
@EViewGroup(R.layout.conteudo_item)
public class ConteudoItemView extends LinearLayout {

	@ViewById
	TextView txtConteudoEducacional;

	public ConteudoItemView(Context context) {
		super(context);
	}

	public void configurarItem(EducationalContent conteudos) {
		txtConteudoEducacional.setText(conteudos.getTitle());
	}
}
