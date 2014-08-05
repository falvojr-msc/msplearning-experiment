package br.com.cast.android.exp.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.conteudo_educacional_item)
public class ConteudoEducacionalItemView extends LinearLayout {

	public ConteudoEducacionalItemView(Context context) {
		super(context);
	}

	@ViewById
	TextView txtTitulo;

	void configurarItem(EducationalContent conteudoEducacional){
		txtTitulo.setText(conteudoEducacional.getTitle());
	}

}
