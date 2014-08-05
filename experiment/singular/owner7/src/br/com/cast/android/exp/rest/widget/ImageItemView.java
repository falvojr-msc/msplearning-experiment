package br.com.cast.android.exp.rest.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.image_item)
public class ImageItemView extends LinearLayout{

	@ViewById
	TextView txtImagem;


	public ImageItemView(Context context) {
		super(context);
	}

	public void configurar(EducationalContent ec){
		txtImagem.setText(ec.getTitle().toString());

	}
}
