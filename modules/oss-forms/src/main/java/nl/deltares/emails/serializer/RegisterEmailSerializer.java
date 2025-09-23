package nl.deltares.emails.serializer;

public class RegisterEmailSerializer extends AbsRegisterEmailSerializer {

    @Override
    String getEventTitle() {
        return "dsd.email.register.event";
    }

}
