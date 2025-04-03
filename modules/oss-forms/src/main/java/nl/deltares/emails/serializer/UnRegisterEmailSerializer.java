package nl.deltares.emails.serializer;

public class UnRegisterEmailSerializer extends AbsRegisterEmailSerializer {

    @Override
    String getEventTitle() {
        return "dsd.email.unregister.event";
    }

}
