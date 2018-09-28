package Listeners;

import java.util.ArrayList;

import Objects.DestructedMissile;
import Objects.Launcher;
import Objects.LauncherDestructor;
import Objects.Missile;
import Objects.MissileDestructor;

public interface DBactions {

	public void saveLauncherDetails(Launcher laucnher);
	
	public void saveMissileDestructorDetails(MissileDestructor missileDestructor);
	
	public void saveLauncherDestructorDetails(LauncherDestructor ld);

	public void saveLaunchEvents(Missile m);

	//public void saveInterceptions(String mdID, Missile m);
	
	public void saveInterceptions(String mdID, DestructedMissile m);

}
