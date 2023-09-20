package nl.deltares.portal.configuration;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
		property = "model.class.name=" + OssConstants.STRUCTURE_KEY_MAP_CONFIGURATIONS_PID,
		service = ConfigurationModelListener.class
)
public class StructureKeyMapConfigurationListener implements ConfigurationModelListener {

	@Reference
	DsdParserUtils dsdParserUtils;

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) throws ConfigurationModelListenerException {
		final Long groupId = (Long) properties.get("groupId");
		dsdParserUtils.clearConfigCache(groupId, "structureKeyMap");
		ConfigurationModelListener.super.onAfterSave(pid, properties);
	}
}