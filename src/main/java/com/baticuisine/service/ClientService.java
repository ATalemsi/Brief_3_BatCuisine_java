package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ClientDaoImpl;
import main.java.com.baticuisine.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ClientService {
    ClientDaoImpl clientDao;
    private HashMap<String, Client> clientMap;


    public ClientService(Connection connection) {
        clientMap = new HashMap<>();
        this.clientDao = new ClientDaoImpl(connection);
        loadClientsIntoMap();
    }

    private void loadClientsIntoMap() {
        List<Client> clients = clientDao.rechercherTous(); // Fetch all clients from the database
        for (Client client : clients) {
            clientMap.put(client.getNom().toLowerCase(), client); // Use lower case for case-insensitive searching
        }
    }

    public void addClient(Client client) {
        clientDao.add(client);
    }

    public void updateClient(Client client, int id) {
        clientDao.update(client, id);
    }

    public void deleteClient(int id) {
        clientDao.delete(id);
    }

    public Client getClient(int id) throws SQLException {
        return clientDao.rechercherParId(id);
    }

    public List<Client> getAllClients(){
        return clientDao.rechercherTous();
    }

    public Client getClientByName(String nomClient) {
        return clientMap.get(nomClient.toLowerCase()); // Use lower case for case-insensitive search
    }

}
