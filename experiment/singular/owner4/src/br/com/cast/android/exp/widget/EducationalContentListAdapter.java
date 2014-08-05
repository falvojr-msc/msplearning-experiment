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

	@RootContext
	Context context;

	private List<EducationalContent> registros;

	public void setDisciplinas(List<EducationalContent> registros) {
		this.registros = registros;
	}

	@Override
	public int getCount() {
		return registros.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return registros.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int position, View itemViewAtual, ViewGroup parent) {
		EducationalContentItemView itemView;
		if (itemViewAtual == null) {
			itemView = EducationalContentItemView_.build(context);
		} else {
			itemView = (EducationalContentItemView)itemViewAtual;
		}
		itemView.configurarItem(getItem(position));
		return itemView;
	}
}
