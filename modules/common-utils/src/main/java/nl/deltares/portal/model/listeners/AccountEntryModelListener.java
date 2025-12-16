package nl.deltares.portal.model.listeners;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import org.osgi.service.component.annotations.Component;

@Component(service = ModelListener.class)
public class AccountEntryModelListener extends BaseModelListener<AccountEntry> {

    @Override
    public void onBeforeUpdate(AccountEntry originalModel, AccountEntry model) throws ModelListenerException {

        String domains = originalModel.getDomains();
        model.setDomains(domains);

        super.onBeforeUpdate(originalModel, model);
    }

}
