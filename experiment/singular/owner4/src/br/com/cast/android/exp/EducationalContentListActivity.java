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
import br.com.cast.android.exp.widget.EducationalContentListAdapter;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_content_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView listViewImagens;

	@Bean
	EducationalContentListAdapter educationalContentListAdapter;

	@RestService
	EducationalContentRestClient educationalContentRestClient;

	@AfterViews
	void tudoPronto() {
		super.registerForContextMenu(listViewImagens);
		atualizarListView();
	}

	@Background
	void carregarListView() {
		List<EducationalContent> registros = educationalContentRestClient.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(registros);
	}

	@UiThread
	void carregarListView(List<EducationalContent> disciplinas) {
		educationalContentListAdapter.setDisciplinas(disciplinas);
		listViewImagens.setAdapter(educationalContentListAdapter);
		educationalContentListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		atualizarListView();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		EducationalContentActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		atualizarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_ms01);
	}

	/* EDITAR e EXCLUIR (CONTEXT MENU) */

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.educational_content_list_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Recupera o item selecionado:
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final EducationalContent registroSelecionado = educationalContentListAdapter.getItem(info.position);

		// Identifica a ação selecionada:
		switch(item.getItemId()){
		case R.id.action_editar:
			// Prepara uma intenção para a UserActivity com o usuário selecionado:
			Intent intent = EducationalContentActivity_.intent(this).get();
			intent.putExtra(EducationalContentActivity.CHAVE_REGISTRO, registroSelecionado);
			// Redireciona para a UserActivity esperando um resultado identificado pela chave REQUESTCODE_EDITAR:
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			// Cria um listener para a resposta positiva (Sim) na confirmação de exclusão:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletarRegistro(registroSelecionado);
				}
			};
			// Exibe um alerta de confirmação usando o listener criado para o botão "Sim".
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(String.format(getString(R.string.msg_mc01), registroSelecionado.getTitle()))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		atualizarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_ms02);
	}

	@Background
	void deletarRegistro(EducationalContent registro) {
		try {
			educationalContentRestClient.delete(registro.getId());

			super.iniciarLoading();
			carregarListView();

			mostrarToast(R.string.msg_ms03);
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	/* ÚTEIS (Auxiliares) */
	private void atualizarListView() {
		super.iniciarLoading();
		carregarListView();
	}


	/* ÚTEIS (MENSAGENS) */

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		} else if(RESULT_CANCELED != resultCode)
		{
			mostrarToast(R.string.msg_erro_rest);
		}
	}
}
