package org.train;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Map;


public class JsonExtractor {

    public String parseCityJson(String jsonString) {
        Gson gson = new Gson();
        JsonArray root = gson.fromJson(jsonString, JsonArray.class);
        if(!root.isEmpty()) {
            JsonObject city = root.get(0).getAsJsonObject();
            return city.get("value").getAsString();
        }
        else {
            return null;
        }
    }

    public ArrayList<TrainPath> parseTrainsJson(String jsonString) {
        ArrayList<TrainPath> result = new ArrayList<>();
        Gson gson = new Gson();

        TrainPath trainPath = new TrainPath();


        JsonObject root = gson.fromJson(jsonString, JsonObject.class);

        JsonObject trainsObj = root.getAsJsonObject("trains");
        if (trainsObj == null) {
            System.out.println("no trains found");
            return new ArrayList<>();
        }

        for (Map.Entry<String, JsonElement> entry : trainsObj.entrySet()) {
            JsonObject trainData = entry.getValue().getAsJsonObject();

            JsonObject departureStation = trainData.getAsJsonObject("departure_station");
            if (departureStation != null) {

                trainPath.setDepartureStation(departureStation.get("single_name").getAsString());

            }

            JsonObject arrivalStation = trainData.getAsJsonObject("arrival_station");
            if (arrivalStation != null) {

                trainPath.setArrivalStation(arrivalStation.get("single_name").getAsString());
            }

            trainPath.setDepartureTime(trainData.get("departure_datetime").getAsString());
            trainPath.setArrivalTime(trainData.get("arrival_datetime").getAsString());
            trainPath.setDuration(trainData.get("running_time").getAsInt());


            JsonArray coachClasses = trainData.getAsJsonArray("coach_classes");
            for (JsonElement coach_type : coachClasses) {
                if (coach_type.isJsonObject()) {

                    JsonObject coach = coach_type.getAsJsonObject();

                    JsonObject coach_class = coach.getAsJsonObject("coach_class");
                    if (coach_class != null) {

                        trainPath.setCoachType(coach_class.get("name").getAsString());
                    }

                    JsonArray fares = coach.getAsJsonArray("fares");
                    for (JsonElement fare_type : fares) {
                        if (fare_type.isJsonObject()) {
                            JsonObject fare_id = fare_type.getAsJsonObject();

                            JsonObject fare = fare_id.getAsJsonObject("fare");
                            if (fare != null) {

                                trainPath.setFareType(fare.get("name").getAsString());
                            }

                            JsonObject price = fare_id.getAsJsonObject("total_price");
                            if (price != null) {

                                trainPath.setPrice(price.get("number").getAsInt());
                            }
                            result.add(new TrainPath(trainPath));
                            //System.out.println(trainPath);
                        }
                    }
                }
            }
            //System.out.println("------------------------------");
        }
        return result;
    }
}