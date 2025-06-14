package org.train;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Main {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final String apiKey = "4ae3369b0952f1c1176deec94708f3a7";
    static String cityId_endpoint = "https://back.rail.ninja/api/v2/station/search-option?name=";
    static String trains_endpoint = "https://back.rail.ninja/api/v2/timetable?frontendSearchRetry=1";
    static String body_request = "{ \"legs\": {\"1\": {\"arrival_station\": \"%s\", \"departure_date\": \"%s\", \"departure_station\": \"%s\"}}," +
                                " \"passengers\": {\"adults\": \"%s\", \"children\": 0, \"children_age\": []}}";

    public static String findCityId(String city) {
        JsonExtractor jsonExtractor = new JsonExtractor();
        HttpResponse<String> response;
        String cityId = null;
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(cityId_endpoint + city))
                    .header("X-API-User-Key", apiKey)
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            cityId = jsonExtractor.parseCityJson(response.body());
            //System.out.println(cityId);
        } catch (IOException | InterruptedException e) {
            new CsvWriter().writeError(null, "ERROR IN FINDING CITY ID: " + e.getMessage());
            System.exit(0);
        }
        return cityId;
    }

    public static ArrayList<TrainPath> findTrains(String requestBody) {
        ArrayList<TrainPath> trains = new ArrayList<>();
        try (HttpClient client = HttpClient.newHttpClient();) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(trains_endpoint))
                    .header("X-Currency", "USD")
                    .header("X-API-User-Key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonExtractor jsonExtractor = new JsonExtractor();
            trains = jsonExtractor.parseTrainsJson(response.body());
        } catch (Exception e) {
            new CsvWriter().writeError(null, "ERROR IN FINDING TRAINS: " + e.getMessage());
            System.exit(0);
        }
            return trains;

    }
    public static void main(String[] args) {

        if(args.length<4){
            new CsvWriter().writeError(null, "NOT ENOUGH CRITERIA PROVIDED, REQUIRED: start city, end city, date and 1 passenger");
            System.exit(0);
        }
        String startCity = args[0].toLowerCase();
        String endCity = args[1].toLowerCase();
        LocalDate trainDate = LocalDate.parse(args[2], dateFormatter);
        String passengers = args[3];

        //System.out.println(startCity + " " + endCity + " " + trainDate + " " + passengers);

        //in our case we need to run a GET request for a city id twice
        //then create a POST request to the /api/v2/log/train_selected using the city ids
        String startCityId = findCityId(startCity);
        String endCityId = findCityId(endCity);
        String requestBody = String.format(body_request, endCityId , trainDate, startCityId, passengers);

        ArrayList<TrainPath> trains = findTrains(requestBody);

        CsvWriter csvWriter = new CsvWriter();
        csvWriter.write(null, trains);

    }
}