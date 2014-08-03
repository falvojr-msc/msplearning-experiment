package br.com.cast.android.aula3.base;

import java.lang.reflect.Field;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import br.com.cast.android.aula3.R;

/**
 * Classe base com caraterísticas úteis às nossas Activities.<br>
 * Quando a herdamos temos acesso a uma {@link ProgressDialog} e as constantes REQUESTCODE_INCLUIR e REQUESTCODE_EDITAR,
 * utilizadas para startar identificar o resultado de uma Activity iniciada com o método startActivityForResult.
 * 
 * @author venilton.junior
 */
@EActivity
public abstract class BaseActivity extends ActionBarActivity {

	protected static final int REQUESTCODE_INCLUIR = 1;
	protected static final int REQUESTCODE_EDITAR = 2;

	private ProgressDialog progressDialog;

	protected void iniciarLoading() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage(getString(R.string.progress_aguarde));
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
	}

	protected void terminarLoading() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	/**
	 * Adaptação técnica para um bug (java.io.EOFException) já conhecido pelo Spring e Google HTTP Client.
	 * 
	 * @see <a href="http://sapandiwakar.in/eofexception-with-spring-rest-template-android/">Sapan Diwakar</a>
	 */
	@AfterInject
	protected void corrigirRequestFactory() {
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			Object propriedade;
			try {
				field.setAccessible(true);
				propriedade = field.get(this);
				if (propriedade instanceof RestClientSupport) {
					((RestClientSupport) propriedade).getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
