package trains;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class TrainData
{
    private File file;
    private Trains trains;
    private JAXBContext jaxbContext;

    private final int year;
    private final int[] monthDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


    public TrainData(String filePath) throws JAXBException
    {
        this.file = new File(filePath);
        this.trains = new Trains();
        this.jaxbContext = JAXBContext.newInstance(Trains.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.year = calendar.get(Calendar.YEAR);
    }


    private boolean isValidDate(String date)
    {
        boolean result = true;
        StringTokenizer dateTokenizer = new StringTokenizer(date, ".");

        if (dateTokenizer.countTokens() != 3)
            result = false;

        if (result) {
            int[] dateArray = new int[3];

            for (int i = 0; i < 3; i++)
                dateArray[i] = Integer.parseInt(dateTokenizer.nextToken());

            if (dateArray[2] != year) { // проверяем год
                System.out.println("Error! Given date is " + date +
                        ". But it must contain only the current " + year + " year!\n");
                return false;
            }

            if (dateArray[1] < 1 || dateArray[1] > 12) // проверяем месяц
                result = false;
            else if (dateArray[0] < 1 || dateArray[0] > monthDays[dateArray[1]]) // проверяем день
                result = false;
        }

        if (!result)
            System.out.println("Error! " + date + " is an invalid date!\n");

        return result;
    }


    private int[] getTimeArray (String time)
    {
        int[] timeArray = new int[2];
        StringTokenizer timeTokenizer = new StringTokenizer(time, ":");

        if (timeTokenizer.countTokens() != 2)
            timeArray = null;

        if (timeArray != null) {
            for (int i = 0; i < 2; i++)
                timeArray[i] = Integer.parseInt(timeTokenizer.nextToken());

            if (timeArray[0] < 0 || timeArray[0] > 23) // проверяем часы
                timeArray = null;
            else if (timeArray[1] < 0 || timeArray[1] > 59) // проверяем минуты
                timeArray = null;
        }

        if (timeArray == null)
            System.out.println("Error! " + time + " is an invalid time!\n");

        return timeArray;
    }


    public void getTrainList() throws JAXBException
    {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        trains = (Trains) unmarshaller.unmarshal(file);
    }


    public void printFile() throws IOException
    {
        System.out.println("\n\n" + new String(Files.readAllBytes(file.toPath())));
    }


    public void printSelectedTrains(String date, String beginTime, String endTime)
    {
        if (!isValidDate(date))
            return;

        int[] beginTimeArray = getTimeArray(beginTime);
        int[] endTimeArray   = getTimeArray(endTime);

        if (beginTimeArray == null || endTimeArray == null)
            return;

        System.out.println("\nSELECTED TRAINS ("+ date +
                " from " + beginTime + " to " + endTime + "):\n");

        List<Train> trainList = trains.getTrains();

        if (trainList.isEmpty()) {
            System.out.println("The train list is empty!");
            return;
        }

        int count = 0;

        for (Train train : trainList) {
            if (!isValidDate(train.getDate()) || !train.getDate().equals(date))
                continue;

            int[] timeArray = getTimeArray(train.getTime());

            if (timeArray == null)
                continue;
            else if (timeArray[0] < beginTimeArray[0])
                continue;
            else if (timeArray[0] == beginTimeArray[0] && timeArray[1] < beginTimeArray[1])
                continue;
            else if (timeArray[0] > endTimeArray[0])
                continue;
            else if (timeArray[0] == endTimeArray[0] && timeArray[1] > endTimeArray[1])
                continue;

            System.out.println(++count + ".");
            System.out.println(train);
        }

        if (count == 0)
            System.out.println("No trains.");
    }


    public void addNewTrain(String from, String to,
                            String date, String departure) throws JAXBException, ParseException
    {
        System.out.println("\nAdding a new train (" + from + "-" + to + ", " +
                date + " " + departure + ")...");

        if (!isValidDate(date)) {
            return;
        }
        else if (getTimeArray(departure) == null) {
            return;
        }

        for (Train train : trains.getTrains()) {
            if (from.equals(train.getFrom()) && to.equals(train.getTo())
                    && date.equals(train.getDate()) && departure.equals(train.getTime())) {
                System.out.println("This train is already exist!");
                return;
            }
        }

        trains.add(new Train(from, to, date, departure));

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(trains, file);
        System.out.println("Ok!");
    }
}
