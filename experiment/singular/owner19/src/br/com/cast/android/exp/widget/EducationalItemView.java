package br.com.cast.android.exp.widget;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.activity_educational_item)
public class EducationalItemView extends LinearLayout{

	@ViewById
	TextView txtTitle;

	public EducationalItemView(Context context) {
		super(context);
	}

	public void configurarItem(EducationalContent educational) {
		txtTitle.setText(educational.getTitle());
	}

}
