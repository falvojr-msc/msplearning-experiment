package br.com.cast.android.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.educacional_item)
public class EducationalItemView extends LinearLayout {

	@ViewById
	TextView txtEducacional;

	public EducationalItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void configurarItem(EducationalContent educational) {
		txtEducacional.setText(educational.getTitle());
	}

}
