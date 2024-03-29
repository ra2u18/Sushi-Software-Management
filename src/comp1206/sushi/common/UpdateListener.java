package comp1206.sushi.common;

/**
 * An update listener using the observer pattern used to stay informed of model updates.
 * This is primarily used by the user interface to be notified when the backend model
 * has ben updated and to update the UI.
 * The listener will be called of details of the model (where applicable) and properties (where applicable) that have been updated.
 *
 * */

public interface UpdateListener {

    /**
     * Receive a notification of the model update with the given UpdateEvent containing
     * @param updateEvent information on the update
     * */
    void updated(UpdateEvent updateEvent);

}
