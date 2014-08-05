package br.com.cast.android.exp;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cast.android.exp.base.BaseActivity;
import br.com.cast.android.exp.rest.EducationalContentRestClient;
import br.com.cast.android.exp.rest.entity.EducationalContent;
import br.com.cast.android.exp.widget.ConteudoEducacionalListAdapter;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.conteudo_educacional_menu_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView listViewConteudoEducacional;

	@Bean
	ConteudoEducacionalListAdapter conteudoEducacionalListAdapter;

	@RestService
	EducationalContentRestClient conteudoEducacionalRestClient;

	@AfterViews
	void tudoPronto(){
		super.registerForContextMenu(listViewConteudoEducacional);
		super.iniciarLoading();
		carregarListView();
	}


	@Background
	void carregarListView() {
		List<EducationalContent> conteudosEducacionais = conteudoEducacionalRestClient.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(conteudosEducacionais);
	}


	@UiThread
	void carregarListView(List<EducationalContent> conteudosEducacionais) {
		conteudoEducacionalListAdapter.setConteudosEducacionais(conteudosEducacionais);
		listViewConteudoEducacional.setAdapter(conteudoEducacionalListAdapter);
		conteudoEducacionalListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarListView();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir(){
		ConteudoEducacionalActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_inclusao);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.educational_content_list , menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final EducationalContent conteudoEducacionalSelecionado = conteudoEducacionalListAdapter.getItem(info.position);

		switch(item.getItemId()){

		case R.id.action_editar:

			Intent intent = ConteudoEducacionalActivity_.intent(this).get();
			intent.putExtra(ConteudoEducacionalActivity.CHAVE_CONTEUDO_EDUCACIONAL, conteudoEducacionalSelecionado);

			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:

			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletarConteudoEducacional(conteudoEducacionalSelecionado);
				}
			};

			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(String.format(getString(R.string.msg_dialog_confirmacao_exclusao), conteudoEducacionalSelecionado.getTitle()))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}


	@Background
	void deletarConteudoEducacional(EducationalContent conteudoEducacional) {
		try {
			conteudoEducacionalRestClient.delete(conteudoEducacional.getId());

			super.iniciarLoading();
			carregarListView();

			mostrarToast(R.string.msg_sucesso_exclusao);
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();

		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}

	/* ÚTEIS (MENSAGENS) */

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);

		} else if(RESULT_CANCELED != resultCode){
			mostrarToast(R.string.msg_erro_rest);
		}
	}


}
