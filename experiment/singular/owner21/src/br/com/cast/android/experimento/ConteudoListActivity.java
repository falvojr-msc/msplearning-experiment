package br.com.cast.android.experimento;

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

import android.widget.ListView;
import android.widget.Toast;
import br.com.cast.android.experimento.base.BaseActivity;
import br.com.cast.android.experimento.rest.ContentRestClient;
import br.com.cast.android.experimento.rest.entity.EducationalContent;
import br.com.cast.android.experimento.widget.ConteudoListAdapter;

@EActivity(R.layout.activity_educational_list)
@OptionsMenu(R.menu.educational_content_list)
public class ConteudoListActivity extends BaseActivity {

	/* LIST VIEW */

	@ViewById
	ListView listViewConteudos;

	@Bean
	ConteudoListAdapter listAdapter;

	@RestService
	ContentRestClient restClient;

	@AfterViews
	void tudoPronto() {
		super.registerForContextMenu(listViewConteudos);
		super.iniciarLoading();
		carregarListView();
	}

	@Background
	void carregarListView() {
		List<EducationalContent> itens = restClient.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(itens);
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir(){
		ConteudoActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.MS01);
	}

	@UiThread
	void carregarListView(List<EducationalContent> itens) {
		listAdapter.setUsuarios(itens);
		listViewConteudos.setAdapter(listAdapter);
		listAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		}else if(RESULT_CANCELED != resultCode){
			mostrarToast(R.string.msg_erro_rest);
		}
	}

}
