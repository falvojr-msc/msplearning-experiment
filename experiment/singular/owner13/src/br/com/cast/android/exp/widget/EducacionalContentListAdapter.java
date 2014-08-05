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
public class EducacionalContentListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	List<EducationalContent> educacionais;

	public void setDisciplines(List<EducationalContent> educacionais) {
		this.educacionais = educacionais;
	}

	@Override
	public int getCount() {
		return educacionais.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return educacionais.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int posicao, View viewAtual, ViewGroup arg2) {
		EducationalContentItemView educationalItemView;

		if(viewAtual == null){
			educationalItemView = EducationalContentItemView_.build(context);
		}else{
			educationalItemView = (EducationalContentItemView) viewAtual;
		}

		educationalItemView.configurarItemDisciplina(getItem(posicao));

		return educationalItemView;
	}

}
