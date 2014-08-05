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
public class EducationalListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	List<EducationalContent> listaConteudo;


	public void setListaConteudo(List<EducationalContent> listaConteudo) {
		this.listaConteudo = listaConteudo;
	}

	@Override
	public int getCount() {

		return listaConteudo.size();
	}

	@Override
	public Object getItem(int posicao) {
		return listaConteudo.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int posicao, View itemViewAtual, ViewGroup parent) {

		EducationalItemView educationalItemView = null ;

		if (educationalItemView == null) {

			educationalItemView = EducationalItemView_.build(context);

		} else {
			educationalItemView = (EducationalItemView) itemViewAtual;
		}
		educationalItemView.configurarItem((EducationalContent) getItem(posicao));

		return educationalItemView;
	}

}
