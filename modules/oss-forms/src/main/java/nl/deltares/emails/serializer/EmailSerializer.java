package nl.deltares.emails.serializer;

public interface EmailSerializer<C> {

    /**
     *
     * @throws Exception: It is allowed to throw any exception.
     */
    void serialize(C content, StringBuilder writer) throws Exception;



}
