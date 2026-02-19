package com.milos.contactsmanager.presentation;
import com.milos.contactsmanager.exceptions.DetaljiServisException;
import com.milos.contactsmanager.exceptions.KontaktServisException;
import com.milos.contactsmanager.model.*;
import com.milos.contactsmanager.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KonzolnaAplikacija {
    private final Scanner scanner = new Scanner(System.in);
    private final KontaktService kontaktService;
    private final DetaljiService detaljiService;
    public KonzolnaAplikacija(){
        kontaktService = new KontaktService();
        detaljiService = new DetaljiService();
    }
    public void pokreni(){
        boolean radi = true;

        while (radi) {
            System.out.println("\n===== KONTAKTNI MENI =====");
            System.out.println("1. Prikazi sve kontakte");
            System.out.println("2. Dodaj kontakt");
            System.out.println("3. Pretrazi kontakt");
            System.out.println("4. Azuriraj kontakt");
            System.out.println("5. Obrisi kontakt");
            System.out.println("6. Upravljaj detaljima");
            System.out.println("7. Izlaz");
            System.out.print("Opcija: ");

            switch (scanner.nextLine()) {
                case "1":
                    prikaziSve();
                    break;
                case "2":
                    dodajKontakt();
                    break;
                case "3":
                    pretraziKontakt();
                    break;
                case "4":
                    azurirajKontakt();
                    break;
                case "5":
                    obrisiKontakt();
                    break;
                case "6":
                    upravljajDetaljima();
                    break;
                case "7":
                    radi = false;
                    break;
                default:
                    System.err.println("Nepoznata opcija!");
                    break;
            }
        }
        System.out.println("Aplikacija zavrsena.");
    }

    private void prikaziSve() {
        List<Kontakt> kontakti = new ArrayList<>();
        try {
            kontakti = kontaktService.vratiSveKontakte();
        } catch (KontaktServisException e) {
            System.err.println("Greska prilikom ucitavanja kontakata: " + e.getMessage());
        }

        if (kontakti.isEmpty()) {
            System.out.println("Nema kontakata.");
            return;
        }
        for (Kontakt k : kontakti) {
            System.out.println(k);
        }
    }

    private void dodajKontakt() {
        System.out.print("Ime: ");
        String ime = scanner.nextLine();
        System.out.print("Prezime: ");
        String prezime = scanner.nextLine();
        System.out.print("Adresa: ");
        String adresa = scanner.nextLine();
        System.out.print("Kompanija: ");
        String kompanija = scanner.nextLine();
        LocalDate rodjendanDate = null;
        while (true) {
            System.out.print("Rodjendan (YYYY-MM-DD, ostavi prazno ako nema): ");
            String rodjendan = scanner.nextLine();
            if (rodjendan.isBlank()) {
                break;
            }
            try {
                rodjendanDate = LocalDate.parse(rodjendan);
                break;
            } catch (DateTimeParseException e) {
                System.err.println("Nepravilan format datuma. Pokusaj ponovo.");
            }
        }
        System.out.print("Napomene: ");
        String napomene = scanner.nextLine();

        Kontakt k = new Kontakt();
        k.setIme(ime);
        k.setPrezime(prezime);
        k.setAdresa(adresa);
        k.setKompanija(kompanija);
        k.setNapomene(napomene);
        if (rodjendanDate != null) {
            k.setRodjendan(rodjendanDate);
        }

        try {
            kontaktService.dodajKontakt(k);
            System.out.println("Kontakt dodat.");
        } catch (KontaktServisException e) {
            System.err.println("Greška prilikom dodavanja kontakta: " + e.getMessage());
        }
    }

    private void pretraziKontakt() {
        System.out.print("PRETRAGA: ");
        String pojam = scanner.nextLine();
        List<Kontakt> rezultati = new ArrayList<>();
        try {
            rezultati = kontaktService.pretraga(pojam.replaceAll("\\s+", ""));
        } catch (KontaktServisException e) {
            System.err.println("Greska prilikom pretrage: " + e.getMessage());
        }

        if (rezultati.isEmpty()) {
            System.out.println("Nema pronadjenih kontakata.");
            return;
        }
        for (Kontakt k : rezultati) {
            System.out.println(k);
        }
    }

    private void azurirajKontakt() {
        System.out.print("ID kontakta za azuriranje: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Nepravilan ID. Morate uneti broj.");
            return;
        }

        Kontakt k;
        try {
            k = kontaktService.vratiKontaktPoID(id);
        } catch (KontaktServisException e) {
            System.err.println("Greška prilikom vraćanja kontakta: " + e.getMessage());
            return;
        }

        System.out.print("Novo ime (" + k.getIme() + "): ");
        String ime = scanner.nextLine();
        if (!ime.isBlank()) k.setIme(ime);

        System.out.print("Novo prezime (" + k.getPrezime() + "): ");
        String prezime = scanner.nextLine();
        if (!prezime.isBlank()) k.setPrezime(prezime);

        System.out.print("Nova adresa (" + k.getAdresa() + "): ");
        String adresa = scanner.nextLine();
        if (!adresa.isBlank()) k.setAdresa(adresa);

        System.out.print("Nova kompanija (" + k.getKompanija() + "): ");
        String kompanija = scanner.nextLine();
        if (!kompanija.isBlank()) k.setKompanija(kompanija);

        System.out.print("Nova napomena (" + k.getNapomene() + "): ");
        String napomena = scanner.nextLine();
        if (!napomena.isBlank()) k.setNapomene(napomena);

        LocalDate rodjendanDate = k.getRodjendan();
        while (true) {
            System.out.print("Novi rodjendan (YYYY-MM-DD, ostavi prazno za prethodni): ");
            String rodjendan = scanner.nextLine();
            if (rodjendan.isBlank()) break;
            try {
                rodjendanDate = LocalDate.parse(rodjendan);
                break;
            } catch (DateTimeParseException e) {
                System.err.println("Nepravilan format datuma. Pokusajte ponovo.");
            }
        }
        k.setRodjendan(rodjendanDate);

        try {
            kontaktService.azurirajKontakt(k);
            System.out.println("Kontakt azuriran.");
        } catch (KontaktServisException e) {
            System.err.println("Greška prilikom ažuriranja: " + e.getMessage());
        }
    }

    private void obrisiKontakt() {
        prikaziSve();

        System.out.print("ID kontakta za brisanje: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Nepravilan ID. Morate uneti broj.");
            return;
        }

        System.out.println("Da li sigurno zelis da obrises izabrani kontakt? (DA - za potvrdu): ");
        String odgovor = scanner.nextLine();

        if(odgovor.equalsIgnoreCase("da")) {
            try {
                kontaktService.obrisiKontakt(id);
                System.out.println("Kontakt i svi detalji obrisani.");
            } catch (KontaktServisException e) {
                System.err.println("Greska prilikom brisanja: " + e.getMessage());
            }
        }

    }

    private void upravljajDetaljima() {
        prikaziSve();

        System.out.print("ID kontakta: ");
        int kontaktId;
        try {
            kontaktId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Nepravilan ID. Morate uneti broj.");
            return;
        }

        boolean radi;

        try {
            Kontakt k = kontaktService.vratiKontaktPoID(kontaktId);
            radi=true;
            System.out.println("Izabrani kontakt je " + k.getIme() + " " + k.getPrezime());
        }
        catch (KontaktServisException e){
            System.err.println("Greska prilikom trazenja kontakta: " + e.getMessage());
            return;
        }

        while (radi) {
            System.out.println("\n--- DETALJI MENI ---");
            System.out.println("1. Prikazi detalje");
            System.out.println("2. Dodaj detalj");
            System.out.println("3. Obrisi detalj");
            System.out.println("4. Nazad");
            System.out.print("Opcija: ");

            switch (scanner.nextLine()) {
                case "1":
                    prikaziDetalje(kontaktId);
                    break;
                case "2":
                    dodajDetalj(kontaktId);
                    break;
                case "3":
                    obrisiDetalj(kontaktId);
                    break;
                case "4":
                    radi = false;
                    break;
                default:
                    System.err.println("Nepoznata opcija!");
                    break;
            }
        }
    }

    private void prikaziDetalje(int kontaktId) {
        List<KontaktDetalji> lista = new ArrayList<>();
        try{
            lista = detaljiService.vratiDetaljeZaKontakt(kontaktId);
        } catch (DetaljiServisException e){
            System.err.println("Greska prilikom trazenja detalja: " + e.getMessage());
        }
        if (lista.isEmpty()) {
            System.out.println("Nema detalja za ovaj kontakt.");
            return;
        }
        for (KontaktDetalji d : lista) {
            System.out.println(d.getId() + ". " + d.vratiVrednosti());
        }
    }

    private void dodajDetalj(int kontaktId) {
        System.out.println("""
                1.Telefon
                2.Email
                3.Link""");
        System.out.println("Unesi broj detalja: ");
        String tip = scanner.nextLine();
        try{
            switch (tip) {
                case "1": {
                    System.out.print("Unesi broj: +");
                    String brojStr = scanner.nextLine();
                    if (brojStr.matches("[0-9]+")){
                        detaljiService.dodajDetalj(kontaktId, new Telefon(0, kontaktId, "Telefon", "+" + brojStr));
                        System.out.println("Detalj dodat.");
                        break;
                    }
                    else {
                        System.err.println("Nepravilan broj telefona. Telefon mora da sadrzi samo brojeve.");
                        return;
                    }
                }
                case "2": {
                    System.out.print("Unesi email: ");
                    String vrednost = scanner.nextLine();
                    if (vrednost.contains("@") && vrednost.contains(".")) {
                        detaljiService.dodajDetalj(kontaktId, new Email(0, kontaktId, "Email", vrednost));
                        System.out.println("Detalj dodat.");
                        break;
                    }
                    else {
                        System.err.println("Nepravilan format e-mail adrese. E-mail mora da sadrzi (@) i tacku.");
                        return;
                    }
                }
                case "3": {
                    System.out.print("Unesi link: ");
                    String vrednost = scanner.nextLine();
                    detaljiService.dodajDetalj(kontaktId, new Link(0, kontaktId, "Link", vrednost));
                    System.out.println("Detalj dodat.");
                    break;
                }
                default:
                    System.out.println("Nepoznat tip detalja.");
                    break;
            }
        } catch (DetaljiServisException e) {
            System.err.println("Greska prilikom dodavanja detalja: " + e.getMessage());
        }
    }

    private void obrisiDetalj(int kontaktId) {
        prikaziDetalje(kontaktId);
        System.out.print("ID detalja za brisanje: ");
        int detaljId;

        try {
            detaljId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Nepravilan ID. Morate uneti broj.");
            return;
        }

        try {
            detaljiService.obrisiDetalj(detaljId, kontaktId);
            System.out.println("Detalj je obrisan.");
        } catch (DetaljiServisException e) {
            System.err.println("Greska prilikom brisanja detalja: " + e.getMessage());
        }
    }
}
