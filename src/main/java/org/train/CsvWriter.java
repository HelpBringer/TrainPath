package org.train;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CsvWriter {
    final String DESCRIPTION_HEADER = "DEPART.STATION;ARRIVAL.STATION;DEPART.TIME;ARRIVAL.TIME;DURATION, MIN;PRICE, USD;COACH_TYPE;FARE_TYPE";

    public void write(String csvPath, ArrayList<TrainPath> trains) {
        String filename;
        filename = Objects.requireNonNullElse(csvPath, "data.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            if(trains != null && !trains.isEmpty()) {

                writer.write(DESCRIPTION_HEADER);
                writer.newLine();
                for (TrainPath train : trains) {
                    writer.write(train.toString());
                    writer.newLine();
                }
            }
            else{
                writer.write("NO TRAINS FOUND");
            }
            //System.out.println("Результат записан в " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file");

        }
    }
    public void writeError(String csvPath, String errorMessage) {
        String filename;
        filename = Objects.requireNonNullElse(csvPath, "data.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(errorMessage);
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}

