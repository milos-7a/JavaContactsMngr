package com.milos.contactsmanager.service;
import com.milos.contactsmanager.dao.*;
import com.milos.contactsmanager.exceptions.*;
import com.milos.contactsmanager.model.Kontakt;

import java.util.ArrayList;
import java.util.List;

public class KontaktService {
    private final KontaktDAO kontaktDAO;
    private final DetaljiDAO detaljiDAO;

    public KontaktService() {
        this.kontaktDAO = new KontaktDAOImpl();
        this.detaljiDAO = new DetaljiDAOImpl();
    }

    public void dodajKontakt(Kontakt kontakt) throws KontaktServisException {
        if (kontakt.getIme() == null || kontakt.getIme().isBlank()) {
            throw new KontaktServisException("Kontakt mora imati ime.");
        }
        try {
            kontaktDAO.dodajKontakt(kontakt);
        } catch (KontaktDAOException e) {
            throw new KontaktServisException("Ne mogu da dodam kontakt: ", e);
        }
    }

    public void obrisiKontakt(int id) throws KontaktServisException {
        try {
            Kontakt kontakt = kontaktDAO.vratiKontaktPoID(id);
            if (kontakt == null) {
                throw new KontaktServisException("Kontakt sa ID " + id + " ne postoji.");
            }
            if (!detaljiDAO.uzmiDetaljePoKontaktID(id).isEmpty()) detaljiDAO.obrisiSveDetaljeZaKontakt(id);
            kontaktDAO.obrisiKontakt(id);
        } catch (KontaktDAOException | DetaljiDAOException e){
            throw new KontaktServisException("Ne mogu da obrisem kontakt ID=" + id, e);
        }
    }

    public void azurirajKontakt(Kontakt kontakt) throws KontaktServisException {
        try {
            kontaktDAO.azurirajKontakt(kontakt);
        } catch (KontaktDAOException e){
            throw new KontaktServisException("Ne mogu da azuriram kontakt ID=" + kontakt.getId(), e);
        }
    }

    public List<Kontakt> vratiSveKontakte() throws KontaktServisException {
        try {
            List<Kontakt> kontakti = kontaktDAO.vratiListuKontakta();
            for (Kontakt k : kontakti) {
                k.setDetalji(detaljiDAO.uzmiDetaljePoKontaktID(k.getId()));
            }
            return kontakti;
        } catch (DetaljiDAOException | KontaktDAOException e) {
            throw new KontaktServisException("Ne mogu da vratim sve kontakte.", e);
        }
    }

    public Kontakt vratiKontaktPoID(int kontaktID) throws KontaktServisException {
        try {
            Kontakt k = kontaktDAO.vratiKontaktPoID(kontaktID);
            if (k == null) {
                throw new KontaktServisException("Kontakt sa ID=" + kontaktID + " ne postoji.");
            }
            return k;
        } catch (KontaktDAOException e){
            throw new KontaktServisException("Ne mogu da vratim kontakt za ID=" + kontaktID, e);
        }
    }

    public List<Kontakt> pretraga(String vrednost) throws KontaktServisException {
        List<Kontakt> kontakti = vratiSveKontakte();
        List<Kontakt> pronadjeni = new ArrayList<>();
        for (Kontakt k : kontakti){
            String ime = k.getIme() + k.getPrezime();
            if (ime.toLowerCase().contains(vrednost.toLowerCase().strip())) {
                pronadjeni.add(k);
            }
        }
        return pronadjeni;
    }
}
