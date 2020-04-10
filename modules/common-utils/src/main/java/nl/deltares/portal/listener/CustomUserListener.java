package nl.deltares.portal.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        service = ModelListener.class
)
public class CustomUserListener extends BaseModelListener<User> {

    @Override
    public void onAfterRemove(User model) throws ModelListenerException {
        super.onAfterRemove(model);

        //TODO remove or anonymize user from custom tables
        System.out.println("User has been removed: " + model.getScreenName());
    }

}
