package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GenericDOut")
public class GroveGenericDigitalOut extends Grove {

    public GroveGenericDigitalOut(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public boolean readOnOffStatus() throws WioException {
        return "1".equals(readProperty("onoff_status", "onoff"));
    }

    public boolean switchOnOff(boolean onOff) throws WioException {
        return writeProperty("onoff", onOff ? "1" : "0");
    }

    public boolean highPulse(int milliseconds) throws WioException {
        return writeProperty("high_pulse", Integer.toString(milliseconds));
    }

    public boolean lowPulse(int milliseconds) throws WioException {
        return writeProperty("low_pulse", Integer.toString(milliseconds));
    }
}
