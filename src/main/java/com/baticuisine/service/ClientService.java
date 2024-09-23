package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ClientDaoImpl;
import main.java.com.baticuisine.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientService {
    ClientDaoImpl clientDao;

    public ClientService(Connection connection) {
        this.clientDao = new ClientDaoImpl(connection);
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

    public List<Client> getAllClients() throws SQLException {
        return clientDao.rechercherTous();
    }

    public Client getClientByName(String name) {
        return clientDao.rechercherParNom(name);
    }

}
