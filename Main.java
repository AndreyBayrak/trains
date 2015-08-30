package trains;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;

// Есть список поездов, представленный с виде XML.
// Вывести на экран информацию о тех поездах, которые отправляются сегодня с 15:00 до 19:00.
// Написать код для добавления новых поездов в существующий XML.
public class Main
{
    public static void main(String[] args) throws JAXBException, ParseException, IOException
    {
        final String filePath = "/home/andrey/trains.xml";

        final String date      = "29.08.2015";
        final String beginTime = "15:00";
        final String endTime   = "19:00";

        TrainData trainData = new TrainData(filePath);
        // выводим содержимое файла
        trainData.printFile();
        // получаем список поездов
        trainData.getTrainList();
        // выводим выбранные поезда
        trainData.printSelectedTrains(date, beginTime, endTime);
        // добавляем новый поезд в список
        trainData.addNewTrain("Kyiv", "Kherson", "29.08.2015", "18:00");
        // выводим содержимое файла
        trainData.printFile();
    }
}
