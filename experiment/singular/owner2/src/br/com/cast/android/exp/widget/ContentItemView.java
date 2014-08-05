package br.com.cast.android.exp.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.exp.R;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EViewGroup(R.layout.content_item_view)
public class ContentItemView extends LinearLayout {

	public ContentItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@ViewById
	TextView txtContentTitle;

	public void configurarItem(EducationalContent econtent) {

		txtContentTitle.setText(econtent.getTitle());
	}
}
