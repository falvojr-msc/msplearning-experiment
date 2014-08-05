package br.com.cast.android.exp.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EBean
public class EducationalContentListAdapter extends BaseAdapter {

	/**
	 * Recupera o contexto atual de execução. Neste caso podemos até mesmo
	 * restringir esse campo para o tipo {@link UserListActivity}.
	 */
	@RootContext
	Context context;

	private List<EducationalContent> econtents;

	public void setEContent(List<EducationalContent> econtents) {
		this.econtents = econtents;
	}

	@Override
	public int getCount() {
		return econtents.size();
	}

	@Override
	public EducationalContent getItem(int arg0) {
		return econtents.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public ContentItemView getView(int position, View itemViewAtual,
			ViewGroup arg2) {
		ContentItemView contentItemView;
		if (itemViewAtual == null) {
			contentItemView = ContentItemView_.build(context);
		} else {
			contentItemView = (ContentItemView) itemViewAtual;
		}
		contentItemView.configurarItem(getItem(position));
		return contentItemView;
	}

}
