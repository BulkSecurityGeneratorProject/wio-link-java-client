package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveTempHumPro")
public class GroveTempHumPro extends Grove {

    public GroveTempHumPro(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Double readHumidity() throws WioException {
        return toDouble(readSimpleProperty("humidity"));
    }

    public Double readTemperature() throws WioException {
        return toDouble(readSimpleProperty("temperature", "celsius_degree"));
    }

    public Double readTemperatureInFahrenheit() throws WioException {
        return toDouble(readSimpleProperty("temperature_f", "fahrenheit_degree"));
    }
}
