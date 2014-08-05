package br.com.cast.android.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EBean
public class EducationalListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	List<EducationalContent> educacoes;


	public void setEducacoes(List<EducationalContent> educacoes) {
		this.educacoes = educacoes;
	}

	@Override
	public int getCount() {
		return educacoes.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return educacoes.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int posicao, View itemView, ViewGroup parent) {
		EducationalItemView educationalItemView;

		if (itemView == null) {
			educationalItemView = EducationalItemView_.build(context);
		} else {
			educationalItemView = (EducationalItemView) itemView;
		}
		educationalItemView.configurarItem(getItem(posicao));
		return educationalItemView;
	}

}
