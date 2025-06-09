package org.train;

public class TrainPath {
    private String departureStation;
    private String arrivalStation;

    private String departureTime;
    private String arrivalTime;
    private int duration;

    private int price;
    private String coachType;
    private String fareType;

    public TrainPath() {}

    public TrainPath(TrainPath trainPath) {
        this.departureStation = trainPath.departureStation;
        this.arrivalStation = trainPath.arrivalStation;
        this.departureTime = trainPath.departureTime;
        this.arrivalTime = trainPath.arrivalTime;
        this.duration = trainPath.duration;
        this.price = trainPath.price;
        this.coachType = trainPath.coachType;
        this.fareType = trainPath.fareType;

    }

    @Override
    public String toString() {
        String DEFAULT_SEPARATOR = ";";
        return
                departureStation + DEFAULT_SEPARATOR +
                arrivalStation + DEFAULT_SEPARATOR +
                departureTime + DEFAULT_SEPARATOR +
                arrivalTime + DEFAULT_SEPARATOR +
                duration/60 + DEFAULT_SEPARATOR +
                price + DEFAULT_SEPARATOR +
                coachType + DEFAULT_SEPARATOR +
                fareType;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

}

