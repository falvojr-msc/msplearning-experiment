package br.com.cast.android.exp.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.imagem_item_view)
public class ImagemItemView extends LinearLayout {

	@ViewById
	TextView textImagemItem;

	public ImagemItemView(Context context) {
		super(context);
	}

	public void configurarItemDisciplina(EducationalContent educacional){
		textImagemItem.setText(educacional.getTitle());
	}


}
