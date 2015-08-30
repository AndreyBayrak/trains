package trains;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "trains")
public class Trains
{
    @XmlElement(name = "train")
    private List<Train> trains = new ArrayList<Train>();

    public void add(Train train) { trains.add(train); }

    public List<Train> getTrains() { return trains; }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (Train train : trains)
            stringBuilder.append(train.toString());

        return stringBuilder.toString();
    }
}
