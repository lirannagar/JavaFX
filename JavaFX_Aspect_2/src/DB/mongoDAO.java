package DB;

import java.util.Arrays;
import java.util.Iterator;

import org.bson.Document;

import Listeners.DBactions;
import Objects.DestructedMissile;
import Objects.Launcher;
import Objects.LauncherDestructor;
import Objects.Missile;
import Objects.MissileDestructor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DocumentToDBRefTransformer;

public class mongoDAO implements DBactions {
	private static mongoDAO theMongoDAO; //Singleton Design Pattern
	private MongoClient mongo;
	private MongoDatabase DataBase;
	
	private mongoDAO() {
		mongo = new MongoClient("localhost", 27017);
		DataBase = mongo.getDatabase("JavaFX_Part2");
		System.out.println("Connected to MongoDB " + DataBase.getName());
		deleteMissileLaunchers();
		deleteMissileDestructors();
	}

	@Override
	public void saveLauncherDetails(Launcher launcher) {
		// TODO Auto-generated method stub
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Missile Launchers");
			System.out.println("Mongo Collection is created");
			Document doc = new Document("ID", launcher.getLId()).append("isHidden", launcher.isHidden());
			collection.insertOne(doc);
			System.out.println("Document is inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveMissileDestructorDetails(MissileDestructor missileDestructor) {
		// TODO Auto-generated method stub
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Missile Destructors");
			System.out.println("Mongo Collection for Missile Destructors is created");
			Document doc = new Document("ID", missileDestructor.getId()).append("isRunning", missileDestructor.isRunning());
			collection.insertOne(doc);
			System.out.println("Document is inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveLauncherDestructorDetails(LauncherDestructor ld) {
		// TODO Auto-generated method stub
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Missile Launcher Destructors");
			System.out.println("Mongo Collection for Missile Launcher Destructors is created");
			Document doc = new Document("ID", ld.getId()).append("isRunning", ld.isRunning()).append("Type", ld.getType());
			collection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveLaunchEvents(Missile m) {
		// TODO Auto-generated method stub
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Launch Events");
			Document doc = new Document("Launcher ID", m.getLauncherId()).append("Missile ID", m.getMId()).append("Destination", m.getDestination()).append("Launch Time", m.getLaunchTime()).append("Fly Time", m.getFlyTime()).append("Damage", m.getDamage());
			collection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	private void deleteMissileLaunchers() {
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Missile Launchers");
			collection.drop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteMissileDestructors() {
		try {
			MongoCollection<Document> collection = DataBase.getCollection("Missile Destructors");
			collection.drop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static mongoDAO getInstance() {
		if (theMongoDAO == null) {
			theMongoDAO = new mongoDAO();
		}
		return theMongoDAO;
	}

	@Override
	public void saveInterceptions(String mdID, DestructedMissile dm) {
		// TODO Auto-generated method stub
		try {
		MongoCollection<Document> collection = DataBase.getCollection("Interceptions");
		System.out.println("Mongo Collection for Interceptions is created");
		Document doc = new Document("MissileDestructor ID", mdID).append("Missile ID", dm.getTargetId()).append("Time Of Interception", dm.getDestructAfterLaunch());
		collection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
