package main.java.com.baticuisine.dao;

import main.java.com.baticuisine.model.Client;

import java.util.List;

public interface ClientDao {
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(int id);
    Client getClient(int id);
    List<Client> getClients();
}
