package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.NodeResource;


public class GroveBarometer extends Grove {
    public GroveBarometer(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }
}
