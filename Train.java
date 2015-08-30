package trains;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;


@XmlRootElement(name = "train")
public class Train
{
    private String from;
    private String to;
    private String date;
    private String departure;

    public Train(){}

    public Train(String from, String to, String date, String departure) throws ParseException
    {
        this.from      = from;
        this.to        = to;
        this.date      = date;
        this.departure = departure;
    }

    @XmlElement(name = "from")
    public void   setFrom(String from) { this.from = from; }
    public String getFrom() { return from; }

    @XmlElement(name = "to")
    public void   setTo(String to) { this.to = to; }
    public String getTo() { return to; }

    @XmlElement(name = "date")
    public void   setDate(String date) { this.date = date; }
    public String getDate() { return date; }

    @XmlElement(name = "departure")
    public void   setTime(String departure) { this.departure = departure; }
    public String getTime() { return departure; }

    @Override
    public String toString()
    {
        return  "From:      " + from       + "\n" +
                "To:        " + to         + "\n" +
                "Date:      " + date       + "\n" +
                "Departure: " + departure  + "\n";
    }
}
