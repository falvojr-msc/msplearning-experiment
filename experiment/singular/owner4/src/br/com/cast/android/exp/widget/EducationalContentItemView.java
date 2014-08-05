package br.com.cast.android.exp.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.educational_content_item_view)
public class EducationalContentItemView extends LinearLayout {

	@ViewById
	TextView txtTitulo;


	public EducationalContentItemView(Context context) {
		super(context);
	}

	public void configurarItem(EducationalContent registro) {
		txtTitulo.setText(registro.getTitle());
	}
}
