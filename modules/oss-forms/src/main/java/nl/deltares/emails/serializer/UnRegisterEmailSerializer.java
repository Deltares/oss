package nl.deltares.emails.serializer;

import nl.deltares.emails.RegistrationEmail;

public class UnRegisterEmailSerializer extends AbsRegisterEmailSerializer {

    @Override
    String getEventTitle() {
        return "dsd.email.unregister.event";
    }

    @Override
    void appendNotice(StringBuilder writer, RegistrationEmail content) {

    }
}
