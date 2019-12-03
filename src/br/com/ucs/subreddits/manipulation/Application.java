package br.com.ucs.subreddits.manipulation;

import br.com.ucs.subreddits.manipulation.constants.Path;
import br.com.ucs.subreddits.manipulation.model.Subreddit;
import br.com.ucs.subreddits.manipulation.service.HashService;
import br.com.ucs.subreddits.manipulation.service.MainFileDateIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileIdIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileServiceBean;
import br.com.ucs.subreddits.manipulation.service.RawDataFileServiceBean;
import br.com.ucs.subreddits.manipulation.util.AES;
import br.com.ucs.subreddits.mongojson.service.JsonGenerator;
import br.com.ucs.subreddits.mongojson.service.MongoConnection;
import br.com.ucs.subreddits.mongojson.service.MongoDAO;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoDatabase;
import sun.security.krb5.internal.crypto.Aes128;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {
    RawDataFileServiceBean rawService;
    MainFileServiceBean mainService;
    MainFileIdIndexedService idIndexedService;
    MainFileDateIndexedService mainFileDateIndexedService;

    public void startApplication() {
        System.out.println("Iniciando aplicação...");
        rawService = new RawDataFileServiceBean();
        rawService.createAndSetRawDataFile(Path.RAW_FILE_PARTIAL);
        rawService.updateFileSubreddits();
        System.out.println("Finalizado lista de registro buscadas no arquivo de dados brutos...");
        System.out.println("====================================================================");

        System.out.println("Iniciando inserção dos registros no arquivo de dados sequenciais...");
        mainService = new MainFileServiceBean();
        mainService.createAndPopulateFile(Path.MAIN_FILE, rawService.getRawDataFile().getSubredditList());
        System.out.println("Finalizado lista de registros no arquivo sequencial principal...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação e inserção do arquivo de índice primário baseados no campo id...");
        idIndexedService = new MainFileIdIndexedService(mainService.getMainFile());
        idIndexedService.createAndPopulateIdFile(Path.INDEXED_ID_FILE);
        System.out.println("Finalizado arquivo de índices baseados no campo id...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação e inserção do arquivo de índice secundáros baseados no campo data...");
        mainFileDateIndexedService = new MainFileDateIndexedService(mainService.getMainFile());
        mainFileDateIndexedService.createAndPopulateDateFile(Path.INDEXED_DATE_FILE);
        System.out.println("Finalizado criação e inserção do arquivo de índice primário baseados no campo data...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação de índices de hash de datas...");
        HashService hashService = new HashService();
        hashService.createMap(mainFileDateIndexedService.getIndexedFile());
        System.out.println("Finalizado criação de índices de hash de datas...");

        JsonGenerator jsonGenerator = new JsonGenerator();
        MongoConnection mongoConnection = new MongoConnection();
        MongoDAO mongoDAO = new MongoDAO();
        DBCollection mongoConnectionDb = mongoConnection.getMongoConnectionDb();

        List<Subreddit> subredditList = rawService.getRawDataFile().getSubredditList();
//        List<Subreddit> subreddits = subredditList.subList(11000,20000);
//        for (Subreddit subreddit : subreddits) {
//            AES aes = new AES();
//            String encrypt = aes.encrypt(subreddit.getDisplayName(), "PERNAMBUCO!");
//            subreddit.setDisplayName(encrypt);
//
//            if(!mongoDAO.existById(subreddit.getId(),mongoConnectionDb)) {
//                String json = jsonGenerator.getJson(subreddit);
//                mongoDAO.insertSubreddit(json,mongoConnectionDb);
//            }
//        }

        mongoDAO.createIndex(new MongoConnection().getMongoConnection());

    }


    public int menu() {
        System.out.println("====================================================================");
        System.out.println("Bem-vindo ao menu \n Opções");
        System.out.println("1. Realizar pesquisa no arquivo principal sequencial. ");
        System.out.println("2. Realizar pesquisa no arquivo principal sequencial-indexado através do índice do campo id. ");
        System.out.println("3. Realizar pesquisa no arquivo principal sequencial-indexado através do índice do campo data. ");
        System.out.println("4. Realizar pesquisa no Banco por ID. ");
        System.out.println("6. Realizar pesquisa no Banco pelo Nome. ");
        System.out.println("7. Pesquisa mais inscritos");

        System.out.println("-1.Exit");

        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            System.out.println("Informe : ");
            String next = scanner1.next().trim();
            switch (next) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
                case "6":
                    return 6;
                case "7":
                    return 7;
                case "-1":
                    return -1;
                default:
            }
        }
    }

    public boolean switchApplication(int choice) {
        switch (choice) {
            case 1:
                binarySearchId(1);
                break;
            case 2:
                binarySearchId(2);
                break;
            case 3:
                binarySearchByDate();
                break;
            case 4:
                mongoById();
                break;
            case 6:
                mongoByName();
                break;
            case 7:
                monthWithMostSubscribed();
                break;
            case -1:
                return false;
            default:
        }

        return true;
    }

    private void monthWithMostSubscribed() {
        MongoConnection mConnection = new MongoConnection();
        MongoDAO dao = new MongoDAO();
        dao.mostSubscriber(mConnection.getMongoConnectionDb());
    }

    private void mongoByName() {
        Scanner scanner1 = new Scanner(System.in);

        String name = "";
        System.out.println("Informe o nome : ");
        name = scanner1.next().trim();

        AES aes = new AES();
        String encrypt = aes.encrypt(name, "PERNAMBUCO!");

        MongoConnection mConnection = new MongoConnection();
        MongoDAO dao = new MongoDAO();
        dao.findByName(encrypt, mConnection.getMongoConnectionDb());
        System.out.println("end");
    }

    private void mongoById() {
        Scanner scanner1 = new Scanner(System.in);

        int id = 0;
        while (true) {
            System.out.println("Informe um id : ");
            String next = scanner1.next().trim();

            id = 0;
            try {
                id = Integer.parseInt(next);
                break;
            } catch (NumberFormatException ex) {
            }
        }

        MongoConnection mConnection = new MongoConnection();
        MongoDAO dao = new MongoDAO();
        dao.findID(id,mConnection.getMongoConnectionDb());
    }

    @SuppressWarnings("Duplicates")
    private void binarySearchByDate() {
        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            Calendar c = getCalendar(scanner1);
            if (c == null) continue;

            Date time = c.getTime();
            System.out.println("date " + time.toString());
            String address = mainFileDateIndexedService.binarySearchByData(time);

            System.out.println("address " + address);

            String[] split = address.split(";");
            if (split.length == 2) {
                int pos = Integer.valueOf(split[0]);
                int finalPos = Integer.valueOf(split[1]);

                mainService.printMainFileInterval(pos, finalPos);
            }


        }
    }

    private Calendar getCalendar(Scanner scanner1) {
        System.out.println("Informe o ano : ");
        String ano = scanner1.next().trim();

        int id = 0;
        try {
            id = Integer.parseInt(ano);
        } catch (NumberFormatException ex) {
            return null;
        }

        System.out.println("Informe o mes : ");
        String mesString = scanner1.next().trim();

        int mes = 0;
        try {
            mes = Integer.parseInt(mesString);
        } catch (NumberFormatException ex) {
            return null;
        }

        System.out.println("Informe o dia : ");
        String diaString = scanner1.next().trim();

        int dia = 0;
        try {
            dia = Integer.parseInt(diaString);
        } catch (NumberFormatException ex) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.set(id, mes - 1, dia, 0, 0);
        return c;
    }


    private void binarySearchId(int mode) {
        Scanner scanner1 = new Scanner(System.in);

        int id = 0;
        while (true) {
            System.out.println("Informe um inteiro : ");
            String next = scanner1.next().trim();

            id = 0;
            try {
                id = Integer.parseInt(next);
                break;
            } catch (NumberFormatException ex) {
            }
        }

        String key = String.valueOf(id);
        if (mode == 1) {
            System.out.println(mainService.binarySearchById(key));
        } else if (mode == 2) {
            try {
                String address = idIndexedService.binarySearchById(key);
                int position = Integer.parseInt(address);
                mainService.findByAdress(position);
            } catch (Exception e) {
            }
        }


    }


}
