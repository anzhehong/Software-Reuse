package reuse.utility;

import com.google.common.eventbus.EventBus;

/**
 * Created by fowafolo
 * Date: 16/3/27
 * Time: 21:44
 */
public class EventController {
    /**
     * 新建一个EventBus
     */
    public static EventBus eventBus = new EventBus("controller");
}
