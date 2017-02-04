package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveTempHumPro")
public class GroveTempHumPro extends GroveTempHum {

    public GroveTempHumPro(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }
}
