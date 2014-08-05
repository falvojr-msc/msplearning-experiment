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
public class ConteudoEducacionalListAdapter extends BaseAdapter {

	List<EducationalContent> conteudosEducacionais;

	@RootContext
	Context context;

	public void setConteudosEducacionais(
			List<EducationalContent> conteudosEducacionais) {
		this.conteudosEducacionais = conteudosEducacionais;
	}

	@Override
	public int getCount() {
		return conteudosEducacionais.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return conteudosEducacionais.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int posicao, View itemViewAtual, ViewGroup parent) {
		ConteudoEducacionalItemView conteudoEducacionalItemView;
		if(itemViewAtual == null){
			conteudoEducacionalItemView = ConteudoEducacionalItemView_.build(context);
		}else{
			conteudoEducacionalItemView = (ConteudoEducacionalItemView) itemViewAtual;
		}

		conteudoEducacionalItemView.configurarItem(getItem(posicao));
		return conteudoEducacionalItemView;
	}

}
