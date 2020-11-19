package nl.deltares.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author rooij_e
 */
@Component(
		immediate = true,
		property = { "key=logout.events.post" },
		service = LifecycleAction.class
)

public class PostLogoutAction implements LifecycleAction {
	@Reference
	private DsdParserUtils dsdParserUtils;

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {

		//Clear cached registrations after logout
		dsdParserUtils.clearCache();
	}


}