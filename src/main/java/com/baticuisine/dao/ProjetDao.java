package main.java.com.baticuisine.dao;

import main.java.com.baticuisine.model.Projet;

import java.util.List;

public interface ProjetDao {
     int addProject(Projet projet);
     void updateProject(Projet projet,int id);
     void deleteProject(int id);
     Projet getProject(int id);
     List<Projet> getAllProjects();
}
