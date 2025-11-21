package com.milos.contactsmanager.dao;
import com.milos.contactsmanager.exceptions.DetaljiDAOException;
import com.milos.contactsmanager.model.KontaktDetalji;
import java.util.*;

public interface DetaljiDAO {
    List<KontaktDetalji> uzmiDetaljePoKontaktID(int kontaktId) throws DetaljiDAOException;
    void dodajDetalj(KontaktDetalji detalj) throws DetaljiDAOException;
    boolean obrisiDetalj(int detaljId, int kontaktId) throws DetaljiDAOException;
    void obrisiSveDetaljeZaKontakt(int kontaktId) throws DetaljiDAOException;
}
