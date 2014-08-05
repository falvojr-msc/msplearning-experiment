package br.com.cast.android.exp.components;

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

	@RootContext
	Context context;

	private List<EducationalContent> listaConteudoEducacional;

	public void setListaConteudoEducacional(
			List<EducationalContent> listaConteudoEducacional) {
		this.listaConteudoEducacional = listaConteudoEducacional;
	}

	@Override
	public int getCount() {
		return listaConteudoEducacional.size();
	}

	@Override
	public EducationalContent getItem(int index) {
		return listaConteudoEducacional.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View readyView, ViewGroup parentView) {
		EducationalContentItemView view = readyView != null ? (EducationalContentItemView) readyView
				: EducationalContentItemView_.build(context);
		view.configurarItem(getItem(index));
		return view;
	}

}
