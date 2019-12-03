package com.nujitha.project.firebaseUtils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.nujitha.project.classes.Car;
import com.nujitha.project.classes.Motorbike;
import com.nujitha.project.classes.Schedule;
import com.nujitha.project.classes.Vehicle;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service //serving to the API requests
public class FirebaseUtil {
    private static Firestore firestore; //instance of firestore

    //initializing firebase SDK
    public static void setupFirebase() throws IOException {
        InputStream serviceAccount = new FileInputStream("./year02oopcw01-firebase-adminsdk-6nsyw-39361258d8.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        firestore = FirestoreClient.getFirestore();

    }

    /*
     * Database CRUD operations
     */

    //retrieving all vehicle objects
    public static List getToArray() throws ExecutionException, InterruptedException {
        List<Vehicle> collection = new ArrayList<>();
        Query query = firestore.collection("vehicles");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        //iterating through the the collection and adding to a list to return
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            if (Objects.requireNonNull(document.getString("vehicleType")).equalsIgnoreCase("Car")) {
                collection.add(document.toObject(Car.class)); //casting and adding a car object
            } else {
                collection.add(document.toObject(Motorbike.class)); //casting and adding a motorbike object
            }
        }
        return collection;
    }

    //deleting a vehicle from the database
    public static void deleteFirestore(String pNum) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("vehicles").document(String.valueOf(pNum));
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            firestore.collection("vehicles").document(String.valueOf(pNum)).delete();
        } else {
            System.out.println();
        }
    }

    //adding a vehicle object
    public static void addVehicle(Vehicle uObject) {
        firestore.collection("vehicles").document(String.valueOf(uObject.getPlateNo())).set(uObject);
    }

    //retrieving only the reference numbers of bookings
    public static List bookingReferences() throws ExecutionException, InterruptedException {
        List<Object> bookedVeh = new ArrayList<>();
        Query query = firestore.collection("bookings");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            bookedVeh.add(document.getId());
        }
        return bookedVeh;
    }

    //retrieving schedules
    public static List bookingObjects() throws ExecutionException, InterruptedException {
        List<Object> objectList = new ArrayList<>();
        Query query = firestore.collection("bookings");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            objectList.add(document.toObject(Schedule.class));
        }
        return objectList;
    }

    //adding a schedule and updating vehicle's availability
    public static void addBooking(Schedule schedule) {
        firestore.collection("bookings").document().set(schedule);
        firestore.collection("vehicles").document(schedule.getPlateNo()).update("availability", false);
    }

    //deleting a schedule from the database and updating the vehicle availability
    public static void deleteBooking(String refNo, String updatePlateNo) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("bookings").document(String.valueOf(refNo));
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            firestore.collection("bookings").document(String.valueOf(refNo)).delete();
            firestore.collection("vehicles").document(updatePlateNo).update("availability", true);
        } else {
            System.out.println();
        }
    }

}
