/**
 * An interface representing any class whose objects get notified when
 * the objects they are observing update themselves.
 *
 * @param <Subject> the type of object an implementor of this interface
 *                is observing
 * @param <ClientData> optional data the model can send to the observer
 *                    (null if nothing)
 *
 * @author Travis Brown
 */
public interface Observer<Subject, ClientData> {
    /**
     * The observed subject calls this method on each observer that has
     * previously registered with it. This version of the design pattern
     * follows the "push model" in that the subject can provide
     * ClientData to inform the observer about what exactly has happened.
     * But this convention is not required. It may still be necessary for
     * the observer to also query the subject to find out more about what has
     * happened. If this is a simple signal with no data attached,
     * or if it can safely be assumed that the observer already has a
     * reference to the subject, even the subject parameter may be null.
     * But as always this would have to be agreed to by designers of both sides.
     *
     * @param subject the object that wishes to inform this object
     *                about something that has happened.
     * @param data optional data the server.model can send to the observer
     *
     * @see <a href="https://sourcemaking.com/design_patterns/observer">the
     * Observer design pattern</a>
     */
    void update(Subject subject, ClientData data);
}
