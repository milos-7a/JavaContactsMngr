package com.milos.contactsmanager.dao;
import com.milos.contactsmanager.exceptions.KontaktDAOException;
import com.milos.contactsmanager.model.*;
import java.util.*;
public interface KontaktDAO {
    List<Kontakt> vratiListuKontakta() throws KontaktDAOException;
    Kontakt vratiKontaktPoID(int id) throws KontaktDAOException;
    void dodajKontakt(Kontakt k) throws KontaktDAOException;
    void azurirajKontakt(Kontakt k) throws KontaktDAOException;
    void obrisiKontakt(int id) throws KontaktDAOException;
}
