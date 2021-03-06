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
import br.com.cast.android.exp.cont.EducationalContentListAdapter;
import br.com.cast.android.exp.rest.EducationalContentRestClient;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_content_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView listViewEduContent;

	@Bean
	EducationalContentListAdapter contentListAdapter;

	@RestService
	EducationalContentRestClient contentRestClient;

	@AfterViews
	void tudoPronto() {
		super.registerForContextMenu(listViewEduContent);
		super.iniciarLoading();
		carregarListView();
	}

	@Background
	void carregarListView() {
		List<EducationalContent> contents = contentRestClient
				.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(contents);
	}

	@UiThread
	void carregarListView(List<EducationalContent> contents) {
		contentListAdapter.setContents(contents);
		listViewEduContent.setAdapter(contentListAdapter);
		contentListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		EducationalContentActivity_.intent(this).startForResult(
				REQUESTCODE_INCLUIR);
	}
	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.ms01);
	}

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarListView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.contextual_educational_content,
				menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final EducationalContent contentSelecionado = contentListAdapter
				.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.action_editar:
			Intent intent = EducationalContentActivity_.intent(this).get();
			intent.putExtra(EducationalContentActivity.CHAVE_USUARIO,
					contentSelecionado);
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletarContent(contentSelecionado);
				}
			};

			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(getString(R.string.mc01)).show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();

		mostrarToastPorResultCode(resultCode, R.string.ms02);
	}

	@Background
	void deletarContent(EducationalContent usuario) {
		try {
			contentRestClient.delete(usuario.getId());

			super.iniciarLoading();
			carregarListView();

			mostrarToast(R.string.ms03);
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	/* ÚTEIS (MENSAGENS) */

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros),
				Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk,
			Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		} else if (RESULT_CANCELED != resultCode) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}
}
