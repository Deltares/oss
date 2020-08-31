package nl.deltares.forms.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.forms.constants.OssFormPortletKeys;

@ExtendedObjectClassDefinition(
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssFormPortletKeys.BUS_TRANSFER_FORM_CONFIGURATIONS_PID
)
public interface BusTransferFormConfiguration {

    @Meta.AD(required = false, deflt = "0")
    long eventId();
}
