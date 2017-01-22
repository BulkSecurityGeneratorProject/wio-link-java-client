package nl.openweb.iot.wio.scheduling;

import java.util.Map;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;

interface TaskEventHandler {

    void handle(Map<String, String> map, Node node, TaskContext context) throws WioException;
}
