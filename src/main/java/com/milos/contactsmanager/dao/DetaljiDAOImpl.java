package com.milos.contactsmanager.dao;

import com.milos.contactsmanager.exceptions.DetaljiDAOException;
import com.milos.contactsmanager.model.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetaljiDAOImpl implements DetaljiDAO {
    private static final String DATA_PATH = "src/main/resources/podaci/kontakt_detalji.csv";

    @Override
    public List<KontaktDetalji> uzmiDetaljePoKontaktID(int kontaktId) throws DetaljiDAOException {
        List<KontaktDetalji> lista = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_PATH))) {
            csvReader.readNext(); // header
            String[] red;
            while ((red = csvReader.readNext()) != null) {
                if (Integer.parseInt(red[1].trim()) == kontaktId) {
                    KontaktDetalji detalj = napraviDetalj(red);
                    if (detalj != null) {
                        lista.add(detalj);
                    }
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new DetaljiDAOException("Ne mogu da vratim listu detalja iz CSV fajla.", e);
        }
        return lista;
    }

    @Override
    public void dodajDetalj(KontaktDetalji detalj) throws DetaljiDAOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(DATA_PATH, true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            int noviId = poslednjiSlobodanID();
            String[] red = {
                    String.valueOf(noviId),
                    String.valueOf(detalj.getKontakt_id()),
                    detalj.vratiVrednosti(),
                    detalj.getTip()
            };
            writer.writeNext(red);
        } catch (IOException e) {
            throw new DetaljiDAOException("Ne mogu da dodam novi detalj u CSV fajl.", e);
        }
    }

    @Override
    public boolean obrisiDetalj(int detaljId, int kontaktId) throws DetaljiDAOException {
        List<String[]> redovi = ucitajSveRedove();

        for (int i = 1; i < redovi.size(); i++) {
            int id = Integer.parseInt(redovi.get(i)[0].trim());
            int cid = Integer.parseInt(redovi.get(i)[1].trim());
            if (id == detaljId && cid == kontaktId) {
                redovi.remove(i);
                upisiSveRedove(redovi);
                return true;
            }
        }

        return false;
    }

    @Override
    public void obrisiSveDetaljeZaKontakt(int kontaktId) throws DetaljiDAOException {
        List<String[]> redovi = ucitajSveRedove();

        for (int i = 1; i < redovi.size(); i++) {
            if (Integer.parseInt(redovi.get(i)[1].trim()) == kontaktId) {
                redovi.remove(i);
                i--;
            }
        }

        upisiSveRedove(redovi);
    }

    //pomocne metode

    private List<String[]> ucitajSveRedove() throws DetaljiDAOException {
        try (CSVReader reader = new CSVReader(new FileReader(DATA_PATH))) {
            return reader.readAll();
        } catch (CsvException | IOException e) {
            throw new DetaljiDAOException("Ne mogu da pročitam CSV fajl detalja.", e);
        }
    }

    private void upisiSveRedove(List<String[]> redovi) throws DetaljiDAOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(DATA_PATH),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(redovi);
        } catch (IOException e) {
            throw new DetaljiDAOException("Ne mogu da upišem u CSV fajl detalja.", e);
        }
    }

    private KontaktDetalji napraviDetalj(String[] red) {
        int id = Integer.parseInt(red[0].trim());
        int kontaktId = Integer.parseInt(red[1].trim());
        String vrednost = red[2].trim();
        String tip = red[3].trim();

        switch (tip) {
            case "Telefon":
                return new Telefon(id, kontaktId, tip, Integer.parseInt(vrednost));
            case "Email":
                return new Email(id, kontaktId, tip, vrednost);
            case "Link":
                return new Link(id, kontaktId, tip, vrednost);
            default:
                return null;
        }

    }

    private int poslednjiSlobodanID() throws DetaljiDAOException {
        int maxId = 0;
        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_PATH))) {
            csvReader.readNext();
            String[] red;
            while ((red = csvReader.readNext()) != null) {
                int id = Integer.parseInt(red[0].trim());
                if (id > maxId) maxId = id;
            }
        } catch (CsvValidationException | IOException e) {
            throw new DetaljiDAOException("Greška pri čitanju novog ID-ja za detalj", e);
        }
        return maxId + 1;
    }
}