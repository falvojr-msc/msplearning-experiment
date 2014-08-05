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
import br.com.cast.android.widget.EducationalListAdapter;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_content_list)
public class EducationalContentListActivity extends BaseActivity {

	/* LIST VIEW */

	@ViewById
	ListView listViewImagens;

	@Bean
	EducationalListAdapter educationalListAdapter;

	@RestService
	EducationalContentRestClient educationalRestClient;


	@AfterViews
	void CarregaPaginaInicial() {
		super.registerForContextMenu(listViewImagens);
		super.iniciarLoading();
		carregarListView();
	}
	@Background
	void carregarListView() {
		List<EducationalContent> educationals = educationalRestClient.findByOwner(EducationalContent.ID_OWNER);
		carregarListViewEducational(educationals);
	}


	@UiThread
	void carregarListViewEducational(List<EducationalContent> educationals) {
		educationalListAdapter.setEducacoes(educationals);
		listViewImagens.setAdapter(educationalListAdapter);
		educationalListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	/* INCLUIR */


	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		EducationalActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}


	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_inclusao_sucesso );
	}

	/* REFRESH */

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarListView();
	}

	/* EDITAR e EXCLUIR (CONTEXT MENU) */

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.contextual_educational_list , menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final EducationalContent educationalSelecionado = educationalListAdapter.getItem(info.position);

		// Identifica a ação selecionada:
		switch(item.getItemId()){
		case R.id.action_editar:
			Intent intent = EducationalActivity_.intent(this).get();
			intent.putExtra(EducationalActivity.CHAVE_EDUCATIONAL, educationalSelecionado);
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletar(educationalSelecionado);
				}
			};
			// Exibe um alerta de confirmação usando o listener criado para o botão "Sim".
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(getString(R.string.msg_dialog_confirmacao_exclusao))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}

	@Background
	void deletar(EducationalContent educational) {
		try {
			educationalRestClient.delete(educational.getId());

			super.iniciarLoading();
			carregarListView();
			mostrarToast(R.string.msg_exclusao_sucesso);
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	/* ÚTEIS (MENSAGENS) */

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		}else if(RESULT_CANCELED != resultCode ){
			mostrarToast(R.string.msg_erro_rest);
		}
	}


}
